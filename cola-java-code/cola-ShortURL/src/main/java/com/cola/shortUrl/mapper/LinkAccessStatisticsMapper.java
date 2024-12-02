package com.cola.shortUrl.mapper;

import com.cola.shortUrl.domain.LinkAccessStatist;
import com.cola.shortUrl.domain.vo.UrlStatisticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface LinkAccessStatisticsMapper {


    @Select("select ip from link_access_statistics where short_link_id=#{id}")
    List<String> ipList(long id);

    Integer ipTotal(long urlId);

    @Select("select count(*) from link_access_statistics where short_link_id=#{shortLinkId} ")
    long selectUrlCount(long shortLinkId);

    int insertLog(LinkAccessStatist linkAccessStatist);

    List<LinkAccessStatist> selectByTime(@Param("id") Long id,@Param("userId") long userId, @Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);

    List<Long> daysStatistics(@Param("id") Long id,@Param("userId")long userId);


    List<Map<String, Object>> selectTerminal(@Param("id") Long id,@Param("userId")long userId);

    UrlStatisticsVo sumUp(@Param("id") Long id,@Param("userId") long userId, @Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);

    List<Map<String, Object>> selectCity(@Param("id") Long id,@Param("userId")long userId);

}
