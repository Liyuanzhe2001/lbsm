package com.lyz.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyz.pojo.Class;
import com.lyz.pojo.Information;
import com.lyz.pojo.User;
import com.lyz.service.ClassService;
import com.lyz.service.InformationService;
import com.lyz.service.RecordsService;
import com.lyz.util.*;
import com.mysql.jdbc.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("/changeInfo")
public class InformationController {

    @Qualifier("informationServiceImpl")
    @Resource
    private InformationService informationService;

    @Qualifier("classServiceImpl")
    @Resource
    private ClassService classService;

    @Qualifier("recordsServiceImpl")
    @Resource
    private RecordsService recordsService;

    private User user;

    @RequestMapping("/toShowStudents")
    public String toShowStudents() {
        return "showStudents";
    }

    @RequestMapping("/showStudents")
    public String showStudents(@RequestParam(required = false, defaultValue = "1", value = "page") Integer page, HttpServletRequest request, Model model) {
        String className = (String) request.getSession().getAttribute(Constants.USER_CLASS);
        PageHelper.startPage(page, 10);
        List<Information> students = informationService.getPageClassInformation(page, className);
        PageInfo pageInfo = new PageInfo(students, 10);
        model.addAttribute("pageInfo", pageInfo);
        return "showStudents";
    }

    @RequestMapping("/findStudent")
    public String findStudent(String findStudent, HttpServletRequest request, Model model) {
        String className = (String) request.getSession().getAttribute(Constants.USER_CLASS);
        List<Information> list;
        try {
            Integer.parseInt(findStudent);
            list = informationService.getInformationById(findStudent, className);
        } catch (NumberFormatException e) {
            list = informationService.getInformationByName(findStudent, className);
        }
        if (list == null) {
            model.addAttribute("error", "未查到");
        } else {
            PageInfo pageInfo = new PageInfo(list, 10);
            model.addAttribute("pageInfo", pageInfo);
        }
        return "showStudents";
    }

    @RequestMapping("/toAddStudent")
    public String toAddStudent(HttpSession session, Model model) {
        String className = (String) session.getAttribute(Constants.USER_CLASS);
        Integer numberOfPeople = classService.getNumberOfPeople(className);
        model.addAttribute("num", numberOfPeople + 1);
        return "addStudent";
    }

    @RequestMapping("/addStudent")
    public String addStudent(String studentName, String studentIdNumber, String studentId, HttpServletRequest request) throws ParseException, IOException {
        //班级学生数量加一
        user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        Integer classId = user.getClassId();
        classService.addNumberOfPeople(classId);
//        Excel.lastRow++;

        Class classInfo = classService.getClassById(classId);

        //数据库学生加一
        informationService.changeNumberOfPeople(classInfo.getName());

        informationService.addNewStudentInformation(classInfo.getNumberOfPeople(), studentName, studentIdNumber, studentId, classInfo.getName(), classInfo.getGrade(), classInfo.getNumberOfPeople(), classInfo.getInstructor(), classInfo.getMonitor(), classInfo.getLeagueBranchSecretary(), classInfo.getNumberOfPeople(), classInfo.getBranch());

        request.getSession().getAttribute(Constants.USER_CLASS);

        //添加操作记录
        recordsService.insertRecord(user.getId(), "添加学生", studentName, "/", getDate.getNow());

        //修改平均分
        informationService.changeAverage(classInfo.getName());

        //获取学生信息
        List<Information> informationById = informationService.getInformationById(studentId, classInfo.getName());
        Information student = informationById.get(0);

        //学生数据写进excel表格
        Excel.addNewStudentInformation(student);

        request.setAttribute("msg", "添加成功");
        request.setAttribute("num", Integer.parseInt(classInfo.getNumberOfPeople()) + 1);

        Excel.save();
        return "addStudent";
    }

    @RequestMapping("/toModifyStudentInfo")
    public String toModifyStudentInfo(HttpSession session, Model model) {
        String className = (String) session.getAttribute(Constants.USER_CLASS);
        List<Information> studentList = informationService.getAllClassInformation(className);
        model.addAttribute("studentList", studentList);
        return "modifyStudentInfo";
    }

    @RequestMapping("/modifyStudentInfo")
    @ResponseBody
    public void modifyStudentInfo(Model model, HttpSession session, String studentName, String studentId, String idNumber) {
        String className = (String) session.getAttribute(Constants.USER_CLASS);
        Sheet worksheet = Excel.sheet;
        Map<String, String> map = new HashMap<>();
        map.put("className", className);
        map.put("studentName", studentName);
        map.put("studentId", studentId);
        map.put("idNumber", idNumber);
        int lineNumber = informationService.getStudentRanking(studentName, className) + 1;

        if (!StringUtils.isNullOrEmpty(studentId)) {
            worksheet.getRow(lineNumber).getCell(titleNo.getNumByTitle("studentId") - 1).setCellValue(studentId);
        }
        if (!StringUtils.isNullOrEmpty(idNumber)) {
            System.out.println(idNumber);
            worksheet.getRow(lineNumber).getCell(titleNo.getNumByTitle("idNumber")).setCellValue(idNumber);
        }

        informationService.changeStudentInfo(map);
        model.addAttribute("msg", "修改成功");
    }

