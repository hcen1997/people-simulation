package com.gistone.demomybatis.web.vo;

import lombok.Data;

@Data

public class AgeSexPeople {
    private Integer year;
    private Integer age;
    private Long man;
    private Long woman;

    public void increaseOneYear() {
        year = year + 1;
        age = age + 1;
    }

    public AgeSexPeople() {
    }

    public AgeSexPeople(Integer year, Integer age, Long man, Long woman) {
        this.year = year;
        this.age = age;
        this.man = man;
        this.woman = woman;
    }

    public Long getSum() {
        return man + woman;
    }

    public AgeSexPeople copy() {
        try {
            return (AgeSexPeople) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
