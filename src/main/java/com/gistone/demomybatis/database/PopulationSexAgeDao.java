package com.gistone.demomybatis.database;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopulationSexAgeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PopulationSexAge record);

    int insertSelective(PopulationSexAge record);

    PopulationSexAge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PopulationSexAge record);

    int updateByPrimaryKey(PopulationSexAge record);

    List<PopulationSexAge> queryByYear(@Param("year") int year);
}