package com.cola.shortUrl.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "domain_manager")
@Data
public class DomainManager {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private boolean sslStatus;

    @TableField(exist = false)
    private String ssl;

    private String domain;

    private Long createBy;

    private Date createTime;

    private Date updateTime;


}
