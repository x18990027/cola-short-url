package com.cola.shortUrl.mapper;


import com.cola.common.core.domain.entity.SysUser;
import com.cola.shortUrl.domain.ArtQrCode;
import com.cola.shortUrl.domain.vo.ArtQRCodeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArtQRCodeMapper {

    Integer selectUserOrderById(long userId);

    Integer updateUserOrder(@Param("userId") long userId, @Param("num") Integer num);

    @Select("select  url_key from  art_qr_code where  url_key =#{urlKey} ")
    String selectByUrlKey(String urlKey);

    Integer insertArtQRCode(ArtQrCode artQrCode);


    List<ArtQRCodeVo> qrList(long  userId);

    SysUser selUserData(long userId);

    String selByKey(String urlKey);

    int delByIds(@Param("list") List<Long> list, @Param("userId") long userId);


}
