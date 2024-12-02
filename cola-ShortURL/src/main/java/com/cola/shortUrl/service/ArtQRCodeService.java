package com.cola.shortUrl.service;

import com.cola.shortUrl.domain.dto.ArtQRCodeDto;
import com.cola.shortUrl.domain.vo.ArtQRCodeVo;

import java.util.List;

public interface ArtQRCodeService {




    String generateCode(ArtQRCodeDto artQRCodeDto);


     List<ArtQRCodeVo> qrList();


    int  payFinish(String payId);





}
