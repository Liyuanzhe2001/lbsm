<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/htmleaf-demo.css">
    <style type="text/css">
        .form-bg {
            padding: 2em 0;
        }

        .form-horizontal {
            background: #fff;
            padding-bottom: 40px;
            border-radius: 15px;
            text-align: center;
        }

        .form-horizontal .heading {
            display: block;
            font-size: 35px;
            font-weight: 700;
            padding: 35px 0;
            border-bottom: 1px solid #f0f0f0;
            margin-bottom: 30px;
        }

        .form-horizontal .form-group {
            padding: 0 40px;
            margin: 0 0 25px 0;
            position: relative;
        }

        .form-horizontal .form-control {
            background: #f0f0f0;
            border: none;
            border-radius: 20px;
            box-shadow: none;
            padding: 0 20px 0 45px;
            height: 40px;
            transition: all 0.3s ease 0s;
        }

        .form-horizontal .form-control:focus {
            background: #e0e0e0;
            box-shadow: none;
            outline: 0 none;
        }

        .form-horizontal .form-group i {
            position: absolute;
            top: 12px;
            left: 60px;
            font-size: 17px;
            color: #c8c8c8;
            transition: all 0.5s ease 0s;
        }

        .form-horizontal .form-control:focus + i {
            color: #00b4ef;
        }

        .form-horizontal .fa-question-circle {
            display: inline-block;
            position: absolute;
            top: 12px;
            right: 60px;
            font-size: 20px;
            color: #808080;
            transition: all 0.5s ease 0s;
        }

        .form-horizontal .fa-question-circle:hover {
            color: #000;
        }

        .form-horizontal .main-checkbox {
            float: left;
            width: 20px;
            height: 20px;
            background: #11a3fc;
            border-radius: 50%;
            position: relative;
            margin: 5px 0 0 5px;
            border: 1px solid #11a3fc;
        }

        .form-horizontal .main-checkbox label {
            width: 20px;
            height: 20px;
            position: absolute;
            top: 0;
            left: 0;
            cursor: pointer;
        }

        .form-horizontal .main-checkbox label:after {
            content: "";
            width: 10px;
            height: 5px;
            position: absolute;
            top: 5px;
            left: 4px;
            border: 3px solid #fff;
            border-top: none;
            border-right: none;
            background: transparent;
            opacity: 0;
            -webkit-transform: rotate(-45deg);
            transform: rotate(-45deg);
        }

        .form-horizontal .main-checkbox input[type=checkbox] {
            visibility: hidden;
        }

        .form-horizontal .main-checkbox input[type=checkbox]:checked + label:after {
            opacity: 1;
        }

        .form-horizontal .text {
            float: left;
            margin-left: 7px;
            line-height: 20px;
            padding-top: 5px;
            text-transform: capitalize;
        }

        .form-horizontal .btn {
            float: right;
            font-size: 14px;
            color: #fff;
            background: #00b4ef;
            border-radius: 30px;
            padding: 10px 25px;
            border: none;
            text-transform: capitalize;
            transition: all 0.5s ease 0s;
        }

        @media only screen and (max-width: 479px) {
            .form-horizontal .form-group {
                padding: 0 25px;
            }

            .form-horizontal .form-group i {
                left: 45px;
            }

            .form-horizontal .btn {
                padding: 10px 20px;
            }
        }
    </style>
</head>
<body>
<div class="htmleaf-container">
    <div class="demo form-bg">
        <div class="container" style="margin-top: 100px">
            <div class="row">
                <div class="col-md-offset-3 col-md-6">
                    <div class="form-horizontal">
                        <span class="heading">账号注册</span>
                        <div class="form-group">
                            <input type="text" class="form-control" name="username" id="username" placeholder="用户名">
                            <i class="fa fa-user"></i>
                        </div>
                        <div class="form-group help">
                            <input type="password" class="form-control" name="password" id="password" placeholder="密码">
                            <i class="fa fa-lock"></i>
                        </div>
                        <div class="form-group help">
                            <input type="password" class="form-control" name="repassword" id="repassword"
                                   placeholder="确认密码">
                            <i class="fa fa-lock"></i>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="realname" id="realname" placeholder="真实姓名">
                            <i class="fa fa-user"></i>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="class" id="class" placeholder="班级">
                            <i class="fa fa-home"></i>
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="email" id="email" placeholder="邮箱">
                            <i class="fa fa-envelope-o"></i>
                        </div>
                        <div class="form-group">
                            <input type="button" id="back" name="back" class="btn btn-default" value="返回"
                                   onclick="javascrtpt:window.location.href='${pageContext.request.contextPath}/login.jsp'" style="float: left">
                            <input type="submit" id="btn" name="btn" class="btn btn-default" value="注册"
                                   onclick="submitMessage()">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script>
    function submitMessage() {
        if ($("#username").val() == "")
            alert("用户名不能为空");
        else if ($("#password").val() == "" || $("#repassword").val() == "")
            alert("密码不能为空");
        else if ($("#realname").val() == "")
            alert("真实姓名不能为空");
        else if ($("#class").val() == "")
            alert("班级不能为空");
        else if ($("#email").val() == "")
            alert("邮箱不能为空");
        else {
            $.ajax({
                url: "${pageContext.request.contextPath}/submit",
                data: {
                    username: $("#username").val(),
                    password: $("#password").val(),
                    repassword: $("#repassword").val(),
                    className: $("#class").val(),
                    email: $("#email").val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.userName === "true") {
                        if (data.pwdResult === "false") {
                            alert("两次密码不相同");
                        } else {
                            if (data.classResult === "noClass") {
                                alert("该班级不存在");
                            } else if (data.classResult === "false") {
                                alert("班级已存在，若需要更换班级管理员，请联系本班团支书");
                            } else {
                                if (data.emailResult === "false") {
                                    alert("邮箱格式错误");
                                } else {
                                    register();
                                }
                            }
                        }
                    } else {
                        alert("用户名已存在");
                    }
                }
            })
        }
    }

    function register() {
        $.ajax({
            url: "${pageContext.request.contextPath}/register",
            data: {
                username: $("#username").val(),
                password: $("#password").val(),
                realname: $("#realname").val(),
                className: $("#class").val(),
                email: $("#email").val()
            },
            success: function () {
                alert("注册成功");
                window.location.href = "${pageContext.request.contextPath}/login.jsp";
            }
        })
    }
</script>
</html>