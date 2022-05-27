package com.lyz.service;

import com.lyz.mapper.ClassMapper;
import com.lyz.pojo.Class;
import com.lyz.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    public void setClassMapper(ClassMapper classMapper) {
        this.classMapper = classMapper;
    }

    //获取班级
    public String getClassByClassId(Integer id) {
        return classMapper.getClassByClassId(id);
    }

    //通过班级名获取班级
    public Class getClassByName(String className) {
        return classMapper.getClassByName(className);
    }

    //通过className获取classId
    public Integer getClassIdByClassName(String className) {
        return classMapper.getClassIdByClassName(className);
    }

    public Integer getNumberOfPeople(String className) {
        return classMapper.getNumberOfPeople(className);
    }

    public void addNumberOfPeople(Integer classId) {
        classMapper.addNumberOfPeople(classId);
    }

    public Class getClassById(Integer classId) {
        return classMapper.getClassById(classId);
    }


}
