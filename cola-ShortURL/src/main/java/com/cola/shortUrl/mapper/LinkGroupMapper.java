package com.cola.shortUrl.mapper;


import com.cola.shortUrl.domain.LinkGroup;
import com.cola.shortUrl.domain.dto.SelGroupDto;
import com.cola.shortUrl.domain.vo.LinkGroupVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface LinkGroupMapper {


    int insertGroup(LinkGroup linkGroup);


    int delGroup(@Param("list") List<Long> list, @Param("userId") long userId);



    int updateGroup(LinkGroup linkGroup);


    List<LinkGroupVo> selectGroupList(SelGroupDto selGroupDto);


    int isOneself(@Param("userId") long userId, @Param("groupId")long groupId);


}
