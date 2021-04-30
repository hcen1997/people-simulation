package com.gistone.demomybatis.web.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InsertResult {

    private List<Long> insertIds;
    private Long insertTime;


    public InsertResult(Long insertTime, Long... ids) {
        ArrayList<Long> list = new ArrayList<>();
        for (Long id : ids) {
            list.add(id);
        }
        this.insertIds = list;
        this.insertTime = insertTime;
    }
}
