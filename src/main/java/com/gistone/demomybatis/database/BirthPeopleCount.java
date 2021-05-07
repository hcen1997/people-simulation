package com.gistone.demomybatis.database;

import lombok.Data;

import java.io.Serializable;

/**
 * birth_people_count
 *
 * @author
 */
@Data
public class BirthPeopleCount implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer year;
    /**
     * 单位, 万人
     */
    private Integer peopleW;

    public BirthPeopleCount(String[] strings) {
        if (strings.length == 2) {
            year = Integer.valueOf(strings[0]);
            peopleW = Integer.valueOf(strings[1]);
        } else return;
    }
}