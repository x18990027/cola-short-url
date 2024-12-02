package com.cola.shortUrl.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TShortLinkDto {

    @NotNull(message = "需要缩短的链接不能为空")
    private String longLink;

    private Integer domainId;

    private Long  numLimit;

    private Long  ipLimit;

    private String numLimitLink;

    private String accessPassword;

    @NotNull(message = "分组不能为空")
    private Long groupId;

    private String remark;

    private boolean redStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryTime;

}
