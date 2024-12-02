package com.cola.shortUrl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cola.common.core.domain.dto.CommonBatchIdDto;
import com.cola.common.core.redis.RedisCache;
import com.cola.common.utils.PageUtils;
import com.cola.common.utils.SecurityUtils;
import com.cola.common.utils.bean.BeanUtils;
import com.cola.shortUrl.domain.DomainManager;
import com.cola.shortUrl.domain.dto.DomainDto;
import com.cola.shortUrl.domain.vo.DomainListVO;
import com.cola.shortUrl.mapper.DomainManagerMapper;
import com.cola.shortUrl.service.DomainManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DomainManagerServiceImpl implements DomainManagerService {


    @Resource
    private DomainManagerMapper domainManagerMapper;

    @Resource
    private RedisCache redisCache;

    private static String domainListKey = "domainList";

    @Override
    public List<DomainManager> getDomainList() {
        PageUtils.startPage();
        List<DomainManager> domainManagers = domainManagerMapper.selectList(new LambdaQueryWrapper<DomainManager>().orderByDesc(DomainManager::getCreateTime));
        if (CollectionUtils.isNotEmpty(domainManagers)) {
            domainManagers.forEach(s -> {
                s.setSsl(s.isSslStatus() ? "是" : "否");
            });
        }
        return domainManagers;
    }

    @Override
    public void addDomain(DomainDto req) {

        DomainManager domainManager = new DomainManager();
        BeanUtils.copyProperties(req, domainManager);
        domainManager.setCreateTime(new Date());
        domainManager.setSslStatus(req.getSslStatus().booleanValue());
        domainManager.setCreateBy(SecurityUtils.getUserId());

        redisCache.deleteObject(domainListKey);
        domainManagerMapper.insert(domainManager);
    }

    @Override
    public void updateDomain(DomainDto req) {
        DomainManager domainManager = new DomainManager();
        BeanUtils.copyProperties(req, domainManager);
        domainManager.setSslStatus(req.getSslStatus().booleanValue());
        domainManager.setUpdateTime(new Date());

        redisCache.deleteObject(domainListKey);
        domainManagerMapper.updateById(domainManager);
    }

    @Override
    public void delDomain(CommonBatchIdDto req) {
        redisCache.deleteObject(domainListKey);
        domainManagerMapper.deleteBatchIds(req.getIdList());
    }

    @Override
    public List<DomainListVO> getDomainAll() {


        List<DomainListVO> result = redisCache.getCacheObject(domainListKey);

        if (CollectionUtils.isNotEmpty(result)) {
            return result;
        }

        List<DomainManager> domainManagers = domainManagerMapper.selectList(new LambdaQueryWrapper<DomainManager>().orderByDesc(DomainManager::getCreateTime));
        if (CollectionUtils.isEmpty(domainManagers)) {
            return null;
        }

        result = new ArrayList<>();
        for (DomainManager s : domainManagers) {
            DomainListVO vo = new DomainListVO();
            vo.setDomainId(s.getId());
            vo.setDomainName(s.getName());
            result.add(vo);
        }


        redisCache.setCacheObject(domainListKey, result);
        return result;
    }
}
