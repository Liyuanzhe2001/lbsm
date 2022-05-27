package com.lyz.mapper;

import com.lyz.pojo.Information;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InformationMapper {

    //将学生信息输入
    void insertStudentsInformation(List<Information> students);

    //获得指定班级所有学生信息（分页）(成绩排序)
    List<Information> getPageClassInformation(@Param("page") Integer page, @Param("className") String className);

    //获得指定班级所有学生信息（原数据）
    List<Information> getClassAllInformation(@Param("className") String className);

    //根据姓名查询学生
    List<Information> getInformationByName(@Param("name") String name, @Param("className") String className);

    //根据学号查询学生
    List<Information> getInformationById(@Param("studentId") String studentId, @Param("className") String className);

    //添加新学生信息
    void addNewStudentInformation(@Param("number") String studentNumber, @Param("name") String studentName, @Param("idNumber") String studentIdNumber, @Param("studentId") String studentId, @Param("className") String className, @Param("grade") String grade, @Param("classRanking") String classRank, @Param("instructor") String instructor, @Param("monitor") String monitor, @Param("leagueBranchSecretary") String leagueBranchSecretary, @Param("numberOfPeople") String numberOfPeople, @Param("branch") String branch);

    //获得指定班级所有学生信息（不分页）
    List<Information> getAllClassInformation(@Param("className") String className);

    //获得学生的排名
    Integer getStudentRanking(@Param("student_name") String student, @Param("class") String className);

    //修改学生的学分
    void changeStudentScore(Map map);

    //记录学生总学分
    void updateStudentTotalScore(@Param("class_name") String className, @Param("student_name") String studentName);

    //排名加一
    void addOneRank(@Param("student_name") String studentName, @Param("class_name") String className);

    //排名减一
    void cutOneRank(@Param("student_name") String studentName, @Param("class_name") String className);

    //删除所有信息
    void deleteAllInformation(@Param("class_name") String className);

    //修改平均分
    void changeAverage(@Param("class_name") String className);

    //修改学生总数
    void changeNumberOfPeople(@Param("class_name") String className);

    //修改学生个人信息
    void changeStudentInfo(Map map);
}
