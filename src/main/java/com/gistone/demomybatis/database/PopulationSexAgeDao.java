package com.gistone.demomybatis.database;

import org.springframework.stereotype.Repository;

@Repository
public interface PopulationSexAgeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PopulationSexAge record);

    int insertSelective(PopulationSexAge record);

    PopulationSexAge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PopulationSexAge record);

    int updateByPrimaryKey(PopulationSexAge record);
}