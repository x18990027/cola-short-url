package com.cola.shortUrl.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DomainDto {

    private Long id;

    @NotBlank(message = "名称不能为空！")
    private String name;

    private Boolean sslStatus =false;

    @NotBlank(message = "域名不能为空！")
    private String domain;


}
