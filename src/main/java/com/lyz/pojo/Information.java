package com.lyz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Information {

    private static final long serialVersionUID = 1L;

    //唯一id
    private Integer id;

    //序号
    private String number;

    //姓名
    private String name;

    //身份证号
    private String idNumber;

    //学号
    private String studentId;

    //班级
    private String className;

    //年级
    private String grade;

    //爱党爱国
    private String loveThePartyAndLoveTheCountry;

    //校纪校规
    private String schoolDisciplineAndRules;

    //公益活动
    private String publicBenefitActivities;

    //教学成绩
    private String teachingAchievement;

    //青年大学习
    private String youthLearning;

    //政企实践
    private String governmentEnterprisePractice;

    //军事学习
    private String militaryLearning;

    //个人荣誉
    private String personalHonor;

    //文体活动
    private String recreationalActivities;

    //自主管理
    private String selfManagement;

    //创新创业
    private String innovationAndEntrepreneurship;

    //继续教育
    private String continuingEducation;

    //时间管理
    private String timeManagement;

    //终身学习
    private String lifelongLearning;

    //学生组织
    private String studentOrganization;

    //爱心爱校
    private String loveSchool;

    //国际技能
    private String internationalSkills;

    //出国留学
    private String studyAbroad;

    //国际赛事
    private String internationalEvents;

    //高级证书
    private String advancedCertificate;

    //获得驾照
    private String getDriverLicense;

    //专业证书
    private String professionalCertificate;

    //技术技能
    private String technicalSkills;

    //总分
    private String totalScore;

    //班级排名
    private String classRanking;

    //平均数
    private String average;

    //辅导员
    private String instructor;

    //班长
    private String monitor;

    //团支书
    private String leagueBranchSecretary;

    //人数
    private String numberOfPeople;

    //分院
    private String branch;
}
