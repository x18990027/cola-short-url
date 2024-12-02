package com.cola.shortUrl.domain;


import lombok.Data;

import java.util.Date;

@Data
public class ArtQrCode {

    private Long id;

    private String content;

    private Long createBy;

    private String urlKey;

    private String shortLink;

    private String imageUrl;

    private String remark;

    private Date createTime;


}
