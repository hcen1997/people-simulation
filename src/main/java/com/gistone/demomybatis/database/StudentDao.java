package com.gistone.demomybatis.database;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {
    int insert(Student record);

    int insertSelective(Student record);

    int insertForeach(List<Student> studentList);
}