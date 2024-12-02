package com.cola.shortUrl.controller;


import com.cola.common.annotation.Log;
import com.cola.common.core.controller.BaseController;
import com.cola.common.core.domain.dto.CommonBatchIdDto;
import com.cola.common.core.page.TableDataInfo;
import com.cola.common.enums.BusinessType;
import com.cola.common.enums.SystemStateCodeEnum;
import com.cola.shortUrl.domain.dto.AddLinkGroupDto;
import com.cola.shortUrl.domain.dto.UpdateLinkGroupDto;
import com.cola.shortUrl.domain.dto.SelGroupDto;
import com.cola.shortUrl.domain.vo.LinkGroupVo;
import com.cola.shortUrl.service.LinkGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分组管理Controller
 *
 * @author cola
 * @since jdk1.8
 */
@Slf4j
@RestController
@RequestMapping("/shortUrl/group")
public class LinkGroupController extends BaseController {

    @Resource
    private LinkGroupService linkGroupService;


    @Log(title = "新增短网址分组", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('shortUrl:group:add')")
    @PostMapping(value = "/add")
    public TableDataInfo createGroup(@Validated @RequestBody AddLinkGroupDto addLinkGroupDto) {

        linkGroupService.addGroup(addLinkGroupDto);
        return getDataTable(SystemStateCodeEnum.SUCCESS);
    }

    @Log(title = "删除短网址分组", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('shortUrl:group:del')")
    @PostMapping(value = "/del")
    public TableDataInfo delGroup(@Validated @RequestBody CommonBatchIdDto commonBatchIdDto) {

        linkGroupService.delGroup(commonBatchIdDto);
        return getDataTable(SystemStateCodeEnum.SUCCESS);
    }


    @Log(title = "更新短网址分组", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('shortUrl:group:update')")
    @PostMapping(value = "/update")
    public TableDataInfo updateGroup(@Validated @RequestBody UpdateLinkGroupDto linkGroupDto) {

        if (linkGroupDto.getId() == null) {
            return getDataTable(SystemStateCodeEnum.ERROR_REQUEST_ILLEGAL);
        }
        linkGroupService.updateGroup(linkGroupDto);
        return getDataTable(SystemStateCodeEnum.SUCCESS);
    }


    @Log(title = "查询短网址分组", businessType = BusinessType.FORCE)
    @PreAuthorize("@ss.hasPermi('shortUrl:group:list')")
    @PostMapping(value = "/list")
    public TableDataInfo selGroup(@RequestBody SelGroupDto selGroupDto) {
        startPage();
        List<LinkGroupVo> list = linkGroupService.selectGroupList(selGroupDto);
        return getDataTable(SystemStateCodeEnum.SUCCESS, list);
    }


    @PostMapping(value = "/allList")
    public TableDataInfo selAllGroup() {

        List<LinkGroupVo> list = linkGroupService.selectGroupList(new SelGroupDto());

        List<Map<String,Object>> listVo= new ArrayList<>();
        if(list!=null &&list.size()>0) {
            for (LinkGroupVo linkGroupVo : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", linkGroupVo.getId());
                map.put("groupName", linkGroupVo.getGroupName());
                listVo.add(map);
            }
        }

        return getDataTable(SystemStateCodeEnum.SUCCESS, listVo);
    }

}
