package com.gistone.demomybatis.database;

import com.gistone.demomybatis.Util;
import lombok.Data;

import java.time.Year;
import java.util.concurrent.ThreadLocalRandom;

/**
 * student
 *
 * @author
 */
@Data
public class Student {
    private Long id;
    private String name;
    private Integer birth;

    public Student(String name, Integer birth) {
        this.name = name;
        this.birth = birth;
    }

    public static Student randOne() {
        String name = Util.randName();
        int age = ThreadLocalRandom.current().nextInt(0, 100);
        int thisYear = Year.now().getValue();
        Student student = new Student(name, thisYear - age);
        return student;
    }
}