package com.lyz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private static final long serialVersionUID = 1L;

    //唯一id
    private Integer id;

    //姓名
    private String name;

    //班级id
    private Integer classId;

    //登录账号/用户名
    private String username;

    //登录密码
    private String password;

    //是否上传了excel（0没有，1有）
    private Short isUpload;

    //邮箱
    private String email;

}
