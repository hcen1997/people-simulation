package com.gistone.demomybatis.web;

import com.gistone.demomybatis.database.InsertStatistic;
import com.gistone.demomybatis.database.InsertStatisticDao;
import com.gistone.demomybatis.database.Student;
import com.gistone.demomybatis.database.StudentDao;
import com.gistone.demomybatis.web.vo.InsertResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentDao studentDao;
    private final InsertStatisticDao insertStatisticDao;

    public StudentService(StudentDao studentDao, InsertStatisticDao insertStatisticDao) {
        this.studentDao = studentDao;
        this.insertStatisticDao = insertStatisticDao;
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

    public void generateStatistic(List<Integer> totalList) {
        for (Integer integer : totalList) {
            generateStatisticOne(integer);
        }
    }

    private void generateStatisticOne(Integer total) {
        InsertResult insertResult = insertN(total);

        InsertStatistic insertStatistic = new InsertStatistic();
        insertStatistic.setInsertCount(Long.valueOf(total));
        insertStatistic.setInsertTime(Math.toIntExact(insertResult.getInsertTime()));
        insertStatistic.setTableName("student");
        insertStatisticDao.insert(insertStatistic);
    }

    private InsertResult insertNForeach(Integer total, Integer batchSize) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

//        使用page类更新
        int page = total / batchSize + 1;
        for (int i = 0; i < page; i++) {
            if (i == page - 1) {
                int lastPack = total - page * batchSize;
                insertNForeachPack(lastPack);
            } else {
                insertNForeachPack(batchSize);
            }
        }

        stopWatch.stop();

        InsertResult insertResult = new InsertResult();
        insertResult.setInsertTime(stopWatch.getTotalTimeMillis());
        return insertResult;
    }

    private void insertNForeachPack(int n) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            students.add(Student.randOne());
        }
        studentDao.insertForeach(students);
    }

    public void generateStatisticForeach(Integer total, Integer batchSize) {

        InsertResult insertResult = insertNForeach(total, batchSize);

        InsertStatistic insertStatistic = new InsertStatistic();
        insertStatistic.setInsertCount(Long.valueOf(total));
        insertStatistic.setInsertTime(Math.toIntExact(insertResult.getInsertTime()));
        insertStatistic.setTableName("student");
        insertStatisticDao.insert(insertStatistic);
    }
}
