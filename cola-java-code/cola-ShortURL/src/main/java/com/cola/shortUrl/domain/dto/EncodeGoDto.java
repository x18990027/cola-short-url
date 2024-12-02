package com.cola.shortUrl.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EncodeGoDto {

    private Long id;


    @NotBlank(message = "输入密码不能为空")
    @NotNull(message = "输入密码不能为空")
    private String code;


}
