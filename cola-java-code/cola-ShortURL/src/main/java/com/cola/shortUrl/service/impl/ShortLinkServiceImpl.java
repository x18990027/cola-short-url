package com.cola.shortUrl.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cola.common.core.domain.dto.CommonIdDto;
import com.cola.common.enums.SystemStateCodeEnum;
import com.cola.common.exception.ServiceException;
import com.cola.common.utils.DateUtils;
import com.cola.common.utils.SecurityUtils;
import com.cola.common.utils.bean.BeanUtils;
import com.cola.shortUrl.domain.DomainManager;
import com.cola.shortUrl.domain.LinkAccessStatist;
import com.cola.shortUrl.domain.dto.EncodeGoDto;
import com.cola.shortUrl.domain.dto.SearchTShortLinkDto;
import com.cola.shortUrl.domain.dto.TShortLinkDto;
import com.cola.shortUrl.domain.dto.UpdateTShortLinkDto;
import com.cola.shortUrl.domain.vo.TShortLinkVo;
import com.cola.shortUrl.domain.vo.UrlStatisticsVo;
import com.cola.shortUrl.mapper.DomainManagerMapper;
import com.cola.shortUrl.mapper.LinkAccessStatisticsMapper;
import com.cola.shortUrl.mapper.LinkGroupMapper;
import com.cola.shortUrl.mapper.ShortLinkMapper;
import com.cola.shortUrl.domain.TShortLink;
import com.cola.shortUrl.util.AESEncryptionUtils;
import com.cola.shortUrl.util.Base62Utils;
import com.cola.shortUrl.util.SnowFlakeUtils;
import com.cola.shortUrl.service.ShortLinkService;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@Slf4j
@Service
public class ShortLinkServiceImpl implements ShortLinkService {


    @Resource
    private ShortLinkMapper shortLinkDao;

    @Resource
    private LinkGroupMapper groupMapper;

    @Resource
    private DomainManagerMapper domainManagerMapper;

    @Resource
    private LinkAccessStatisticsMapper linkAccessStatisticsMapper;

    /**
     * 异步处理离线任务的线程池
     */
    private ThreadPoolTaskExecutor asyncTaskTreadPool;

    @PostConstruct
    public void init() {
        this.asyncTaskTreadPool = new ThreadPoolTaskExecutor();
        this.asyncTaskTreadPool.initialize();
        this.asyncTaskTreadPool.setCorePoolSize(2);
        this.asyncTaskTreadPool.setMaxPoolSize(4);
        this.asyncTaskTreadPool.setQueueCapacity(16);
        this.asyncTaskTreadPool.setThreadNamePrefix("离线任务线程");
    }

    @PreDestroy
    public void destroy() {
        if (this.asyncTaskTreadPool != null) {
            this.asyncTaskTreadPool.shutdown();
        }
    }


    @Override
    public List<TShortLinkVo> findList(SearchTShortLinkDto searchTShortLinkDto) {

        if (searchTShortLinkDto.getDateRange() != null && searchTShortLinkDto.getDateRange().size() > 1) {
            searchTShortLinkDto.setStartTime(searchTShortLinkDto.getDateRange().get(0));
            searchTShortLinkDto.setEndTime(searchTShortLinkDto.getDateRange().get(1));
        }

        searchTShortLinkDto.setUserId(SecurityUtils.getUserId());
        List<TShortLinkVo> list = shortLinkDao.findList(searchTShortLinkDto);

        return list;
    }

