package com.lyz.controller;

import com.github.pagehelper.PageInfo;
import com.lyz.pojo.Information;
import com.lyz.pojo.Records;
import com.lyz.pojo.User;
import com.lyz.service.ClassService;
import com.lyz.service.RecordsServiceImpl;
import com.lyz.service.InformationService;
import com.lyz.service.UserService;
import com.lyz.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/home")
public class RecordsController {

    @Qualifier("recordsServiceImpl")
    @Resource
    private RecordsServiceImpl recordsService;

    @Qualifier("userServiceImpl")
    @Resource
    private UserService userService;

    @Qualifier("informationServiceImpl")
    @Resource
    private InformationService informationService;

    @Qualifier("classServiceImpl")
    @Resource
    private ClassService classService;

    //前往首页
    @RequestMapping("/records")
    public String goHome(@RequestParam(required = false, defaultValue = "1", value = "page") Integer page, HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        Integer userId = user.getId();
        List<Records> list = recordsService.getAllRecords(page, userId);
        PageInfo pageInfo = new PageInfo(list, 10);
        model.addAttribute("pageInfo", pageInfo);
        return "records";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        recordsService.deleteRecord(Integer.parseInt(id));
        return "redirect:/home/records";
    }

    //文件上传页面
    @RequestMapping("/toUpload")
    public String upload() {
        return "upload";
    }

    //文件上传 使用file.Transto
    @RequestMapping("/upload")
    public String fileUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws Exception {
        if (file == null || file.isEmpty()) {
            request.setAttribute("msg", "上传失败");
            return "upload";
        }

        //获得班级
        String className = (String) request.getSession().getAttribute(Constants.USER_CLASS);

        //上传路径保存设置
        String path = request.getSession().getServletContext().getRealPath("/upload/" + className);

        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdirs();
        }
        file.transferTo(new File(path + "/tmp.xlsx"));
        File realFile = new File(path + "/tmp.xlsx");

        Workbook workbook;
        String type = MineTypeUtils.getFileType(path + "/tmp.xlsx");
        FileInputStream inputStream = new FileInputStream(path + "/tmp.xlsx");
        if (type.equals("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (type.equals("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            throw new FileTypeException("Wrong Excel Type");
        }

        Sheet sheet = workbook.getSheetAt(0);

        int n = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
                Cell cell = sheet.getRow(i).getCell(j);
                String value = Excel.getValue(cell);
                if (!value.equals(Excel.correctHeaderList.get(n++))) {
                    request.setAttribute("error", "true");
                    realFile.delete();
                    return "upload";
                }
            }
        }

        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        Short isUpload = user.getIsUpload();
        if (isUpload != 1) {
            userService.changeIsUpload(user.getId());
            user.setIsUpload((short) 1);
        }
        request.getSession().setAttribute(Constants.USER_SESSION, user);

        request.setAttribute("msg", "上传成功");

        //删除旧学生信息
        informationService.deleteAllInformation(className);

        inputStream.close();

        File delFile = new File(path + "/" + className + "班学分.xlsx");
        if (delFile.exists())
            delFile.delete();

        boolean b = realFile.renameTo(new File(path + "/" + className + "班学分.xlsx"));

        //Excel重新初始化
        Excel.init(request.getSession());
        Excel.removeAllFormula();

        List<Information> allInformation = Excel.getAllInformation();

        informationService.insertStudentsInformation(allInformation);

        //删除旧记录
        recordsService.deleteRecords(user.getId());

        Excel.save();

        return "upload";
    }

    @RequestMapping("/downloadMouldFile")
    //下载模板
    public String downloadMouldFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/upload");
        downloadFile(response, path, "模板.xlsx");
        return "upload";
    }

    @RequestMapping("/downloadFile")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        String path;
        Short isUpload = user.getIsUpload();
        String fileName;
        if (isUpload == 0) {
            path = request.getSession().getServletContext().getRealPath("/upload");
            fileName = "模板.xlsx";
            downloadFile(response, path, fileName);
        } else {
            String className = (String) request.getSession().getAttribute(Constants.USER_CLASS);
            path = request.getSession().getServletContext().getRealPath("/upload/" + className);
            Sheet sheet = Excel.sheet;
            int lastRow = Excel.lastRow;///最后一行的下标

            String formulaNumberOfPeople = "COUNT($AD$3:$AD$" + (lastRow + 1) + ")";
            for (int i = 2; i <= lastRow; i++) {
                sheet.getRow(i).getCell(titleNo.getNumByTitle("classRanking") - 1).setCellFormula("RANK(AD" + (i + 1) + ",$AD$3:$AD$" + (lastRow + 1) + ")+COUNTIF($AD$3:$AD" + (i + 1) + ",AD" + (i + 1) + ")-1");
                sheet.getRow(i).getCell(titleNo.getNumByTitle("average") - 1).setCellFormula("ROUND(SUM($AD$3:$AD$" + (lastRow + 1) + ")/$AJ$3,0)");
                sheet.getRow(i).getCell(titleNo.getNumByTitle("numberOfPeople") - 1).setCellFormula(formulaNumberOfPeople);
            }
            Excel.save();
            try {
                FolderToZipUtil.zip(className, path, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "upload";
    }

    public void downloadFile(HttpServletResponse response, String path, String fileName) throws IOException {
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

        File file = new File(path, fileName);
        FileInputStream input = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();

        byte[] buff = new byte[1024];
        int index = 0;
        while ((index = input.read(buff)) != -1) {
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();

    }

}
