package com.cola.shortUrl.controller;


import com.alibaba.fastjson2.JSON;
import com.cola.common.annotation.Anonymous;
import com.cola.common.core.controller.BaseController;
import com.cola.common.core.domain.AjaxResult;
import com.cola.common.core.domain.dto.CommonBatchIdDto;
import com.cola.common.core.domain.entity.SysUser;
import com.cola.common.core.page.TableDataInfo;
import com.cola.common.enums.SystemStateCodeEnum;
import com.cola.common.utils.SecurityUtils;
import com.cola.shortUrl.domain.dto.ArtQRCodeDto;
import com.cola.shortUrl.domain.vo.ArtQRCodeVo;
import com.cola.shortUrl.mapper.ArtQRCodeMapper;
import com.cola.shortUrl.service.ArtQRCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/artQRCode")

public class ArtQRCodeController extends BaseController {

    @Resource
    private ArtQRCodeService artQRCodeService;

    @Resource
    private ArtQRCodeMapper artQRCodeMapper;


    @PostMapping(value = "/delCode")
    public AjaxResult delCode(@RequestBody CommonBatchIdDto commonBatchIdDto) {
        artQRCodeMapper.delByIds(commonBatchIdDto.getIdList(), SecurityUtils.getUserId());
        return success();
    }


    @PostMapping(value = "/userData")
    public AjaxResult userData() {
        SysUser sysUser = artQRCodeMapper.selUserData(SecurityUtils.getUserId());
        return success(sysUser);
    }


    @PostMapping(value = "/callback")
    public TableDataInfo invest(@RequestBody String message) {


        String content = JSON.parseObject(message).getString("content");
        if (StringUtils.isBlank(content)) {
            return getDataTable(SystemStateCodeEnum.ERROR_REQUEST_ILLEGAL);
        }
        //截取支付的payId,“<msg> <appmsg...”开头不能少，防止别人发特定的消息混淆视听
        if (content.startsWith("<msg> <appmsg appid=\"\" sdkver=\"0\">     <title><![CDATA[微信支付收款")) {
            Matcher matcher = Pattern.compile("\n收款方备注(\\d+)\n").matcher(content);
            while (matcher.find()) {
                String payId = matcher.group(1);
                artQRCodeService.payFinish(payId);
                System.out.println("***************************");
                System.out.println("id的值为" + payId);
                break;
            }
        }

        return getDataTable(SystemStateCodeEnum.SUCCESS);

    }


    @PostMapping(value = "/add")
    public TableDataInfo generateQrCode(@RequestBody @Validated ArtQRCodeDto artQRCodeDto) {

        String generateCode = artQRCodeService.generateCode(artQRCodeDto);
        return getDataTable(SystemStateCodeEnum.SUCCESS, generateCode);
    }


    @PostMapping(value = "/list")
    public TableDataInfo qrCodeList() {

        List<ArtQRCodeVo> list = artQRCodeService.qrList();
        return getDataTable(SystemStateCodeEnum.SUCCESS, list);
    }


}