    /**
     * 生成短链接
     *
     * @param tShortLinkDto 请求类
     * @return {@code String}
     */
    @Override
    public synchronized String generateShortLink(TShortLinkDto tShortLinkDto) {


        //长链接格式校验
        boolean validURL = isValidURL(tShortLinkDto.getLongLink());
        if (!validURL) {
            throw new ServiceException(SystemStateCodeEnum.ERROR_URL_FORMAT.getMsg());
        }

        if (tShortLinkDto.getNumLimitLink() != null && StringUtils.isNotEmpty(tShortLinkDto.getNumLimitLink())) {
            boolean validNumLimit = isValidURL(tShortLinkDto.getNumLimitLink());
            if (!validNumLimit) {
                throw new ServiceException(SystemStateCodeEnum.ERROR_URL_FORMAT.getMsg());
            }
        }


        //校验分组合法性
        int oneself = groupMapper.isOneself(SecurityUtils.getUserId(), tShortLinkDto.getGroupId());
        if (oneself <= 0) {
            throw new ServiceException(SystemStateCodeEnum.ERROR_REQUEST_ILLEGAL.getMsg());
        }


        DomainManager domainManager = domainManagerMapper.selectById(tShortLinkDto.getDomainId());
        Assert.notNull(domainManager, "非法请求！");


        TShortLink tShortLink = new TShortLink();

        if (tShortLinkDto.getAccessPassword() != null) {
            if (tShortLinkDto.getAccessPassword().contains(" ")) {
                throw new ServiceException("访问密码请勿输入空格！");
            }
            //4位数字字母组合校验
            String regex = "^[A-Za-z0-9]{4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(tShortLinkDto.getAccessPassword());
            boolean matches = matcher.matches();
            if (matches) {
                tShortLink.setAccessPassword(tShortLinkDto.getAccessPassword());
            } else {
                throw new ServiceException("请入数字或字母组合格式的4位密码！");
            }
        }


        Long timestamp = System.currentTimeMillis();

        // 使用 Murmurhash算法，进行哈希，得到长链接Hash值
        long longLinkHash = Hashing.murmur3_32().hashString(timestamp.toString(), StandardCharsets.UTF_8).padToLong();
        String urlKey = regenerateOnHashConflict(tShortLinkDto.getLongLink(), longLinkHash);

        StringBuilder shortUrl = new StringBuilder();
        shortUrl.append(domainManager.isSslStatus() ? "https://" : "http://").append(domainManager.getDomain()).append("/" + urlKey);

        BeanUtils.copyProperties(tShortLinkDto, tShortLink);


        tShortLink.setUrlKey(urlKey);
        tShortLink.setShortLink(shortUrl.toString());
        tShortLink.setVisitsNum(0);
        tShortLink.setStatus(1);
        tShortLink.setUserId(SecurityUtils.getUserId());
        tShortLink.setCreateTime(new Date());
        tShortLink.setUpdateTime(new Date());
        shortLinkDao.insertShortUrl(tShortLink);
        return shortUrl.toString();

    }

