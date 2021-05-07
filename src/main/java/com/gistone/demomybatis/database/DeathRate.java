package com.gistone.demomybatis.database;

import lombok.Data;

import java.io.Serializable;

/**
 * death_rate
 *
 * @author
 */
@Data
public class DeathRate implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer age;
    private String rateManNotOld1;
    private String rateWomanNotOld1;
    private String rateManNotOld2;
    private String rateWomanNotOld2;
    private String rateManOld;
    private String rateWomanOld;
}