package com.cola.shortUrl.domain;


import lombok.Data;

import java.util.Date;

/**
 * 短链分组实体类
 *
 * @author cola
 * @since jdk1.8
 */
@Data
public class LinkGroup {


    private long id;

    private String groupName;

    private String remark;

    private long userId;

    private Date createTime;

    private Date updateTime;


}
