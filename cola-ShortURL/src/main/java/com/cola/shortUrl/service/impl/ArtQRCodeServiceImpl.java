package com.cola.shortUrl.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cola.common.enums.SystemStateCodeEnum;
import com.cola.common.exception.ServiceException;
import com.cola.common.utils.SecurityUtils;
import com.cola.shortUrl.domain.ArtQrCode;
import com.cola.shortUrl.domain.dto.ArtQRCodeDto;
import com.cola.shortUrl.domain.vo.ArtQRCodeVo;
import com.cola.shortUrl.mapper.ArtQRCodeMapper;
import com.cola.shortUrl.service.ArtQRCodeService;
import com.cola.shortUrl.util.Base62Utils;
import com.cola.shortUrl.util.SnowFlakeUtils;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class ArtQRCodeServiceImpl implements ArtQRCodeService {

    @Value("${zhiShuYun.api}")
    private String api;

    @Value("${zhiShuYun.token}")
    private String token;

    @Resource
    private ArtQRCodeMapper artQRCodeMapper;

    @Override
    public String generateCode(ArtQRCodeDto artQRCodeDto) {

        //查询剩余生成点数
        Integer num = artQRCodeMapper.selectUserOrderById(SecurityUtils.getUserId());
        if (num < 1) {
            throw new ServiceException("生成次数不足，请及时充值！");
        }


        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        // 将LocalDateTime转换为ZonedDateTime
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        // 将ZonedDateTime转换为Date
        Date date = Date.from(zonedDateTime.toInstant());


        String url = api + "?token=" + token;

        JSONObject paramsData = new JSONObject();
        paramsData.putOnce("rawurl", true);
        paramsData.putOnce("sub_marker", "random");
        paramsData.putOnce("marker_shape", "random");
//        paramsData.putOnce("qrw",1);

        List<String> list =new ArrayList<>();
        list.add("c1");
        list.add("c2");
        list.add("c3");
        list.add("sq1");
        list.add("sq2");
        list.add("sq3");
        list.add("rd1");
        list.add("rd2");
        list.add("rd3");
        list.add("d1");list.add("d2");list.add("d3");        list.add("r1");list.add("r2");list.add("r3");
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(15);
        paramsData.putOnce("pattern",list.get(randomNumber));
//        paramsData.putOnce("steps",18);


        //动态参数
        paramsData.putOnce("prompt", artQRCodeDto.getPrompt());
        paramsData.putOnce("type", artQRCodeDto.getType());
        paramsData.putOnce("content", artQRCodeDto.getContent());
        if (artQRCodeDto.getAspectRatio() != null) {
            paramsData.putOnce("aspect_ratio", artQRCodeDto.getAspectRatio());
        }
        if (artQRCodeDto.getPixelStyle() != null) {

        }
        if (artQRCodeDto.getPosition() != null) {
            paramsData.putOnce("position", artQRCodeDto.getPosition());
        }

        log.info("请求响应参数：{}", paramsData);
        String postData = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .body(paramsData.toString())
                .execute().body();
        log.info("请求响应结果：{}", postData);

        JSONObject postDataResult = JSONUtil.parseObj(postData);

        //api接口失败
        if (postDataResult == null || !postDataResult.containsKey("image_url") || postDataResult.get("image_url") == null) {
            throw new ServiceException(SystemStateCodeEnum.ERROR_GENERATE.getMsg());
        }

        String longUrl = postDataResult.get("image_url").toString();
        Long timestamp = System.currentTimeMillis();

        // 使用 Murmurhash算法，进行哈希，得到长链接Hash值
        long longLinkHash = Hashing.murmur3_32().hashString(longUrl + timestamp, StandardCharsets.UTF_8).padToLong();
        String urlKey = regenerateOnHashConflict(longUrl, longLinkHash);

        String shortUrl = "http://e.ylm2.online/a/" + urlKey;

        //入库
        ArtQrCode artQrCode = new ArtQrCode();
        artQrCode.setCreateBy(SecurityUtils.getUserId());
        artQrCode.setShortLink(shortUrl);
        artQrCode.setImageUrl(longUrl);
        artQrCode.setUrlKey(urlKey);
        artQrCode.setCreateTime(date);
        artQrCode.setRemark(artQRCodeDto.getRemark());
        artQrCode.setContent(artQRCodeDto.getContent());
        artQRCodeMapper.insertArtQRCode(artQrCode);

        //扣除点数
        num--;
        artQRCodeMapper.updateUserOrder(SecurityUtils.getUserId(), num);


        //发送邮件
        if (artQRCodeDto.getEmail() != null) {
            // 创建一个格式化对象，用于将时间格式化为 "yyyy-MM-dd HH:mm" 格式，即精确到分钟
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            // 格式化当前时间
            String dateTime = now.format(formatter);
            String msg = "您与[" + dateTime.toString() + "]提交的艺术二维码已成功生成！,请及时下载以免失效！" + "\n" + shortUrl.toString();
            sendEmail(artQRCodeDto.getEmail(), msg, shortUrl);
        }
        return shortUrl;

    }

    @Override
    public List<ArtQRCodeVo> qrList() {
        return artQRCodeMapper.qrList(SecurityUtils.getUserId());
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

        String selectUrl = artQRCodeMapper.selectByUrlKey(urlKey);
        if (selectUrl == null) {
            return urlKey;
        }
        // 如果有 短链接 重复 再走一遍
        return regenerateOnHashConflict(longLink, longLinkHash);
    }


    public void sendEmail(String email, String msg, String url) {

        // QQ邮箱的SMTP服务器地址、端口、用户名和密码
        final String smtpHost = "smtp.qq.com";
        final int smtpPort = 465; // SSL加密端口
        final String username = "18990027@qq.com";
        final String password = "xpxskwdkivvzbggf";

//         // 收件人邮箱地址
//         final String recipientEmail = "1155564@qq.com";

        // 设置邮件服务器属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // 如果使用TLS加密，请将此项设置为true
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        props.put("mail.smtp.ssl.enable", "true"); // 如果使用SSL加密，请将此项设置为true

        // 获取默认的Session对象，并加入密码验证
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        String htmlBody = "<html>"
                + "<body>"
                + "<h1>欢迎查阅您的艺术二维码</h1>"
                + "<p>有些过于艺术化的二维码可能需要另外一台设备扫码才能打开：</p>"
                + "<p>请点击以下链接查看您的艺术二维码：</p>"
                + "<a href=\"" + url + "\">" + url + "</a>"
                + "</body>"
                + "</html>";


        try {
            // 创建默认的MimeMessage对象
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("请查收您的艺术二维码");
            message.setText(msg);
            message.setContent(htmlBody, "text/html;charset=UTF-8"); // 设置内容和编码

            // 发送邮件
            Transport.send(message);
            log.info("邮件发送成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int payFinish(String payId) {
        Integer num = artQRCodeMapper.selectUserOrderById(1);

        int i = num + 10;
        artQRCodeMapper.updateUserOrder(1, i);
        System.out.println("===========================");
        System.out.println("充值成功！");
        return 0;
    }


}
