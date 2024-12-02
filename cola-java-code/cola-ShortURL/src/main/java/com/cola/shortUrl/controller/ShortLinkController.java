package com.cola.shortUrl.controller;


import com.cola.common.annotation.Anonymous;
import com.cola.common.core.controller.BaseController;
import com.cola.common.core.domain.AjaxResult;
import com.cola.common.core.domain.dto.CommonBatchIdDto;
import com.cola.common.core.domain.dto.CommonIdDto;
import com.cola.common.core.page.TableDataInfo;
import com.cola.common.enums.SystemStateCodeEnum;
import com.cola.common.exception.ServiceException;
import com.cola.common.utils.SecurityUtils;
import com.cola.shortUrl.domain.dto.*;
import com.cola.shortUrl.domain.vo.TShortLinkVo;
import com.cola.shortUrl.domain.vo.UrlStatisticsVo;
import com.cola.shortUrl.service.ShortLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RestController
@RequestMapping("/shortUrl/url")
public class ShortLinkController extends BaseController {


    @Resource
    private ShortLinkService shortLinkService;


    @PreAuthorize("@ss.hasPermi('shortUrl:url:add')")
    @PostMapping(value = "/add")
    public TableDataInfo linkAdd(@Validated @RequestBody TShortLinkDto tShortLinkDto) {

        String shortLink = shortLinkService.generateShortLink(tShortLinkDto);
        return getDataTable(SystemStateCodeEnum.SUCCESS, shortLink);
    }

    @PreAuthorize("@ss.hasPermi('shortUrl:url:list')")
    @PostMapping(value = "/list")
    public TableDataInfo linkList(@RequestBody SearchTShortLinkDto searchTShortLinkDto) {

        startPage();
        List<TShortLinkVo> list = shortLinkService.findList(searchTShortLinkDto);
        return getDataTable(SystemStateCodeEnum.SUCCESS, list);
    }

    @PreAuthorize("@ss.hasPermi('shortUrl:url:del')")
    @PostMapping(value = "/del")
    public TableDataInfo delLink(@Validated @RequestBody CommonBatchIdDto commonBatchIdDto) {

        shortLinkService.delLink(commonBatchIdDto.getIdList());
        return getDataTable(SystemStateCodeEnum.SUCCESS);
    }

    @PreAuthorize("@ss.hasPermi('shortUrl:url:update')")
    @PostMapping(value = "/update")
    public TableDataInfo updateLink(@Validated @RequestBody UpdateTShortLinkDto updateTShortLinkDto) {

        shortLinkService.updateLink(updateTShortLinkDto);
        return getDataTable(SystemStateCodeEnum.SUCCESS);
    }

    @PreAuthorize("@ss.hasPermi('shortUrl:url:updateStatus')")
    @PostMapping(value = "/updateStatus")
    public TableDataInfo updateStatus(@Validated @RequestBody UpdateStatusTShortLinkDto updateTShortLinkDto) {

        shortLinkService.updateStatus(updateTShortLinkDto.getId(), updateTShortLinkDto.getStatus(), SecurityUtils.getUserId());
        return getDataTable(SystemStateCodeEnum.SUCCESS);
    }



    @PostMapping(value = "/statistics")
    public TableDataInfo statistics(@RequestBody CommonIdDto commonIdDto) {

        UrlStatisticsVo urlStatisticsVo = shortLinkService.urlStatistics(commonIdDto);
        return getDataTable(SystemStateCodeEnum.SUCCESS, urlStatisticsVo);
    }

    @PostMapping(value = "/encodeGo")
    @Anonymous
    public AjaxResult encodeGo(@RequestBody @Validated EncodeGoDto encodeGoDto) {

        String code = encodeGoDto.getCode().replace(" ", "");

        //4位数字字母组合校验
        String regex = "^[A-Za-z0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        boolean matches = matcher.matches();
        if (matches) {
            encodeGoDto.setCode(code);
        } else {
            throw new ServiceException("请入数字或字母组合格式的4位密码！");
        }

        String url = shortLinkService.encodeGo(encodeGoDto);
        RedirectView redirectView = new RedirectView(url);
//        // 301永久重定向，避免网络劫持
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        return success(url);
    }





}
