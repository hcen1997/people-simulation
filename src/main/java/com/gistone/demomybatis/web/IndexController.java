package com.gistone.demomybatis.web;


import com.gistone.demomybatis.database.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class IndexController {

    private final StudentService studentService;

    public IndexController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/insertOne")
    public long in10() {
        return studentService.insert(Student.randOne());
    }
}
