package com.cola.shortUrl.service.impl;

import com.cola.common.core.domain.dto.CommonBatchIdDto;
import com.cola.common.enums.SystemStateCodeEnum;
import com.cola.common.exception.ServiceException;
import com.cola.common.utils.SecurityUtils;
import com.cola.common.utils.bean.BeanUtils;
import com.cola.shortUrl.domain.LinkGroup;
import com.cola.common.core.domain.dto.CommonStrDto;
import com.cola.shortUrl.domain.dto.AddLinkGroupDto;
import com.cola.shortUrl.domain.dto.UpdateLinkGroupDto;
import com.cola.shortUrl.domain.dto.SelGroupDto;
import com.cola.shortUrl.domain.vo.LinkGroupVo;
import com.cola.shortUrl.mapper.LinkGroupMapper;
import com.cola.shortUrl.mapper.ShortLinkMapper;
import com.cola.shortUrl.service.LinkGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LinkGroupServiceImpl implements LinkGroupService {

    @Resource
    private LinkGroupMapper linkGroupMapper;

    @Resource
    private ShortLinkMapper shortLinkMapper;


    @Override
    public int addGroup(AddLinkGroupDto addLinkGroupDto) {
        LinkGroup linkGroup = new LinkGroup();
        BeanUtils.copyProperties(addLinkGroupDto, linkGroup);
        linkGroup.setCreateTime(new Date());
        linkGroup.setUpdateTime(new Date());
        linkGroup.setUserId(SecurityUtils.getUserId());
        return linkGroupMapper.insertGroup(linkGroup);
    }

    @Override
    public int delGroup(CommonBatchIdDto commonBatchIdDto) {

        int urlNum = shortLinkMapper.selectGroupByNum(SecurityUtils.getUserId(), commonBatchIdDto.getIdList());
        if (urlNum > 0) {
            throw new ServiceException(SystemStateCodeEnum.ERROR_DELETE_GROUP_BY_URL.getMsg());
        }

        return linkGroupMapper.delGroup(commonBatchIdDto.getIdList(), SecurityUtils.getUserId());
    }


    @Override
    public int updateGroup(UpdateLinkGroupDto updateGroup) {

        LinkGroup linkGroup = new LinkGroup();
        BeanUtils.copyProperties(updateGroup, linkGroup);
        linkGroup.setUserId(SecurityUtils.getUserId());

        return linkGroupMapper.updateGroup(linkGroup);
    }

    @Override
    public List<LinkGroupVo> selectGroupList(SelGroupDto selGroupDto) {
        selGroupDto.setUserId(SecurityUtils.getUserId());
        return linkGroupMapper.selectGroupList(selGroupDto);
    }


}
