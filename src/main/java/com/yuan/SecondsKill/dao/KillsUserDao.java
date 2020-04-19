package com.yuan.SecondsKill.dao;

import com.yuan.SecondsKill.domain.KillsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface KillsUserDao {

    @Select("select * from miaosha_user where id=#{id}")
    KillsUser selectById(@Param("id") long id);


}
