package com.lyz.controller;

import com.lyz.pojo.User;
import com.lyz.service.ClassService;
import com.lyz.service.UserService;
import com.lyz.util.*;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Qualifier("userServiceImpl")
    @Resource
    private UserService userService;

    @Qualifier("classServiceImpl")
    @Resource
    private ClassService classService;

    //账号登录
    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model, String username, String password) throws Exception {
        if (StringUtils.isNullOrEmpty(username)) {
            model.addAttribute("msg", "账号不能为空");
            return "../../login";
        }
        if (StringUtils.isNullOrEmpty(password)) {
            model.addAttribute("msg", "密码不能为空");
            return "../../login";
        }
        User user = userService.login(username, password);
        if (user == null) {
            model.addAttribute("msg", "账号或密码错误");
            return "../../login";
        } else {
            request.getSession().setAttribute(Constants.USER_SESSION, user);
            //获取班级
            String className = classService.getClassByClassId(user.getClassId());
            request.getSession().setAttribute(Constants.USER_CLASS, className);

            Short isUpload = user.getIsUpload();
            System.out.println(isUpload);
            if (isUpload == 1) {
                //获得第一张表
                Excel.init(request.getSession());
                Excel.removeAllFormula();
            }
            return "redirect:/home/records";
        }
    }

    //退出登录
    @RequestMapping("/goOut")
    public String goOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "../../login";
    }

}
