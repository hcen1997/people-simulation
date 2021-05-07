package com.gistone.demomybatis.database;

import lombok.Data;

import java.io.Serializable;

/**
 * population_sex_age
 *
 * @author
 */
@Data
public class PopulationSexAge implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * 统计数据年份
     */
    private Integer dataYear;
    private Integer fromAge;
    private Integer toAge;
    private Long sum;
    private Long man;
    private Long woman;

    public PopulationSexAge(int year, String[] data) {
        dataYear = year;
        sum = Long.valueOf(data[0]);
        man = Long.valueOf(data[1]);
        woman = Long.valueOf(data[2]);
    }

    public void setFromAndTo(int from, int to) {
        setFromAge(from);
        setToAge(to);
    }
}