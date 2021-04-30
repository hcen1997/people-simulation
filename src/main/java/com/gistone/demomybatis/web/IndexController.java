package com.gistone.demomybatis.web;


import com.gistone.demomybatis.web.vo.InsertResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        InsertResult insertResultAll = studentService.insertN(1);
        return insertResultAll;
    }

    @GetMapping("/insertTenStudent")
    public InsertResult in34() {
        InsertResult insertResultAll = studentService.insertN(10);
        return insertResultAll;
    }

    @GetMapping("/insertNStudent/{n}")
    public InsertResult in51(@PathVariable("n") int n) {
        InsertResult insertResultAll = studentService.insertN(n);
        return insertResultAll;
    }


}
