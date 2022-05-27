package com.lyz.service;

import com.lyz.pojo.Information;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InformationService {

    //将学生信息输入
    void insertStudentsInformation(List<Information> students);

    //获得指定班级所有学生信息（分页）(成绩排序)
    List<Information> getPageClassInformation(Integer page, String className);

    //获得指定班级所有学生信息（原数据）
    List<Information> getClassAllInformation(String className);

    //根据姓名查询学生
    List<Information> getInformationByName(String name, String className);

    //根据学号查询学生
    List<Information> getInformationById(String studentId, String className);

    //添加新学生信息
    void addNewStudentInformation(String studentNumber, String studentName, String studentIdNumber, String studentId, String className, String grade, String studentRank, String instructor, String monitor, String leagueBranchSecretary, String numberOfPeople, String branch);

    //获得指定班级所有学生信息（不分页）
    List<Information> getAllClassInformation(String className);

    //获得学生的排名
    Integer getStudentRanking(String student, String className);

    //修改学生的学分
    void changeStudentScore(Map map);

    //记录学生总学分
    void updateStudentTotalScore(String className, String studentName);

    //排名加一
    void addOneRank(String studentName, String className);

    //排名减一
    void cutOneRank(String studentName, String className);

    //删除所有信息
    void deleteAllInformation(String className);

    //修改平均分
    void changeAverage(String className);

    //修改学生总数
    void changeNumberOfPeople(String className);

    //修改学生个人信息
    void changeStudentInfo(Map map);
}
