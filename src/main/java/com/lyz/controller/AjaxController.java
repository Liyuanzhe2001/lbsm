package com.lyz.controller;

import com.alibaba.fastjson.JSON;
import com.lyz.pojo.Class;
import com.lyz.pojo.User;
import com.lyz.service.ClassService;
import com.lyz.service.UserService;
import com.lyz.util.Constants;
import com.lyz.util.JSONUtil;
import com.lyz.util.sendEmail;
import com.mysql.jdbc.StringUtils;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class AjaxController {

    @Qualifier("classServiceImpl")
    @Resource
    private ClassService classService;

    @Qualifier("userServiceImpl")
    @Resource
    private UserService userService;

    @RequestMapping("/submit")
    public void confirmClass(String username, String password, String repassword, String className, String email, HttpServletResponse response) {
        Map<String, String> resultMap = new HashMap<>();

        //判断用户名是否重复
        User userByName = userService.getUserByUsername(username);
        if (userByName == null) {
            resultMap.put("userName", "true");
        } else {
            resultMap.put("userName", "false");
        }

        //判断两次密码是否相同
        if (!StringUtils.isNullOrEmpty(password) && !StringUtils.isNullOrEmpty(repassword)) {
            if (password.equals(repassword)) {
                resultMap.put("pwdResult", "true");
            } else {
                resultMap.put("pwdResult", "false");
            }
        } else {
            resultMap.put("pwdResult", "pwdIsNull");
        }

        //判断该班级是否存在，判断该班级是否已经有管理员
        Class classes = classService.getClassByName(className);//判断班级是否存在
        if (classes == null) {
            //返回 不存在该班级
            resultMap.put("classResult", "noClass");
        } else {//该班级存在，则判断是否已经存在管理员
            Integer classesId = classes.getId();
            User user = userService.getUserByClassId(classesId);
            if (user == null) {
                resultMap.put("classResult", "true");
            } else {
                resultMap.put("classResult", "false");
            }
        }

        //判断邮箱格式
        String emailMatcher = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
        boolean isMatch = Pattern.matches(emailMatcher, email);
        if (isMatch)
            //邮箱格式正确
            resultMap.put("emailResult", "true");
        else
            //邮箱格式错误
            resultMap.put("emailResult", "false");

        JSONUtil.returnJSON(response, resultMap);

    }

    @RequestMapping("/register")
    public void register(String username, String password, String realname, String className, String email) {
        Integer classId = classService.getClassIdByClassName(className);
        userService.registerNewUser(realname, classId, username, password, email);
    }

    @RequestMapping("/forgetPwd")
    public void forgetPwd(String username, String realname, String email, HttpServletResponse response) {
        System.out.println(username);
        System.out.println(realname);
        System.out.println(email);
        Map<String, String> resultMap = new HashMap<>();
        User user = userService.getUserByUserName_RealName_Email(username, realname, email);
        //输入信息正确，发送密码到邮箱
        if (user != null) {
            resultMap.put("result", "true");
            String password = user.getPassword();
            sendEmail sendEmail = new sendEmail(email);
            sendEmail.addInfo("<h1>【找回密码】</h1><br>您的用户名： " + username + " ，您的密码：" + password);
            sendEmail.start();
        } else {
            resultMap.put("result", "false");
        }
        JSONUtil.returnJSON(response, resultMap);
    }

    @RequestMapping("/changePassword")
    public void changePassword(String originalPassword, String password, String repassword, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> resultMap = new HashMap<>();
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        String realPassword = user.getPassword();

        //判断原密码是否正确
        if (originalPassword.equals(realPassword)) {
            resultMap.put("originalPassword", "true");
        } else {
            resultMap.put("originalPassword", "false");
        }

        //判断两次输入密码是否相同
        if (password.equals(repassword)) {
            resultMap.put("newPassword", "true");
        } else {
            resultMap.put("newPassword", "false");
        }

        //如果原密码输入正确，两次输入新密码相同，就修改密码
        if (resultMap.get("originalPassword").equals("true") && resultMap.get("newPassword").equals("true")) {
            userService.changePassword(user.getId(), password);
            request.getSession().invalidate();
        }
        JSONUtil.returnJSON(response, resultMap);

    }

    @RequestMapping("/getCAPTCHA")
    public void getCAPTCHA(String originalEmail, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        String email = user.getEmail();
        Map<String, String> resultMap = new HashMap<>();

        if (email.equals(originalEmail)) {
            resultMap.put("email", "true");
            String randomNum = ((int) ((Math.random() * 9 + 1) * 100000)) + "";
            sendEmail sendEmail = new sendEmail(user.getEmail());
            sendEmail.addInfo("<h1>【找回密码】</h1><br>验证码：" + randomNum);
            sendEmail.start();
            request.getSession().setAttribute(Constants.USER_CAPTCHA, randomNum);
        } else {
            resultMap.put("email", "false");
        }
        JSONUtil.returnJSON(response, resultMap);
    }

    @RequestMapping("/changeEmail")
    public void changeEmail(String CAPTCHA, String newEmail, HttpServletRequest request, HttpServletResponse response) {
        String inCAPTCHA = (String) request.getSession().getAttribute(Constants.USER_CAPTCHA);
        Map<String, String> resultMap = new HashMap<>();
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        System.out.println("验证码为" + inCAPTCHA);
        System.out.println("输入的为" + CAPTCHA);
        if (!StringUtils.isNullOrEmpty(inCAPTCHA) && inCAPTCHA.equals(CAPTCHA)) {
            resultMap.put("CAPTCHAResult", "true");
            String emailMatcher = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+";
            boolean isMatch = Pattern.matches(emailMatcher, newEmail);
            if (isMatch) {
                resultMap.put("email", "true");
                userService.changeUserEmail(user.getId(), newEmail);
                request.getSession().removeAttribute(Constants.USER_CAPTCHA);
            } else {
                resultMap.put("email", "false");
            }
        } else {
            resultMap.put("CAPTCHAResult", "false");
        }
        JSONUtil.returnJSON(response, resultMap);
    }

}
