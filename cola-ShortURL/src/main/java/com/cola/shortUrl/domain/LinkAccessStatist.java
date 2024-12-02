package com.cola.shortUrl.domain;

import lombok.Data;

import java.util.Date;

@Data
public class LinkAccessStatist {

    private long id;

    private long shortLinkId;

    private String ip;

    private String address;

    private int terminal;

    private String userAgent;

    private long userBy;

    private Date createTime;

}
