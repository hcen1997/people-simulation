package com.gistone.demomybatis.web;


import com.gistone.demomybatis.database.Student;
import com.gistone.demomybatis.web.vo.InsertResult;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class IndexController {

    private final StudentService studentService;

    public IndexController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/insertOneStudent")
    public InsertResult in10() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int insert = studentService.insert(Student.randOne());
        stopWatch.stop();
        return new InsertResult(
                stopWatch.getTotalTimeMillis(),
                Long.valueOf(insert)
        );
    }

    @GetMapping("/insertTenStudent")
    public InsertResult in34() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int total = 10;
        InsertResult insertResult = new InsertResult();
        for (int i = 0; i < total; i++) {
            int insert = studentService.insert(Student.randOne());
            insertResult.add((long) insert);
        }
        stopWatch.stop();
        insertResult.setInsertTime(stopWatch.getTotalTimeMillis());

        return insertResult;
    }
}