    @Override
    public String findByShortLink(String urlKey, LinkAccessStatist linkAccessStatist) {

        TShortLink tShortLink = shortLinkDao.selectByShortLink(urlKey);


        if (!tShortLink.getShortLink().contains(urlKey)) {
            return "http://url.cn/sorry";
        }


        //查询不到或者状态停用
        if (tShortLink == null || tShortLink.getStatus() != 1) {
            return "http://url.cn/sorry";
        }

        //设置了过期时间并且超过了当前时间，该短链失效
        if (tShortLink.getExpiryTime() != null && DateUtils.getNowDate().after(tShortLink.getExpiryTime())) {
            return "http://url.cn/sorry";
        }

        CompletableFuture.runAsync(() -> {
            linkAccessStatist.setShortLinkId(tShortLink.getId());
            linkAccessStatist.setUserBy(tShortLink.getUserId());
            insertAccessLog(linkAccessStatist, tShortLink);
        }, asyncTaskTreadPool);


        if (tShortLink.getAccessPassword() != null && !tShortLink.getAccessPassword().isEmpty()) {
            //需要密码访问逻辑处理
//            return "https://ylm2.online/encode?id="+tShortLink.getId();
            return "http://d.ylm2.online/encode?id=" + tShortLink.getId();
//            return  "http://localhost:2000/encode?id="+tShortLink.getId();
        }

        //访问次数到达阈值跳转超过阈值的长链接
        if (tShortLink.getNumLimit() != null && tShortLink.getNumLimit() > 0) {
            long accessNum = tShortLink.getVisitsNum();
            if (accessNum > tShortLink.getNumLimit()) {
                String url = get360Url(tShortLink.getNumLimitLink());
                if (url != null) {
                    return url;
                }
                return tShortLink.getNumLimitLink();
            }
        }

        //访问次数到达ip阈值访问禁止
        if (tShortLink.getIpLimit() != null && tShortLink.getIpLimit() > 0) {
            Integer ipNum = linkAccessStatisticsMapper.ipTotal(tShortLink.getId());
            if (tShortLink.getIpLimit() < ipNum) {
                List<String> ipList = linkAccessStatisticsMapper.ipList(tShortLink.getId());
                if (!ipList.contains(linkAccessStatist.getIp())) {
                    return "http://url.cn/sorry";
                }
            }
        }


        String longLink = "";

        if (tShortLink.getEncodeLongLink() != null && StringUtils.isNotEmpty(tShortLink.getEncodeLongLink())) {
            longLink = tShortLink.getEncodeLongLink();
        } else {
            longLink = tShortLink.getLongLink();
        }
        if (tShortLink.isRedStatus()) {


            try {
                String code = AESEncryptionUtils.encode(tShortLink.getUrlKey());
                String encodedLink = URLEncoder.encode(code, "UTF-8");
                longLink = "http://d.ylm2.online/openRed?link=" + encodedLink;
//                longLink = "http://localhost:2000/openRed?link=" + encodedLink;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        return longLink;
    }

    @Override
    public int delLink(List<Long> idList) {
        return shortLinkDao.delLink(idList, SecurityUtils.getUserId());
    }

    @Override
    public int updateLink(UpdateTShortLinkDto updateTShortLinkDto) {

        updateTShortLinkDto.setUpdateTime(new Date());
        updateTShortLinkDto.setUserId(SecurityUtils.getUserId());
        return shortLinkDao.updateLink(updateTShortLinkDto);

    }

    @Override
    public int updateStatus(Long id, boolean status, Long userId) {

        return shortLinkDao.updateStatus(id, status, userId);
    }

    @Override
    public UrlStatisticsVo urlStatistics(CommonIdDto commonIdDto) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


        LocalDate currentDate = LocalDate.now(); // 获取当前日期精确到天
        LocalDate tomorrow = currentDate.plusDays(1);
        LocalDate yesterday = currentDate.minusDays(1);

        List<LinkAccessStatist> todayList = linkAccessStatisticsMapper.selectByTime(commonIdDto.getId(), SecurityUtils.getUserId(), currentDate, tomorrow);
        List<LinkAccessStatist> yesterdayList = linkAccessStatisticsMapper.selectByTime(commonIdDto.getId(), SecurityUtils.getUserId(), yesterday, currentDate);

        // 按照createTime字段进行分组
        Map<String, List<LinkAccessStatist>> groupedByToday = todayList.stream()
                .collect(Collectors.groupingBy(item -> {
                    // 使用dateFormat来格式化每个item的createTime
                    return dateFormat.format(item.getCreateTime());
                }));

        Map<String, List<LinkAccessStatist>> groupedByYesterday = yesterdayList.stream()
                .collect(Collectors.groupingBy(item -> {
                    // 使用dateFormat来格式化每个item的createTime
                    return dateFormat.format(item.getCreateTime());
                }));

        List<String> dayHoursList = dayHoursList();

        List<Long> todayNumList = new ArrayList<>();
        List<Long> yesterdayNumList = new ArrayList<>();
        for (String str : dayHoursList) {
            if (groupedByToday.containsKey(str)) {
                todayNumList.add((long) groupedByToday.get(str).size());
            } else {
                todayNumList.add(0L);
            }

            if (groupedByYesterday.containsKey(str)) {
                yesterdayNumList.add((long) groupedByYesterday.get(str).size());
            } else {
                yesterdayNumList.add(0L);
            }
        }

        UrlStatisticsVo urlStatisticsVo = linkAccessStatisticsMapper.sumUp(commonIdDto.getId(), SecurityUtils.getUserId(), currentDate, tomorrow);
        urlStatisticsVo.setTodayNumList(todayNumList);
        urlStatisticsVo.setYesterdayNumList(yesterdayNumList);
        urlStatisticsVo.setDaysStatistics(linkAccessStatisticsMapper.daysStatistics(commonIdDto.getId(), SecurityUtils.getUserId()));
        List<Map<String, Object>> maps = linkAccessStatisticsMapper.selectTerminal(commonIdDto.getId(), SecurityUtils.getUserId());
        urlStatisticsVo.setTerminalList(maps);

        List<Map<String, Object>> cityList = linkAccessStatisticsMapper.selectCity(commonIdDto.getId(), SecurityUtils.getUserId());

        List<Map<String, Object>> cityListNew = new ArrayList<>();

        if (cityList.size() < 10) {
            int num = 10 - cityList.size();
            for (Integer i = num; i > 0; i--) {
                String value = "待上榜" + i.toString();
                Map<String, Object> map = new HashMap<>();
                map.put("address", value);
                map.put("num", 0);
                cityListNew.add(map);
            }
        }
        cityListNew.addAll(cityList);
        urlStatisticsVo.setCityList(cityListNew);

        return urlStatisticsVo;

    }

    @Override
    public String encodeGo(EncodeGoDto encodeGoDto) {
        TShortLink tShortLink = shortLinkDao.selectById(encodeGoDto.getId());

        //访问次数到达阈值跳转超过阈值的长链接
        if (tShortLink.getNumLimit() > 0) {
            long accessNum = linkAccessStatisticsMapper.selectUrlCount(tShortLink.getId());
            if (accessNum > tShortLink.getNumLimit()) {
                return tShortLink.getNumLimitLink();
            }
        }

        if (encodeGoDto.getCode().equals(tShortLink.getAccessPassword())) {
            if (tShortLink.getEncodeLongLink() != null) {
                return tShortLink.getEncodeLongLink();
            }
            return tShortLink.getLongLink();
        } else {
            throw new ServiceException("密码错误！");
        }

    }


    private static List<String> dayHoursList() {
        List<String> list = new ArrayList<>();
        list.add("00:00:00");
        list.add("01:00:00");
        list.add("02:00:00");
        list.add("03:00:00");
        list.add("04:00:00");
        list.add("05:00:00");
        list.add("06:00:00");
        list.add("07:00:00");
        list.add("08:00:00");
        list.add("09:00:00");
        list.add("10:00:00");
        list.add("11:00:00");
        list.add("12:00:00");
        list.add("13:00:00");
        list.add("14:00:00");
        list.add("15:00:00");
        list.add("16:00:00");
        list.add("17:00:00");
        list.add("18:00:00");
        list.add("19:00:00");
        list.add("20:00:00");
        list.add("21:00:00");
        list.add("22:00:00");
        list.add("23:00:00");
        return list;
    }


    //插入短链访问记录
    public int insertAccessLog(LinkAccessStatist linkAccessStatist, TShortLink tShortLink) {
        linkAccessStatist.setCreateTime(DateUtils.getNowDate());
        linkAccessStatist.setAddress(getCity(linkAccessStatist.getIp()));

        long num = tShortLink.getVisitsNum() + 1;

        shortLinkDao.updateNum(tShortLink.getId(), num);

        return linkAccessStatisticsMapper.insertLog(linkAccessStatist);
    }


    // 长链接缩短方法
    private String regenerateOnHashConflict(String longLink, long longLinkHash) {
        // 这个工具类是 雪花算法的工具类
        SnowFlakeUtils snowFlakeUtil = new SnowFlakeUtils();
        // 雪花算法 生成主键id
        long id = snowFlakeUtil.nextId();
        long uniqueIdHash = Hashing.murmur3_32().hashLong(id).padToLong();


        // 相减主要是为了让哈希值更小
        String urlKey = Base62Utils.encodeToBase62String(Math.abs(longLinkHash - uniqueIdHash));

        log.info("缩短后key:{}", urlKey);

        TShortLink selectUrl = shortLinkDao.selectByOneself(urlKey);

        if (selectUrl == null) {
            return urlKey;
        }
        // 如果有 短链接 重复 再走一遍
        return regenerateOnHashConflict(longLink, longLinkHash);
    }


    //  http/https链接校验
    public static boolean isValidURL(String url) {
        String regex = "https?://.+";
        return url.matches(regex);
    }

    //第三方api通过ip获取城市信息
    public String getCity(String ip) {
        String city = null;

        String url = "http://ip-api.com/json/" + ip + "?lang=zh-CN";
        String postData = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .execute().body();
        log.info("请求响应结果：{}", postData);

        JSONObject postDataResult = JSONUtil.parseObj(postData);

        if (postDataResult.get("status").toString().equals("success")) {

            city = String.join(",", postDataResult.get("country").toString(), postDataResult.get("regionName").toString(), postDataResult.get("city").toString());
        }
        return city;
    }

    public String get360Url(String longLink) {

        String api = "https://suapi.net/api/website/360url";
        String key = "BizvnoEkdS6MDugPQPL26H2ma0";

        String link = api + "?key=" + key + "&url=" + longLink;

        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方式
            connection.setRequestMethod("GET");
            connection.connect();

            // 获取响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info("返回信息：" + line);
                    JSONObject jsonObject = new JSONObject(line);
                    String data = jsonObject.get("data").toString();
                    if (data.contains("http")) {
                        return data;
                    }

                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
