package com.cola.shortUrl.service;

import com.cola.common.core.domain.dto.CommonBatchIdDto;
import com.cola.shortUrl.domain.DomainManager;
import com.cola.shortUrl.domain.dto.DomainDto;
import com.cola.shortUrl.domain.vo.DomainListVO;

import java.util.List;

public interface DomainManagerService {

    List<DomainManager> getDomainList();

    void addDomain(DomainDto req);

    void updateDomain(DomainDto req);

    void delDomain(CommonBatchIdDto req);

    List<DomainListVO> getDomainAll();
}
