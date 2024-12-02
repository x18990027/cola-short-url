package com.cola.common.core.domain.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CommonBatchStrDto {


    @NotEmpty(message = "不能为空！")
    private List<String> strList;


}
