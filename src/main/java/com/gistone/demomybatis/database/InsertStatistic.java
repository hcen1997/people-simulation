package com.gistone.demomybatis.database;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * insert_statistic
 *
 * @author
 */
@Data
public class InsertStatistic implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String tableName;
    private Long insertCount;
    private Integer insertTime;
    private Date createTime;
}