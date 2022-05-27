//package com.lyz.util;
//
//import com.lyz.pojo.Information;
//import com.mysql.jdbc.StringUtils;
//import com.spire.xls.CellRange;
//import com.spire.xls.ExcelFont;
//import com.spire.xls.Workbook;
//import com.spire.xls.Worksheet;
//
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DoExcel {
//
//    /**
//     * 正确模板
//     */
//    public static List<String> correctHeaderList = new ArrayList<>();
//
//    public static Workbook workbook = new Workbook();
//
//    public static Worksheet worksheet;
//
//    public static String path;
//
//    public static Integer lastRow;
//
//    public static String className;
//
//    /**
//     * 将模板初始化
//     */
//    static {
//        correctHeaderList.add("序号");
//        correctHeaderList.add("姓名");
//        correctHeaderList.add("身份证号");
//        correctHeaderList.add("学号");
//        correctHeaderList.add("班级");
//        correctHeaderList.add("年级");
//        correctHeaderList.add("爱党爱国");
//        correctHeaderList.add("校纪校规");
//        correctHeaderList.add("公益活动");
//        correctHeaderList.add("学习成果");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("文体活动");
//        correctHeaderList.add("自主管理");
//        correctHeaderList.add("创新创业");
//        correctHeaderList.add("继续教育");
//        correctHeaderList.add("时间管理");
//        correctHeaderList.add("终身学习");
//        correctHeaderList.add("学生组织");
//        correctHeaderList.add("爱心爱校");
//        correctHeaderList.add("国际技能");
//        correctHeaderList.add("出国留学");
//        correctHeaderList.add("国际赛事");
//        correctHeaderList.add("高级证书");
//        correctHeaderList.add("获得驾照");
//        correctHeaderList.add("专业证书");
//        correctHeaderList.add("技术技能");
//        correctHeaderList.add("总分");
//        correctHeaderList.add("班级排名");
//        correctHeaderList.add("平均数");
//        correctHeaderList.add("辅导员");
//        correctHeaderList.add("班长");
//        correctHeaderList.add("团支书");
//        correctHeaderList.add("人数");
//        correctHeaderList.add("分院");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("教学成绩");
//        correctHeaderList.add("青年大学习");
//        correctHeaderList.add("政企实践");
//        correctHeaderList.add("军事学习");
//        correctHeaderList.add("个人荣誉");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//        correctHeaderList.add("");
//    }
//
//    public static String getExcelPath(HttpSession session) {
//        if (StringUtils.isNullOrEmpty(path)) {
//            className = (String) session.getAttribute(Constants.USER_CLASS);
//            path = session.getServletContext().getRealPath("/upload/" + className);
//            path = path + "/" + className + "班学分.xlsx";
//        }
//        return path;
//    }
//
//    public static void init(HttpSession session) throws IOException {
//        className = (String) session.getAttribute(Constants.USER_CLASS);
//        path = getExcelPath(session);
//        System.out.println(path);
//        workbook.loadFromFile(path);
//        System.out.println(path);
//        worksheet = workbook.getWorksheets().get(0);
//        lastRow = worksheet.findAllString(className, true, true).length + 2;
//    }
//
//    //添加批注
//    public static void setComment(Integer lineNumber, Integer columnNumber, String newContent) {
//
//        //设置字体9号宋体
//        ExcelFont font = workbook.createFont();
//        font.setFontName("宋体");
//        font.setSize(9);
//
//        CellRange cellRange = worksheet.getCellRange(lineNumber, columnNumber);
//        cellRange.getComment().getRichText().setText(newContent);
//        cellRange.getComment().getRichText().setFont(0, newContent.length() - 1, font);
//    }
//
//    //添加新学生信息
//    public static void addNewStudentInformation(Information information) {
//        lastRow++;
//        for (int i = 1; i <= titleNo.all; i++) {
//            String value = "";
//            switch (i) {
//                case 1:
//                    value = information.getNumber();
//                    break;
//                case 2:
//                    value = information.getName();
//                    break;
//                case 3:
//                    value = information.getIdNumber();
//                    break;
//                case 4:
//                    value = information.getStudentId();
//                    break;
//                case 5:
//                    value = information.getClassName();
//                    break;
//                case 6:
//                    value = information.getGrade();
//                    break;
//                case 7:
//                    value = information.getLoveThePartyAndLoveTheCountry();
//                    break;
//                case 8:
//                    value = information.getSchoolDisciplineAndRules();
//                    break;
//                case 9:
//                    value = information.getPublicBenefitActivities();
//                    break;
//                case 10:
//                    value = information.getTeachingAchievement();
//                    break;
//                case 11:
//                    value = information.getYouthLearning();
//                    break;
//                case 12:
//                    value = information.getGovernmentEnterprisePractice();
//                    break;
//                case 13:
//                    value = information.getMilitaryLearning();
//                    break;
//                case 14:
//                    value = information.getPersonalHonor();
//                    break;
//                case 15:
//                    value = information.getRecreationalActivities();
//                    break;
//                case 16:
//                    value = information.getSelfManagement();
//                    break;
//                case 17:
//                    value = information.getInnovationAndEntrepreneurship();
//                    break;
//                case 18:
//                    value = information.getContinuingEducation();
//                    break;
//                case 19:
//                    value = information.getTimeManagement();
//                    break;
//                case 20:
//                    value = information.getLifelongLearning();
//                    break;
//                case 21:
//                    value = information.getStudentOrganization();
//                    break;
//                case 22:
//                    value = information.getLoveSchool();
//                    break;
//                case 23:
//                    value = information.getInternationalSkills();
//                    break;
//                case 24:
//                    value = information.getStudyAbroad();
//                    break;
//                case 25:
//                    value = information.getInternationalEvents();
//                    break;
//                case 26:
//                    value = information.getAdvancedCertificate();
//                    break;
//                case 27:
//                    value = information.getGetDriverLicense();
//                    break;
//                case 28:
//                    value = information.getProfessionalCertificate();
//                    break;
//                case 29:
//                    value = information.getTechnicalSkills();
//                    break;
//                case 30:
//                    value = information.getTotalScore();
//                    break;
//                case 31:
//                    value = information.getClassRanking();
//                    break;
//                case 32:
//                    value = information.getAverage();
//                    break;
//                case 33:
//                    value = information.getInstructor();
//                    break;
//                case 34:
//                    value = information.getMonitor();
//                    break;
//                case 35:
//                    value = information.getLeagueBranchSecretary();
//                    break;
//                case 36:
//                    value = information.getNumberOfPeople();
//                    break;
//                case 37:
//                    value = information.getBranch();
//                    break;
//            }
//            worksheet.getCellRange(lastRow, i).setValue(value);
//        }
//    }
//
//    //获取所有学生
//    public static List<Information> getAllInformation() {
//        List<Information> list = new ArrayList<>();
//        for (int i = 3; i <= lastRow; i++) {
//            Information student = new Information();
//            for (int j = 1; j <= worksheet.getLastColumn(); j++) {
//                String title = titleNo.getTitleByNum(j);
//                String value;
//                if (worksheet.get(i, j).hasFormula())
//                    value = worksheet.get(i, j).getFormulaNumberValue() + "";
//                else
//                    value = worksheet.get(i, j).getValue();
//                switch (title) {
//                    case "number":
//                        student.setNumber(value);
//                        break;
//                    case "name":
//                        student.setName(value);
//                        break;
//                    case "idNumber":
//                        student.setIdNumber(value);
//                        break;
//                    case "studentId":
//                        student.setStudentId(value);
//                        break;
//                    case "className":
//                        student.setClassName(value);
//                        break;
//                    case "grade":
//                        student.setGrade(value);
//                        break;
//                    case "loveThePartyAndLoveTheCountry":
//                        student.setLoveThePartyAndLoveTheCountry(value);
//                        break;
//                    case "schoolDisciplineAndRules":
//                        student.setSchoolDisciplineAndRules(value);
//                        break;
//                    case "publicBenefitActivities":
//                        student.setPublicBenefitActivities(value);
//                        break;
//                    case "teachingAchievement":
//                        student.setTeachingAchievement(value);
//                        break;
//                    case "youthLearning":
//                        student.setYouthLearning(value);
//                        break;
//                    case "governmentEnterprisePractice":
//                        student.setGovernmentEnterprisePractice(value);
//                        break;
//                    case "militaryLearning":
//                        student.setMilitaryLearning(value);
//                        break;
//                    case "personalHonor":
//                        student.setPersonalHonor(value);
//                        break;
//                    case "recreationalActivities":
//                        student.setRecreationalActivities(value);
//                        break;
//                    case "selfManagement":
//                        student.setSelfManagement(value);
//                        break;
//                    case "innovationAndEntrepreneurship":
//                        student.setInnovationAndEntrepreneurship(value);
//                        break;
//                    case "continuingEducation":
//                        student.setContinuingEducation(value);
//                        break;
//                    case "timeManagement":
//                        student.setTimeManagement(value);
//                        break;
//                    case "lifelongLearning":
//                        student.setLifelongLearning(value);
//                        break;
//                    case "studentOrganization":
//                        student.setStudentOrganization(value);
//                        break;
//                    case "loveSchool":
//                        student.setLoveSchool(value);
//                        break;
//                    case "internationalSkills":
//                        student.setInternationalSkills(value);
//                        break;
//                    case "studyAbroad":
//                        student.setStudyAbroad(value);
//                        break;
//                    case "internationalEvents":
//                        student.setInternationalEvents(value);
//                        break;
//                    case "advancedCertificate":
//                        student.setAdvancedCertificate(value);
//                        break;
//                    case "getDriverLicense":
//                        student.setGetDriverLicense(value);
//                        break;
//                    case "professionalCertificate":
//                        student.setProfessionalCertificate(value);
//                        break;
//                    case "technicalSkills":
//                        student.setTechnicalSkills(value);
//                        break;
//                    case "totalScore":
//                        student.setTotalScore(value);
//                        break;
//                    case "classRanking":
//                        student.setClassRanking(value);
//                        break;
//                    case "average":
//                        student.setAverage(value);
//                        break;
//                    case "instructor":
//                        student.setInstructor(value);
//                        break;
//                    case "monitor":
//                        student.setMonitor(value);
//                        break;
//                    case "leagueBranchSecretary":
//                        student.setLeagueBranchSecretary(value);
//                        break;
//                    case "numberOfPeople":
//                        student.setNumberOfPeople(value);
//                        break;
//                    case "branch":
//                        student.setBranch(value);
//                        break;
//                }
//            }
//            list.add(student);
//        }
//        return list;
//    }
//
//
//    //保存
//    public static void save() {
//        workbook.save();
//    }
//
//
//}
