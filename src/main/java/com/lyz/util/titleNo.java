package com.lyz.util;

public class titleNo {

    //序号
    private final static Integer number = 1;

    //姓名
    private final static Integer name = 2;

    //身份证号
    private final static Integer idNumber = 3;

    //学号
    private final static Integer studentId = 4;

    //班级
    private final static Integer className = 5;

    //年级
    private final static Integer grade = 6;

    //爱党爱国
    private final static Integer loveThePartyAndLoveTheCountry = 7;

    //校纪校规
    private final static Integer schoolDisciplineAndRules = 8;

    //公益活动
    private final static Integer publicBenefitActivities = 9;

    //教学成绩
    private final static Integer teachingAchievement = 10;

    //青年大学习
    private final static Integer youthLearning = 11;

    //政企实践
    private final static Integer governmentEnterprisePractice = 12;

    //军事学习
    private final static Integer militaryLearning = 13;

    //个人荣誉
    private final static Integer personalHonor = 14;

    //文体活动
    private final static Integer recreationalActivities = 15;

    //自主管理
    private final static Integer selfManagement = 16;

    //创新创业
    private final static Integer innovationAndEntrepreneurship = 17;

    //继续教育
    private final static Integer continuingEducation = 18;

    //时间管理
    private final static Integer timeManagement = 19;

    //终身学习
    private final static Integer lifelongLearning = 20;

    //学生组织
    private final static Integer studentOrganization = 21;

    //爱心爱校
    private final static Integer loveSchool = 22;

    //国际技能
    private final static Integer internationalSkills = 23;

    //出国留学
    private final static Integer studyAbroad = 24;

    //国际赛事
    private final static Integer internationalEvents = 25;

    //高级证书
    private final static Integer advancedCertificate = 26;

    //获得驾照
    private final static Integer getDriverLicense = 27;

    //专业证书
    private final static Integer professionalCertificate = 28;

    //技术技能
    private final static Integer technicalSkills = 29;

    //总分
    private final static Integer totalScore = 30;

    //班级排名
    private final static Integer classRanking = 31;

    //平均数
    private final static Integer average = 32;

    //辅导员
    private final static Integer instructor = 33;

    //班长
    private final static Integer monitor = 34;

    //团支书
    private final static Integer leagueBranchSecretary = 35;

    //人数
    private final static Integer numberOfPeople = 36;

    //分院
    private final static Integer branch = 37;

    //总字段数
    public final static Integer all = 37;

    //通过列名获取列编号
    public static Integer getNumByTitle(String title) {
        switch (title) {
            case "number":
                return number;
            case "name":
                return name;
            case "idNumber":
                return idNumber;
            case "studentId":
                return studentId;
            case "className":
                return className;
            case "grade":
                return grade;
            case "loveThePartyAndLoveTheCountry":
                return loveThePartyAndLoveTheCountry;
            case "schoolDisciplineAndRules":
                return schoolDisciplineAndRules;
            case "publicBenefitActivities":
                return publicBenefitActivities;
            case "teachingAchievement":
                return teachingAchievement;
            case "youthLearning":
                return youthLearning;
            case "governmentEnterprisePractice":
                return governmentEnterprisePractice;
            case "militaryLearning":
                return militaryLearning;
            case "personalHonor":
                return personalHonor;
            case "recreationalActivities":
                return recreationalActivities;
            case "selfManagement":
                return selfManagement;
            case "innovationAndEntrepreneurship":
                return innovationAndEntrepreneurship;
            case "continuingEducation":
                return continuingEducation;
            case "timeManagement":
                return timeManagement;
            case "lifelongLearning":
                return lifelongLearning;
            case "studentOrganization":
                return studentOrganization;
            case "loveSchool":
                return loveSchool;
            case "internationalSkills":
                return internationalSkills;
            case "studyAbroad":
                return studyAbroad;
            case "internationalEvents":
                return internationalEvents;
            case "advancedCertificate":
                return advancedCertificate;
            case "getDriverLicense":
                return getDriverLicense;
            case "professionalCertificate":
                return professionalCertificate;
            case "technicalSkills":
                return technicalSkills;
            case "totalScore":
                return totalScore;
            case "classRanking":
                return classRanking;
            case "average":
                return average;
            case "instructor":
                return instructor;
            case "monitor":
                return monitor;
            case "leagueBranchSecretary":
                return leagueBranchSecretary;
            case "numberOfPeople":
                return numberOfPeople;
            case "branch":
                return branch;
            default:
                return 0;
        }
    }

