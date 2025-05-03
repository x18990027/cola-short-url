package com.cola.shortUrl.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class StatisticsVo {

    private Long id;

    private String shortLink;

    private String ip;

    private String address;

    private String terminal;

    private Date createTime;

}