    @RequestMapping("/toModifyOneStudent")
    public String toModifyOneStudent(HttpSession session, Model model) {
        String className = (String) session.getAttribute(Constants.USER_CLASS);
        List<Information> studentList = informationService.getAllClassInformation(className);
        model.addAttribute("studentList", studentList);
        String year = classService.getClassById(classService.getClassIdByClassName(className)).getGrade();
        model.addAttribute("year", Integer.parseInt(year));
        return "modifyOneStudent";
    }

    @RequestMapping("/modifyOneStudent")
    @ResponseBody
    public void modifyOneStudent(HttpServletRequest request, HttpServletResponse response, String studentName, String bonusType, String year, String month, String coverAdd, String bonusIntroduction) throws IOException, ParseException, FileTypeException {
        System.out.println("进入");
        //获得班级，用于查询修改的行（排名）
        String className = (String) request.getSession().getAttribute(Constants.USER_CLASS);
        //团支书
        user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        String leagueBranchSecretary = user.getName();

        //写入数据
        writeIntoExcel(studentName, className, bonusType, year, month, leagueBranchSecretary, bonusIntroduction, coverAdd);

        //保存
        Excel.save();

        System.out.println("完成");

    }

    @RequestMapping("/toModifyMoreStudent")
    public String toModifyMoreStudent(HttpSession session, Model model) {
        String className = (String) session.getAttribute(Constants.USER_CLASS);
        List<Information> studentList = informationService.getAllClassInformation(className);
        model.addAttribute("studentList", studentList);
        String year = classService.getClassById(classService.getClassIdByClassName(className)).getGrade();
        model.addAttribute("year", Integer.parseInt(year));
        return "modifyMoreStudent";
    }

    @RequestMapping("/modifyMoreStudent")
    public void modifyMoreStudent(HttpServletRequest request, HttpServletResponse response, String year, String month, String bonusType, String[] names, String coverAdd, String bonusIntroduction) throws ParseException, IOException, FileTypeException {
        System.out.println("进入modifyMoreStudent");
        //获得班级，用于查询修改的行（排名）
        String className = (String) request.getSession().getAttribute(Constants.USER_CLASS);
        //团支书
        user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        String leagueBranchSecretary = user.getName();


        if (names != null && names.length != 0) {
            for (String studentName : names) {
                //写入数据
                writeIntoExcel(studentName, className, bonusType, year, month, leagueBranchSecretary, bonusIntroduction, coverAdd);
            }
        } else {
            List<Map<String, String>> students = doString.getStrings(bonusIntroduction);
            String studentName = "null";
            for (Map<String, String> student : students) {
                try {
                    studentName = student.get("name");
                    bonusIntroduction = student.get("content");
                    //写入数据
                    writeIntoExcel(studentName, className, bonusType, year, month, leagueBranchSecretary, bonusIntroduction, coverAdd);
                } catch (Exception e) {
                    System.out.println(studentName);
                }
            }
        }
        Excel.save();
        System.out.println("完成modifyMoreStudent");
    }

//    @RequestMapping("/toAddBonusCertificate")
//    public String toAddBonusCertificate(HttpSession session, Model model) {
//        String className = (String) session.getAttribute(Constants.USER_CLASS);
//        List<Information> studentList = informationService.getAllClassInformation(className);
//        model.addAttribute("studentList", studentList);
//        String year = classService.getClassById(classService.getClassIdByClassName(className)).getGrade();
//        model.addAttribute("year", Integer.parseInt(year));
//        return "addBonusCertificate";
//    }
//
//    @RequestMapping("/addBonusCertificate")
//    @ResponseBody
//    public void adBonusCertificate(HttpServletRequest request, @RequestParam("files") List<MultipartFile> files) throws IOException {
//        System.out.println("进入addBonusCertificate");
//        String className = (String) request.getSession().getAttribute(Constants.USER_CLASS);
//        for (MultipartFile img : files) {
//            String imgPath = request.getSession().getServletContext().getRealPath("/upload/" + className + "/certificates");
//            File file = new File(imgPath);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            img.transferTo(new File(file + "/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg"));
//        }
//    }

