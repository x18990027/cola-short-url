package com.cola.shortUrl.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TShortLink {

    private long id;

    private String shortLink;

    private String urlKey;

    private Long domainId;

    private String longLink;

    private String encodeLongLink;

    private int status;

    private boolean redStatus;

    private Long  numLimit;

    private Long  ipLimit;

    private String numLimitLink;

    private String accessPassword;

    private long userId;

    private long groupId;

    private String remark;

    private long visitsNum;

    @JsonFormat( pattern = "yyyy-MM-dd")
    private Date expiryTime;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date updateTime;


}
