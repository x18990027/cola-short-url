package com.cola.shortUrl.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateTShortLinkDto extends TShortLinkDto {

    @NotNull(message = "短链id不能为空！")
    private Long id;

    private Long userId;

    private String encodeLongLink;

    private boolean redStatus;


    private Date updateTime;

}
