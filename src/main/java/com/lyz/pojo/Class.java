package com.lyz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {

    private static final long serialVersionUID = 1L;

    //唯一id
    private Integer id;

    //班级名
    private String name;

    //年级
    private String grade;

    //辅导员
    private String instructor;

    //班长
    private String monitor;

    //团支书
    private String leagueBranchSecretary;

    //人数
    private String numberOfPeople;

    //学院
    private String branch;
}
