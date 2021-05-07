package com.gistone.demomybatis.database;

import org.springframework.stereotype.Repository;

@Repository
public interface BirthPeopleCountDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BirthPeopleCount record);

    int insertSelective(BirthPeopleCount record);

    BirthPeopleCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BirthPeopleCount record);

    int updateByPrimaryKey(BirthPeopleCount record);
}