package com.yuan.SecondsKill.dao;

import com.yuan.SecondsKill.domain.KillsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface KillsUserDao {

    @Select("select * from miaosha_user where id=#{id}")
    KillsUser selectById(@Param("id") long id);

    @Update("update miaosha_user set password=#{password} where id=#{id}")
    public void update(KillsUser user);

}
