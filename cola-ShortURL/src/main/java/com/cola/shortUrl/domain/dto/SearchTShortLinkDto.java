package com.cola.shortUrl.domain.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class SearchTShortLinkDto {

    private long userId;

    private Long groupId;

    private String shortLink;

    private String longLink;

    private Boolean status;

    private List<Date> dateRange;

    private Date startTime;

    private Date endTime;

}
