package com.gistone.demomybatis.database;

import org.springframework.stereotype.Repository;

@Repository
public interface InsertStatisticDao {
    int deleteByPrimaryKey(Long id);

    int insert(InsertStatistic record);

    int insertSelective(InsertStatistic record);

    InsertStatistic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InsertStatistic record);

    int updateByPrimaryKey(InsertStatistic record);
}