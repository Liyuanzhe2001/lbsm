package com.lyz.service;

import com.lyz.pojo.Class;
import org.apache.ibatis.annotations.Param;

public interface ClassService {

    //通过班级id获取班级名
    String getClassByClassId(Integer id);

    //通过班级名获取班级
    Class getClassByName(String className);

    //通过className获取classId
    Integer getClassIdByClassName(String className);

    //获得班级人数
    Integer getNumberOfPeople(String className);

    //学生数量加一
    void addNumberOfPeople(Integer classId);

    //通过班级id获得班级
    Class getClassById(Integer classId);

}
