package com.gistone.demomybatis.database;

import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao {
    int insert(Student record);

    int insertSelective(Student record);
}