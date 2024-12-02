package com.cola.shortUrl.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UrlStatisticsVo {


    //今日新增访问量
    private long todayAddNum;

    //访问总量
    private long visitsTotal;

    //今日新增访问ip
    private long todayAddIpNum;

    //访问ip总量
    private long ipTotal;

    //今天的访问数据
    private List<Long> todayNumList;

    //昨天天的访问数据
    private List<Long> yesterdayNumList;

    //最近7天的访问数据
    private List<Long> daysStatistics;

    //终端分布map
    private List<Map<String,Object>>  terminalList;

    //终端分布map
    private List<Map<String,Object>>  cityList;


}
