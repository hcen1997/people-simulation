package com.gistone.demomybatis.database;

import org.springframework.stereotype.Repository;

@Repository
public interface DeathRateDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DeathRate record);

    int insertSelective(DeathRate record);

    DeathRate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeathRate record);

    int updateByPrimaryKey(DeathRate record);
}