    //通过列编号获取列名
    public static String getTitleByNum(Integer n) {
        switch (n) {
            case 1:
                return "number";
            case 2:
                return "name";
            case 3:
                return "idNumber";
            case 4:
                return "studentId";
            case 5:
                return "className";
            case 6:
                return "grade";
            case 7:
                return "loveThePartyAndLoveTheCountry";
            case 8:
                return "schoolDisciplineAndRules";
            case 9:
                return "publicBenefitActivities";
            case 10:
                return "teachingAchievement";
            case 11:
                return "youthLearning";
            case 12:
                return "governmentEnterprisePractice";
            case 13:
                return "militaryLearning";
            case 14:
                return "personalHonor";
            case 15:
                return "recreationalActivities";
            case 16:
                return "selfManagement";
            case 17:
                return "innovationAndEntrepreneurship";
            case 18:
                return "continuingEducation";
            case 19:
                return "timeManagement";
            case 20:
                return "lifelongLearning";
            case 21:
                return "studentOrganization";
            case 22:
                return "loveSchool";
            case 23:
                return "internationalSkills";
            case 24:
                return "studyAbroad";
            case 25:
                return "internationalEvents";
            case 26:
                return "advancedCertificate";
            case 27:
                return "getDriverLicense";
            case 28:
                return "professionalCertificate";
            case 29:
                return "technicalSkills";
            case 30:
                return "totalScore";
            case 31:
                return "classRanking";
            case 32:
                return "average";
            case 33:
                return "instructor";
            case 34:
                return "monitor";
            case 35:
                return "leagueBranchSecretary";
            case 36:
                return "numberOfPeople";
            case 37:
                return "branch";
            default:
                return "";
        }
    }

    //通过列名获取列中文名
    public static String getChineseByTitle(String title) {
        switch (title) {
            case "number":
                return "序号";
            case "name":
                return "姓名";
            case "idNumber":
                return "身份证号";
            case "studentId":
                return "学号";
            case "className":
                return "班级";
            case "grade":
                return "年级";
            case "loveThePartyAndLoveTheCountry":
                return "爱党爱国";
            case "schoolDisciplineAndRules":
                return "校纪校规";
            case "publicBenefitActivities":
                return "公益活动";
            case "teachingAchievement":
                return "教学成绩";
            case "youthLearning":
                return "青年大学习";
            case "governmentEnterprisePractice":
                return "政企实践";
            case "militaryLearning":
                return "军事学习";
            case "personalHonor":
                return "个人荣誉";
            case "recreationalActivities":
                return "文体活动";
            case "selfManagement":
                return "自主管理";
            case "innovationAndEntrepreneurship":
                return "创新创业";
            case "continuingEducation":
                return "继续教育";
            case "timeManagement":
                return "时间管理";
            case "lifelongLearning":
                return "终身学习";
            case "studentOrganization":
                return "学生组织";
            case "loveSchool":
                return "爱心爱校";
            case "internationalSkills":
                return "国际技能";
            case "studyAbroad":
                return "出国留学";
            case "internationalEvents":
                return "国际赛事";
            case "advancedCertificate":
                return "高级证书";
            case "getDriverLicense":
                return "获得驾照";
            case "professionalCertificate":
                return "专业证书";
            case "technicalSkills":
                return "技术技能";
            case "totalScore":
                return "总分";
            case "classRanking":
                return "班级排名";
            case "average":
                return "平均数";
            case "instructor":
                return "辅导员";
            case "monitor":
                return "班长";
            case "leagueBranchSecretary":
                return "团支书";
            case "numberOfPeople":
                return "人数";
            case "branch":
                return "分院";
            default:
                return "";
        }
    }

}
