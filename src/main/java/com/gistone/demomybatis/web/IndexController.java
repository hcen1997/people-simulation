package com.gistone.demomybatis.web;


import com.gistone.demomybatis.web.vo.InsertResult;
import com.gistone.demomybatis.web.vo.PackInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class IndexController {

    private final StudentService studentService;
    @Autowired
    private DataFileService dataFileService;

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

    @PostMapping("/generateStatisticForeach1w")
    public String g51(@RequestBody PackInfo packInfo) {
        studentService.generateStatisticForeach(packInfo.getTotal(), 10000);
        return "感谢等待, pack生成数据完成, 请在数据库查看";
    }


    @GetMapping("/saveBornData")
    public String g58() {
        dataFileService.insertBornDataToDb();
        return "感谢等待, 数据处理完成, 请在数据库查看";
    }

    @GetMapping("/insert2010PopulationDataToDb")
    public String g67() {
        dataFileService.insert2010PopulationDataToDb();
        return "感谢等待, 数据处理完成, 请在数据库查看";
    }


}
