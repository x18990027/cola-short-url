package com.cola.common.core.domain.dto;


import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class CommonIdDto {


    @NotBlank
    private Long id;


}
