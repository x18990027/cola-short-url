package com.cola.shortUrl.domain.dto;


import lombok.Data;
import javax.validation.constraints.NotNull;
@Data
public class ArtQRCodeDto {



        @NotNull(message = "类型不能为空！")
        private String type;

        @NotNull(message = "内容不能为空")
        private String content;

        @NotNull(message = "背景描述不能为空")
        private String prompt;

        //风格
        private Integer presetType;

        //二维码宽高比
        private String aspectRatio;

        //二维码像素风格
        private String pixelStyle;

        @NotNull(message = "接收邮箱不能为空")
        private String email;

        private String position;

        private String remark;










}
