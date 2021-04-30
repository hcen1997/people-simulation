package com.gistone.demomybatis.database;

public interface StudentDao {
    int insert(Student record);

    int insertSelective(Student record);
}