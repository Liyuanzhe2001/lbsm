package com.lyz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Records {

    private static final long serialVersionUID = 1L;

    //唯一id
    private Integer id;

    //操作者id
    private Integer userId;

    //操作类型
    private String type;

    //被操作的学生名
    private String studentName;

    //操作位置
    private String position;

    //操作时间
    private Date time;

}
