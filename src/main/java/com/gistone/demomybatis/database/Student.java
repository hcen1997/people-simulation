package com.gistone.demomybatis.database;

import lombok.Data;

import java.io.Serializable;

/**
 * student
 *
 * @author
 */
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Integer birth;
}