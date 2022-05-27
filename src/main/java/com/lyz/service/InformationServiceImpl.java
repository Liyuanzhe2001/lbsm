package com.lyz.service;

import com.github.pagehelper.PageHelper;
import com.lyz.mapper.InformationMapper;
import com.lyz.pojo.Information;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class InformationServiceImpl implements InformationService {

    @Autowired
    private InformationMapper informationMapper;

    public void setInformationMapper(InformationMapper informationMapper) {
        this.informationMapper = informationMapper;
    }

    public void insertStudentsInformation(List<Information> students) {
        informationMapper.insertStudentsInformation(students);
    }

    public List<Information> getPageClassInformation(Integer page, String className) {
        return informationMapper.getPageClassInformation(page, className);
    }

    public List<Information> getClassAllInformation(String className) {
        return informationMapper.getClassAllInformation(className);
    }

    public List<Information> getInformationByName(String name, String className) {
        return informationMapper.getInformationByName(name, className);
    }

    public List<Information> getInformationById(String studentId, String className) {
        return informationMapper.getInformationById(studentId, className);
    }

    public void addNewStudentInformation(String studentNumber, String studentName, String studentIdNumber, String studentId, String className, String grade, String studentRank, String instructor, String monitor, String leagueBranchSecretary, String numberOfPeople, String branch) {
        informationMapper.addNewStudentInformation(studentNumber, studentName, studentIdNumber, studentId, className, grade, studentRank, instructor, monitor, leagueBranchSecretary, numberOfPeople, branch);
    }

    public List<Information> getAllClassInformation(String className) {
        return informationMapper.getAllClassInformation(className);
    }

    public Integer getStudentRanking(String student, String className) {
        return informationMapper.getStudentRanking(student, className);
    }

    public void changeStudentScore(Map map) {
        informationMapper.changeStudentScore(map);
    }

    public void updateStudentTotalScore(String className, String studentName) {
        informationMapper.updateStudentTotalScore(className, studentName);
    }

    public void addOneRank(String studentName, String className) {
        informationMapper.addOneRank(studentName, className);
    }

    public void cutOneRank(String studentName, String className) {
        informationMapper.cutOneRank(studentName, className);
    }

    public void deleteAllInformation(String className) {
        informationMapper.deleteAllInformation(className);
    }

    public void changeAverage(String className) {
        informationMapper.changeAverage(className);
    }

    public void changeNumberOfPeople(String className) {
        informationMapper.changeNumberOfPeople(className);
    }

    public void changeStudentInfo(Map map) {
        informationMapper.changeStudentInfo(map);
    }

}
