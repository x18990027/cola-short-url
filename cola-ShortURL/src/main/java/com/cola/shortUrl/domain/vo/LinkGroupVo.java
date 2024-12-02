package com.cola.shortUrl.domain.vo;

import lombok.Data;
import java.util.Date;

@Data
public class LinkGroupVo {


    private long id;

    private String groupName;

    private long urlNum;

    private String remark;

    private Date CreateTime;



}
