package com.gistone.demomybatis.web;

import com.gistone.demomybatis.database.Student;
import com.gistone.demomybatis.database.StudentDao;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Long insert(Student student) {
        int insert = studentDao.insert(student);
        return student.getId();
    }
}
