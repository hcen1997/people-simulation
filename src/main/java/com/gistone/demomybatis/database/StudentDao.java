package com.gistone.demomybatis.database;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {
    int insert(Student record);

    int insertSelective(Student record);

    int insertForeach(@Param("studentList") List<Student> studentList);
}