package com.gistone.demomybatis.web;

import com.gistone.demomybatis.database.Student;
import com.gistone.demomybatis.database.StudentDao;
import com.gistone.demomybatis.web.vo.InsertResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

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

    public InsertResult insertN(int n) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        InsertResult insertResult = new InsertResult();
        for (int i = 0; i < n; i++) {
            Long insert = insert(Student.randOne());
            insertResult.add(insert);
        }
        stopWatch.stop();
        insertResult.setInsertTime(stopWatch.getTotalTimeMillis());


        return insertResult;
    }
}
