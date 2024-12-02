package com.cola.common.core.domain.dto;


import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class CommonStrDto {



    @NotEmpty(message = "必填项不能为空！")
    private String str;


}
