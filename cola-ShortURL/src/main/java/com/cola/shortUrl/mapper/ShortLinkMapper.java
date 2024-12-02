package com.cola.shortUrl.mapper;


import com.cola.shortUrl.domain.TShortLink;
import com.cola.shortUrl.domain.dto.SearchTShortLinkDto;
import com.cola.shortUrl.domain.dto.UpdateTShortLinkDto;
import com.cola.shortUrl.domain.vo.TShortLinkVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.Date;
import java.util.List;


public interface ShortLinkMapper {


    TShortLink selectById(@Param("id") long id);

    TShortLink selectByShortLink(@Param("urlKey") String urlKey);

    TShortLink selectByOneself(@Param("urlKey") String urlKey );

    int insertShortUrl(TShortLink tShortLink);

    int selectGroupByNum(@Param("userId") long userId, @Param("list") List<Long> groupId);

    Date selectUserById(long uerId);

    int updateNum(@Param("id")long id, @Param("visitsNum")long visitsNum);

    List<TShortLinkVo> findList(SearchTShortLinkDto searchTShortLinkDto);

    int delLink(@Param("list") List<Long> list, @Param("userId") long userId);

    int updateLink(UpdateTShortLinkDto updateTShortLinkDto);

    int updateStatus(@Param("id") Long id,@Param("status") boolean  status,@Param("userId") Long userId);

}
