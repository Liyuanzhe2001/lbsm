package com.lyz.util;

import com.lyz.pojo.Information;
import com.mysql.jdbc.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Excel {

    /**
     * 正确模板
     */
    public static List<String> correctHeaderList = new ArrayList<>();

    //对应的Excel表
    public static Workbook workbook;

    //Excel表格类型
    public static String type;

    //Excel表格地址
    public static String path;

    //工作表
    public static Sheet sheet;

    //最后一行下标
    public static int lastRow;

    public static ClientAnchor anchor;

    //班级名
    public static String className;

    public static FormulaEvaluator eval;

    /**
     * 将模板初始化
     */
    static {
        correctHeaderList.add("序号");
        correctHeaderList.add("姓名");
        correctHeaderList.add("身份证号");
        correctHeaderList.add("学号");
        correctHeaderList.add("班级");
        correctHeaderList.add("年级");
        correctHeaderList.add("爱党爱国");
        correctHeaderList.add("校纪校规");
        correctHeaderList.add("公益活动");
        correctHeaderList.add("学习成果");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("文体活动");
        correctHeaderList.add("自主管理");
        correctHeaderList.add("创新创业");
        correctHeaderList.add("继续教育");
        correctHeaderList.add("时间管理");
        correctHeaderList.add("终身学习");
        correctHeaderList.add("学生组织");
        correctHeaderList.add("爱心爱校");
        correctHeaderList.add("国际技能");
        correctHeaderList.add("出国留学");
        correctHeaderList.add("国际赛事");
        correctHeaderList.add("高级证书");
        correctHeaderList.add("获得驾照");
        correctHeaderList.add("专业证书");
        correctHeaderList.add("技术技能");
        correctHeaderList.add("总分");
        correctHeaderList.add("班级排名");
        correctHeaderList.add("平均数");
        correctHeaderList.add("辅导员");
        correctHeaderList.add("班长");
        correctHeaderList.add("团支书");
        correctHeaderList.add("人数");
        correctHeaderList.add("分院");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("教学成绩");
        correctHeaderList.add("青年大学习");
        correctHeaderList.add("政企实践");
        correctHeaderList.add("军事学习");
        correctHeaderList.add("个人荣誉");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
        correctHeaderList.add("");
    }

    /**
     * 根据数据类型获取数据
     */
    public static String getValue(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING://字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN://布尔
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK://空
                    break;
                case NUMERIC://数字（日期、普通数字）
                    if (DateUtil.isCellDateFormatted(cell)) {//日期
                        Date date = cell.getDateCellValue();
                        cellValue = new DateTime(date).toString("yy-MM-dd");
                    } else {
                        //不是日期格式，防止数字过长！
                        cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                    }
                    break;
                case FORMULA://公式
                    cellValue = eval.evaluate(cell).formatAsString();
                    cellValue = cellValue.substring(0, cellValue.indexOf('.'));
                    break;
                case ERROR://错误
                    break;
            }
        }
        return cellValue;
    }

    public static String getExcelPath(HttpSession session) {
        className = (String) session.getAttribute(Constants.USER_CLASS);
        String path = session.getServletContext().getRealPath("/upload/" + className);
        path = path + "/" + className + "班学分.xlsx";
        return path;
    }

    //初始化 获得第一张表
    public static void init(HttpSession session) throws Exception {
        path = getExcelPath(session);
        type = MineTypeUtils.getFileType(path);
        FileInputStream inputStream = new FileInputStream(path);
        if (type.equals("xls")) {
            workbook = new HSSFWorkbook(inputStream);
            eval = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
            anchor = new HSSFClientAnchor();
        } else if (type.equals("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
            eval = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
            anchor = new XSSFClientAnchor();
        } else {
            throw new FileTypeException("Wrong Excel Type");
        }
        inputStream.close();
        sheet = workbook.getSheetAt(0);
    }

    public static void removeAllFormula() {
        int i = 2;
        lastRow = 1;
        while (true) {
            try {
                Cell cell = sheet.getRow(i).getCell(0);
                String value = getValue(cell);
                if (StringUtils.isNullOrEmpty(value)) {
                    break;
                } else {
                    lastRow++;
                }
            } catch (Exception e) {
                break;
            }
            Cell totalScoreCell = sheet.getRow(i).getCell(titleNo.getNumByTitle("totalScore") - 1);
            if (totalScoreCell.getCellType() == CellType.FORMULA) {
                totalScoreCell.removeFormula();
            }
            i++;
        }
    }


    //添加批注
    public static void setComment(Integer lineNumber, Integer columnNumber, String newContent) throws FileTypeException {
        /**
         * 设置字体
         */
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 9);

        /**
         * 设置批注
         */
        Cell cell = sheet.getRow(lineNumber).getCell(columnNumber);
        cell.removeCellComment();
        anchor.setDx1(0);
        anchor.setDx2(0);
        anchor.setDy1(0);
        anchor.setDy2(0);
        anchor.setCol1(cell.getColumnIndex());
        anchor.setRow1(cell.getRowIndex());
        anchor.setCol2(cell.getColumnIndex() + 5);
        anchor.setRow2(cell.getRowIndex() + newContent.length() / 15 + 3);

        //结束
        Comment comment = sheet.createDrawingPatriarch().createCellComment(anchor);

        RichTextString str;
        //输入批注信息
        if (type.equals("xls")) {
            str = new HSSFRichTextString(newContent);
        } else {
            str = new XSSFRichTextString(newContent);
        }

        //应用字体
        str.applyFont(font);

        //写入批注
        comment.setString(str);

        //批注添加进单元格对象
        cell.setCellComment(comment);
    }

    public static void addNewStudentInformation(Information information) {
        lastRow++;
        Row row = sheet.createRow(lastRow);
        for (int i = 0; i < titleNo.all; i++) {
            Cell cell = row.createCell(i);
            String value = "";
            switch (i + 1) {
                case 1:
                    value = information.getNumber();
                    break;
                case 2:
                    value = information.getName();
                    break;
                case 3:
                    value = information.getIdNumber();
                    break;
                case 4:
                    value = information.getStudentId();
                    break;
                case 5:
                    value = information.getClassName();
                    break;
                case 6:
                    value = information.getGrade();
                    break;
                case 7:
                    value = information.getLoveThePartyAndLoveTheCountry();
                    break;
                case 8:
                    value = information.getSchoolDisciplineAndRules();
                    break;
                case 9:
                    value = information.getPublicBenefitActivities();
                    break;
                case 10:
                    value = information.getTeachingAchievement();
                    break;
                case 11:
                    value = information.getYouthLearning();
                    break;
                case 12:
                    value = information.getGovernmentEnterprisePractice();
                    break;
                case 13:
                    value = information.getMilitaryLearning();
                    break;
                case 14:
                    value = information.getPersonalHonor();
                    break;
                case 15:
                    value = information.getRecreationalActivities();
                    break;
                case 16:
                    value = information.getSelfManagement();
                    break;
                case 17:
                    value = information.getInnovationAndEntrepreneurship();
                    break;
                case 18:
                    value = information.getContinuingEducation();
                    break;
                case 19:
                    value = information.getTimeManagement();
                    break;
                case 20:
                    value = information.getLifelongLearning();
                    break;
                case 21:
                    value = information.getStudentOrganization();
                    break;
                case 22:
                    value = information.getLoveSchool();
                    break;
                case 23:
                    value = information.getInternationalSkills();
                    break;
                case 24:
                    value = information.getStudyAbroad();
                    break;
                case 25:
                    value = information.getInternationalEvents();
                    break;
                case 26:
                    value = information.getAdvancedCertificate();
                    break;
                case 27:
                    value = information.getGetDriverLicense();
                    break;
                case 28:
                    value = information.getProfessionalCertificate();
                    break;
                case 29:
                    value = information.getTechnicalSkills();
                    break;
                case 30:
                    value = information.getTotalScore();
                    break;
                case 31:
                    value = information.getClassRanking();
                    break;
                case 32:
                    value = information.getAverage();
                    break;
                case 33:
                    value = information.getInstructor();
                    break;
                case 34:
                    value = information.getMonitor();
                    break;
                case 35:
                    value = information.getLeagueBranchSecretary();
                    break;
                case 36:
                    value = information.getNumberOfPeople();
                    break;
                case 37:
                    value = information.getBranch();
                    break;
            }
            try {
                cell.setCellValue(Integer.parseInt(value));
            } catch (Exception e) {
                cell.setCellValue(value);
            }
        }
    }

    //获得所有学生
    public static List<Information> getAllInformation() {
        List<Information> list = new ArrayList<>();
        for (int i = 2; i <= lastRow; i++) {
            Information student = new Information();
            for (int j = 0; j < titleNo.all; j++) {
                String title = titleNo.getTitleByNum(j + 1);
                Cell cell = sheet.getRow(i).getCell(j);
                String value = getValue(cell);
                switch (title) {
                    case "number":
                        student.setNumber(value);
                        break;
                    case "name":
                        student.setName(value);
                        break;
                    case "idNumber":
                        student.setIdNumber(value);
                        break;
                    case "studentId":
                        student.setStudentId(value);
                        break;
                    case "className":
                        student.setClassName(value);
                        break;
                    case "grade":
                        student.setGrade(value);
                        break;
                    case "loveThePartyAndLoveTheCountry":
                        student.setLoveThePartyAndLoveTheCountry(value);
                        break;
                    case "schoolDisciplineAndRules":
                        student.setSchoolDisciplineAndRules(value);
                        break;
                    case "publicBenefitActivities":
                        student.setPublicBenefitActivities(value);
                        break;
                    case "teachingAchievement":
                        student.setTeachingAchievement(value);
                        break;
                    case "youthLearning":
                        student.setYouthLearning(value);
                        break;
                    case "governmentEnterprisePractice":
                        student.setGovernmentEnterprisePractice(value);
                        break;
                    case "militaryLearning":
                        student.setMilitaryLearning(value);
                        break;
                    case "personalHonor":
                        student.setPersonalHonor(value);
                        break;
                    case "recreationalActivities":
                        student.setRecreationalActivities(value);
                        break;
                    case "selfManagement":
                        student.setSelfManagement(value);
                        break;
                    case "innovationAndEntrepreneurship":
                        student.setInnovationAndEntrepreneurship(value);
                        break;
                    case "continuingEducation":
                        student.setContinuingEducation(value);
                        break;
                    case "timeManagement":
                        student.setTimeManagement(value);
                        break;
                    case "lifelongLearning":
                        student.setLifelongLearning(value);
                        break;
                    case "studentOrganization":
                        student.setStudentOrganization(value);
                        break;
                    case "loveSchool":
                        student.setLoveSchool(value);
                        break;
                    case "internationalSkills":
                        student.setInternationalSkills(value);
                        break;
                    case "studyAbroad":
                        student.setStudyAbroad(value);
                        break;
                    case "internationalEvents":
                        student.setInternationalEvents(value);
                        break;
                    case "advancedCertificate":
                        student.setAdvancedCertificate(value);
                        break;
                    case "getDriverLicense":
                        student.setGetDriverLicense(value);
                        break;
                    case "professionalCertificate":
                        student.setProfessionalCertificate(value);
                        break;
                    case "technicalSkills":
                        student.setTechnicalSkills(value);
                        break;
                    case "totalScore":
                        student.setTotalScore(value);
                        break;
                    case "classRanking":
                        student.setClassRanking(value);
                        break;
                    case "average":
                        student.setAverage(value);
                        break;
                    case "instructor":
                        student.setInstructor(value);
                        break;
                    case "monitor":
                        student.setMonitor(value);
                        break;
                    case "leagueBranchSecretary":
                        student.setLeagueBranchSecretary(value);
                        break;
                    case "numberOfPeople":
                        student.setNumberOfPeople(value);
                        break;
                    case "branch":
                        student.setBranch(value);
                        break;
                }
            }
            list.add(student);
        }
        return list;
    }

    public static void save() throws IOException {
        workbook.setForceFormulaRecalculation(true);
        FileOutputStream outputStream = new FileOutputStream(path);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

}
