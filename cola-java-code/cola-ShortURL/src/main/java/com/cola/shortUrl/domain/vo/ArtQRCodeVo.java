package com.cola.shortUrl.domain.vo;


import lombok.Data;
import java.util.Date;

@Data
public class ArtQRCodeVo {


    private Long id;

    private String link;

    private String remark;

    private Date createTime;


}
