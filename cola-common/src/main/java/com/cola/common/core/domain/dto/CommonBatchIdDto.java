package com.cola.common.core.domain.dto;


import lombok.Data;


import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class CommonBatchIdDto {

    @NotEmpty(message = "不能为空！")
    private List<Long> idList;


}
