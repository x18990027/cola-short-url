package com.cola.shortUrl.service;


import com.cola.common.core.domain.dto.CommonBatchIdDto;
import com.cola.common.core.domain.dto.CommonStrDto;
import com.cola.shortUrl.domain.dto.AddLinkGroupDto;
import com.cola.shortUrl.domain.dto.UpdateLinkGroupDto;
import com.cola.shortUrl.domain.dto.SelGroupDto;
import com.cola.shortUrl.domain.vo.LinkGroupVo;

import java.util.List;

public interface LinkGroupService {

    int addGroup(AddLinkGroupDto addLinkGroupDto);

    int delGroup(CommonBatchIdDto commonBatchIdDto);

    int updateGroup(UpdateLinkGroupDto linkGroupDto);

    List<LinkGroupVo> selectGroupList(SelGroupDto selGroupDto);



}
