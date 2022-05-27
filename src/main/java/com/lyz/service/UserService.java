package com.lyz.service;

import com.lyz.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {

    //获取用户信息
    User login(String username, String password);

    //修改是否上传了文件
    void changeIsUpload(Integer id);

    //注册新用户
    void registerNewUser(String realname, Integer classId, String username, String password, String email);

    //用户名查询用户
    User getUserByUsername(String username);

    //通过班级id查询用户
    User getUserByClassId(Integer classId);

    //通过用户名、真实姓名、邮箱获取用户信息
    User getUserByUserName_RealName_Email(String username, String realname, String email);

    //修改用户密码
    void changePassword(Integer id, String newpassword);

    //修改用户邮箱
    void changeUserEmail(Integer id, String email);
}
