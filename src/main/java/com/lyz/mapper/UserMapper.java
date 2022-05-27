package com.lyz.mapper;

import com.lyz.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    //获取用户信息
    User login(@Param("username") String username, @Param("password") String password);

    //修改是否上传了文件
    Integer changeIsUpload(@Param("id") Integer id);

    //注册新用户
    void registerNewUser(@Param("name") String realname, @Param("class") Integer classId, @Param("username") String username, @Param("password") String password, @Param("email") String email);

    //用户名查询用户
    User getUserByUsername(@Param("username") String username);

    //通过班级id查询用户
    User getUserByClassId(@Param("classId") Integer classId);

    //通过用户名、真实姓名、邮箱获取用户信息
    User getUserByUserName_RealName_Email(@Param("username") String username, @Param("name") String realname, @Param("email") String email);

    //修改用户密码
    void changePassword(@Param("id") Integer id, @Param("password") String newpassword);

    //修改用户邮箱
    void changeUserEmail(@Param("id") Integer id,@Param("email") String email);

}
