package com.cola.shortUrl.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddLinkGroupDto {

    @NotEmpty(message = "分组名称不能为空！")
    private String groupName;

    private String remark;
}
