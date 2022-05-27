package com.lyz.mapper;

import com.lyz.pojo.Class;
import com.lyz.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface ClassMapper {

    //通过班级id获取班级名
    String getClassByClassId(@Param("id") Integer id);

    //确定班级是否存在
    Class getClassByName(@Param("name") String className);

    //通过className获取classId
    Integer getClassIdByClassName(@Param("name") String className);

    //获得班级人数
    Integer getNumberOfPeople(@Param("className") String className);

    //学生数量加一
    void addNumberOfPeople(@Param("classId") Integer classId);

    //通过班级id获得班级
    Class getClassById(@Param("id")Integer classId);

}
