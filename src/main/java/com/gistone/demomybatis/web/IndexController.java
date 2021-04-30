package com.gistone.demomybatis.web;


import com.gistone.demomybatis.web.vo.InsertResult;
import com.gistone.demomybatis.web.vo.PackInfo;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @PostMapping("/generateStatistic")
    public String g37(@RequestBody List<Integer> totalList) {
        studentService.generateStatistic(totalList);
        return "感谢等待, 生成数据完成, 请在数据库查看";
    }

    @PostMapping("/generateStatisticForeach")
    public String g45(@RequestBody PackInfo packInfo) {
        studentService.generateStatisticForeach(packInfo.getTotal(), packInfo.getPackSize());
        return "感谢等待, pack生成数据完成, 请在数据库查看";
    }


}