    public void writeIntoExcel(String studentName, String className, String bonusType, String year, String month, String leagueBranchSecretary, String bonusIntroduction, String coverAdd) throws ParseException, FileTypeException {

        Sheet sheet = Excel.sheet;

        //修改的行
        int lineNumber = informationService.getStudentRanking(studentName, className) + 2;
        //修改的列
        Integer columnNumber = titleNo.getNumByTitle(bonusType) - 1;

        //获得要操作的单元格的批注
        String cellComment;
        try {
            cellComment = sheet.getRow(lineNumber).getCell(columnNumber).getCellComment().getString().toString();
        } catch (Exception e) {
            cellComment = "";
        }

        //设置新批注头部（xxxx年xx月  团支书\n  主要内容）
        String comment = year + "年" + month + "月  " + leagueBranchSecretary + "\r\n" + bonusIntroduction;

        //得到批注的新增学分
        Integer newValue = doString.getLastInteger(bonusIntroduction);
        //之前的单元格学分
        Cell oldScoreCell = sheet.getRow(lineNumber).getCell(columnNumber);
        Integer oldScoreValue = Integer.parseInt(Excel.getValue(oldScoreCell));

        //总学分
        Integer relValue = 0;
        //新总分
        Cell totalScoreCell = sheet.getRow(lineNumber).getCell(titleNo.getNumByTitle("totalScore") - 1);
        int totalValue = Integer.parseInt(Excel.getValue(totalScoreCell));

        //最后一行下标
        Integer lastRow = Excel.lastRow;
        //判断是覆盖原纪录还是新增新纪录
        //新增新纪录 或者 原记录为空
        if (coverAdd.equals("add") && !StringUtils.isNullOrEmpty(cellComment)) {
            //获得批注最后一行（共计：xxxxxxxxxxxxxxxx）
            String lastStr = cellComment.substring(cellComment.indexOf("共计："));

            //获得除 共计行的所有内容
            cellComment = cellComment.substring(0, cellComment.indexOf("共计："));

            //组装批注（增加新头部）
            comment = cellComment + comment;

            //将新增的数字放进 ”共计“ 算术式子
            String s = doString.insertNumber(lastStr, newValue);

            //单元格学分结果
            relValue = doString.getLastInteger(s);
            //新总学分
            totalValue = newValue + totalValue;

            //组装
            comment = comment + "\r\n" + s;

        } else {//覆盖原内容
            //写入的学分为传入的学分
            relValue = newValue;

            //组装
            comment = comment + "\r\n" + "共计：" + relValue + "分";

            totalValue = totalValue - oldScoreValue + relValue;
        }

        //数值写进单元格
        sheet.getRow(lineNumber).getCell(columnNumber).setCellValue(relValue);
        sheet.getRow(lineNumber).getCell(titleNo.getNumByTitle("totalScore") - 1).setCellValue(totalValue);

        //内容写进批注
        Excel.setComment(lineNumber, columnNumber, comment);

        if (coverAdd.equals("add")) {
            //上移
            int small = 0;
            int n = lineNumber - 1;
            while (n > 1) {
                Cell tmpCell = sheet.getRow(n).getCell(titleNo.getNumByTitle("totalScore") - 1);
                int value = Integer.parseInt(Excel.getValue(tmpCell));
                if (value < totalValue)
                    small++;
                else
                    break;
                n--;
            }

            if (small > 0) {
                sheet.shiftRows(lineNumber, lineNumber, lastRow - lineNumber + 1);
                sheet.shiftRows(lineNumber - small, lineNumber - 1, 1);
                sheet.shiftRows(lastRow + 1, lastRow + 1, -lastRow - 1 + lineNumber - small);
            }
        } else {
            //下移
            int big = 0;
            int n = lineNumber + 1;
            while (n <= lastRow) {
                Cell tmpCell = sheet.getRow(n).getCell(titleNo.getNumByTitle("totalScore") - 1);
                int value = Integer.parseInt(Excel.getValue(tmpCell));
                if (value > totalValue)
                    big++;
                else
                    break;
                n++;
            }

            if (big > 0) {
                sheet.shiftRows(lineNumber, lineNumber, lastRow - lineNumber + 1);
                sheet.shiftRows(lineNumber + 1, lineNumber + big + 1, -1);
                sheet.shiftRows(lastRow + 1, lastRow + 1, -lastRow + lineNumber + big);
            }

        }

        //添加操作记录
        if (coverAdd.equals("add"))
            coverAdd = "增加学分";
        else
            coverAdd = "覆盖原学分";
        recordsService.insertRecord(user.getId(), coverAdd, studentName, titleNo.getChineseByTitle(bonusType), getDate.getNow());

        //修改数据库记录
        Map map = new HashMap();
        map.put(bonusType, relValue);
        map.put("student_name", studentName);
        map.put("class_name", className);
        //修改学生某项分数
        informationService.changeStudentScore(map);

        //修改数据库总分
        informationService.updateStudentTotalScore(className, studentName);

    }

}
