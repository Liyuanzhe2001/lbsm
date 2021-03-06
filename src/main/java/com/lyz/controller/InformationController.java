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
            model.addAttribute("error", "?????????");
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
        //????????????????????????
        user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        Integer classId = user.getClassId();
        classService.addNumberOfPeople(classId);
//        Excel.lastRow++;

        Class classInfo = classService.getClassById(classId);

        //?????????????????????
        informationService.changeNumberOfPeople(classInfo.getName());

        informationService.addNewStudentInformation(classInfo.getNumberOfPeople(), studentName, studentIdNumber, studentId, classInfo.getName(), classInfo.getGrade(), classInfo.getNumberOfPeople(), classInfo.getInstructor(), classInfo.getMonitor(), classInfo.getLeagueBranchSecretary(), classInfo.getNumberOfPeople(), classInfo.getBranch());

        request.getSession().getAttribute(Constants.USER_CLASS);

        //??????????????????
        recordsService.insertRecord(user.getId(), "????????????", studentName, "/", getDate.getNow());

        //???????????????
        informationService.changeAverage(classInfo.getName());

        //??????????????????
        List<Information> informationById = informationService.getInformationById(studentId, classInfo.getName());
        Information student = informationById.get(0);

        //??????????????????excel??????
        Excel.addNewStudentInformation(student);

        request.setAttribute("msg", "????????????");
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
        model.addAttribute("msg", "????????????");
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
        System.out.println("??????");
        //???????????????????????????????????????????????????
        String className = (String) request.getSession().getAttribute(Constants.USER_CLASS);
        //?????????
        user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        String leagueBranchSecretary = user.getName();

        //????????????
        writeIntoExcel(studentName, className, bonusType, year, month, leagueBranchSecretary, bonusIntroduction, coverAdd);

        //??????
        Excel.save();

        System.out.println("??????");

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
        System.out.println("??????modifyMoreStudent");
        //???????????????????????????????????????????????????
        String className = (String) request.getSession().getAttribute(Constants.USER_CLASS);
        //?????????
        user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        String leagueBranchSecretary = user.getName();


        if (names != null && names.length != 0) {
            for (String studentName : names) {
                //????????????
                writeIntoExcel(studentName, className, bonusType, year, month, leagueBranchSecretary, bonusIntroduction, coverAdd);
            }
        } else {
            List<Map<String, String>> students = doString.getStrings(bonusIntroduction);
            String studentName = "null";
            for (Map<String, String> student : students) {
                try {
                    studentName = student.get("name");
                    bonusIntroduction = student.get("content");
                    //????????????
                    writeIntoExcel(studentName, className, bonusType, year, month, leagueBranchSecretary, bonusIntroduction, coverAdd);
                } catch (Exception e) {
                    System.out.println(studentName);
                }
            }
        }
        Excel.save();
        System.out.println("??????modifyMoreStudent");
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
//        System.out.println("??????addBonusCertificate");
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

        //????????????
        int lineNumber = informationService.getStudentRanking(studentName, className) + 2;
        //????????????
        Integer columnNumber = titleNo.getNumByTitle(bonusType) - 1;

        //????????????????????????????????????
        String cellComment;
        try {
            cellComment = sheet.getRow(lineNumber).getCell(columnNumber).getCellComment().getString().toString();
        } catch (Exception e) {
            cellComment = "";
        }

        //????????????????????????xxxx???xx???  ?????????\n  ???????????????
        String comment = year + "???" + month + "???  " + leagueBranchSecretary + "\r\n" + bonusIntroduction;

        //???????????????????????????
        Integer newValue = doString.getLastInteger(bonusIntroduction);
        //????????????????????????
        Cell oldScoreCell = sheet.getRow(lineNumber).getCell(columnNumber);
        Integer oldScoreValue = Integer.parseInt(Excel.getValue(oldScoreCell));

        //?????????
        Integer relValue = 0;
        //?????????
        Cell totalScoreCell = sheet.getRow(lineNumber).getCell(titleNo.getNumByTitle("totalScore") - 1);
        int totalValue = Integer.parseInt(Excel.getValue(totalScoreCell));

        //??????????????????
        Integer lastRow = Excel.lastRow;
        //?????????????????????????????????????????????
        //??????????????? ?????? ???????????????
        if (coverAdd.equals("add") && !StringUtils.isNullOrEmpty(cellComment)) {
            //????????????????????????????????????xxxxxxxxxxxxxxxx???
            String lastStr = cellComment.substring(cellComment.indexOf("?????????"));

            //????????? ????????????????????????
            cellComment = cellComment.substring(0, cellComment.indexOf("?????????"));

            //?????????????????????????????????
            comment = cellComment + comment;

            //???????????????????????? ???????????? ????????????
            String s = doString.insertNumber(lastStr, newValue);

            //?????????????????????
            relValue = doString.getLastInteger(s);
            //????????????
            totalValue = newValue + totalValue;

            //??????
            comment = comment + "\r\n" + s;

        } else {//???????????????
            //?????????????????????????????????
            relValue = newValue;

            //??????
            comment = comment + "\r\n" + "?????????" + relValue + "???";

            totalValue = totalValue - oldScoreValue + relValue;
        }

        //?????????????????????
        sheet.getRow(lineNumber).getCell(columnNumber).setCellValue(relValue);
        sheet.getRow(lineNumber).getCell(titleNo.getNumByTitle("totalScore") - 1).setCellValue(totalValue);

        //??????????????????
        Excel.setComment(lineNumber, columnNumber, comment);

        if (coverAdd.equals("add")) {
            //??????
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
            //??????
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

        //??????????????????
        if (coverAdd.equals("add"))
            coverAdd = "????????????";
        else
            coverAdd = "???????????????";
        recordsService.insertRecord(user.getId(), coverAdd, studentName, titleNo.getChineseByTitle(bonusType), getDate.getNow());

        //?????????????????????
        Map map = new HashMap();
        map.put(bonusType, relValue);
        map.put("student_name", studentName);
        map.put("class_name", className);
        //????????????????????????
        informationService.changeStudentScore(map);

        //?????????????????????
        informationService.updateStudentTotalScore(className, studentName);

    }

}
