package com.cola.shortUrl.controller;

import com.cola.common.core.controller.BaseController;
import com.cola.common.core.domain.dto.CommonBatchIdDto;
import com.cola.common.core.page.TableDataInfo;
import com.cola.common.enums.SystemStateCodeEnum;
import com.cola.shortUrl.domain.dto.DomainDto;
import com.cola.shortUrl.service.DomainManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/domain")
public class DomainManagerController extends BaseController {

    @Resource
    private DomainManagerService domainManagerService;


    @PreAuthorize("@ss.hasPermi('shortUrl:domain:list')")
    @PostMapping(value = "/list")
    public TableDataInfo domainList() {
        return getDataTable(domainManagerService.getDomainList());
    }


    @PreAuthorize("@ss.hasPermi('shortUrl:domain:add')")
    @PostMapping(value = "/add")
    public TableDataInfo domainAdd(@Validated @RequestBody DomainDto req) {
        domainManagerService.addDomain(req);
        return getDataTable(SystemStateCodeEnum.SUCCESS);
    }


    @PreAuthorize("@ss.hasPermi('shortUrl:domain:update')")
    @PostMapping(value = "/update")
    public TableDataInfo domainUpdate(@Validated @RequestBody DomainDto req) {
        Assert.notNull(req.getId(), "非法请求！");
        domainManagerService.updateDomain(req);
        return getDataTable(SystemStateCodeEnum.SUCCESS);
    }

    @PreAuthorize("@ss.hasPermi('shortUrl:domain:del')")
    @PostMapping(value = "/del")
    public TableDataInfo domainUpdate(@Validated @RequestBody CommonBatchIdDto req) {

        domainManagerService.delDomain(req);
        return getDataTable(SystemStateCodeEnum.SUCCESS);
    }


    @PreAuthorize("@ss.hasPermi('shortUrl:domain:getDomainMap')")
    @PostMapping(value = "/getDomainAll")
    public TableDataInfo getDomainAll() {
        return getDataTable(domainManagerService.getDomainAll());
    }


}
