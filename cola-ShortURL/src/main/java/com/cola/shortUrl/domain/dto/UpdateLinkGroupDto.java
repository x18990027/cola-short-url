package com.cola.shortUrl.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateLinkGroupDto {



    private Long id;


    @NotNull(message = "分组名称不能为空")
    private String groupName;


    private String remark;





}
