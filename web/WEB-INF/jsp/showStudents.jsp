<%@ page import="com.github.pagehelper.PageInfo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" class="no-js">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/fonts/font-awesome-4.2.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu_topside.css"/>
</head>
<body>

<%--个人信息弹窗--%>
<button type="button" style="display: none" id="show" name="show" data-toggle="modal"
        data-target="#modal_1" data-whatever="@mdo">
</button>
<div class="modal fade" id="modal_1" tabindex="-1" aria-labelledby="modalLabel_1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalLabel_1" style="font-size: 14px;color: grey;">选择操作</h5>
                <button type="button" id="close" name="close" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group" style="text-align: center">
                        <button type="button" class="btn btn-primary" style="margin-right: 30px;"
                                onClick="changePassword()">修改密码
                        </button>
                        <button type="button" class="btn btn-primary" style="margin-left: 30px;"
                                onClick="changeEmail()">修改邮箱
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<button type="button" style="display: none" id="changePassword" name="changePassword"
        data-toggle="modal" data-target="#modal_2" data-whatever="@mdo"></button>
<div class="modal fade" id="modal_2" tabindex="-1" aria-labelledby="modalLabel_2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalLabel_2" style="font-size: 14px;color: grey;">修改密码</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="originalPassword" style="color: black;">原密码</label>
                        <input type="password" class="form-control" id="originalPassword">
                    </div>
                    <div class="form-group">
                        <label for="password" style="color: black;">确认密码</label>
                        <input type="password" class="form-control" id="password">
                    </div>
                    <div class="form-group">
                        <label for="repassword" style="color: black;">再次输入密码</label>
                        <input type="password" class="form-control" id="repassword">
                    </div>
                    <button type="button" class="btn btn-primary" onclick="changePasswordButton()">Submit</button>
                </form>
            </div>
        </div>
    </div>
</div>
<button type="button" style="display: none;" id="changeEmail" name="changeEmail"
        data-toggle="modal" data-target="#modal_3" data-whatever="@mdo"></button>
<div class="modal fade" id="modal_3" tabindex="-1" aria-labelledby="modalLabel_3" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalLabel_3" style="font-size: 14px;color: grey;">修改邮箱</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label for="originalEmail" style="color: black;">原邮箱</label>
                        <input type="text" class="form-control" id="originalEmail">
                    </div>
                    <div class="form-group">
                        <label for="CAPTCHA" style="color: black">验证码</label><br>
                        <input type="text" class="form-control" id="CAPTCHA"
                               style="width: 20%;display: inline-block;">
                        <button type="button" class="btn btn-primary" id="sendBtn" name="sendBtn"
                                style="margin-left: 20px" onclick="getCAPTCHA()">发送
                        </button>
                    </div>
                    <div class="form-group">
                        <label for="newEmail" style="color: black;">新邮箱</label>
                        <input type="text" class="form-control" id="newEmail" disabled>
                    </div>
                    <button type="button" id="changeEmailBtn" class="btn btn-primary" onclick="changeEmailButton()"
                            disabled>Submit
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="menu-wrap" style="font-size: 15px;">
        <nav class="menu-top">
            <div class="profile">
                <img src="${pageContext.request.contextPath}/img/user.png" height="42px" width="42px"
                     onclick="PersonalInformation()" style="cursor: pointer;"/>
                <span><%=request.getSession().getAttribute("className")%></span>
            </div>
        </nav>
        <nav class="menu-side">
            <a href="${pageContext.request.contextPath}/home/records">操作记录</a>
            <a href="${pageContext.request.contextPath}/home/toUpload">上传我的Excel</a>
            <a data-toggle="collapse" href="#changeExcel" role="button" aria-expanded="false"
               aria-controls="changeExcel">修改学生信息</a>
            <div class="collapse" id="changeExcel" style="margin-left: 40px">
                <div class="card card-body" style="font-size: 13px;">
                    <a href="${pageContext.request.contextPath}/changeInfo/toAddStudent">添加学生信息</a> <br>
                    <a href="${pageContext.request.contextPath}/changeInfo/showStudents">查看学生信息</a> <br>
                    <a href="${pageContext.request.contextPath}/changeInfo/toModifyStudentInfo">修改学生基本信息</a> <br>
                    <a href="${pageContext.request.contextPath}/changeInfo/toModifyOneStudent">修改单个学生学分信息</a><br>
                    <a href="${pageContext.request.contextPath}/changeInfo/toModifyMoreStudent">修改多个学生学分信息</a><br>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/home/downloadFile">下载我的Excel</a>
            <a href="${pageContext.request.contextPath}/goOut">退出登录</a>
        </nav>
    </div>
    <button class="menu-button" id="open-button">Open Menu</button>
    <div class="content-wrap">
        <div class="content">
            <header class="codrops-header" style="padding-bottom: 400px;">
                <div class="codrops-demos">
                    <form action="${pageContext.request.contextPath}/changeInfo/findStudent" method="post"
                          style="float: right"
                          class="form-inline">
                        <span style="color: red;font-weight: bold">${error}</span>
                        <input type="text" name="findStudent" placeholder="请输入要查询的姓名/学号" class="form-control" required
                               oninvalid="setCustomValidity('输入不能为空');" oninput="setCustomValidity('');">
                        <input type="submit" value="查询" class="btn btn-primary">
                    </form>
                    <table class="table" style="color: rgb(59,59,59)">
                        <thead>
                        <tr>
                            <th scope="col">序号</th>
                            <th scope="col">姓名</th>
                            <th scope="col">身份证号</th>
                            <th scope="col">学号</th>
                            <th scope="col">总分</th>
                            <th scope="col">班级排名</th>
                            <th scope="col">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="students" items="${pageInfo.list}">
                            <tr>
                                <td>${students.number}</td>
                                <td>${students.name}</td>
                                <td>${students.idNumber}</td>
                                <td>${students.studentId}</td>
                                <td>${students.totalScore}</td>
                                <td>${students.classRanking}</td>
                                <td>
                                    <a onclick="allMsg(${students.number})" style="cursor: pointer;">详细信息</a>
                                </td>

                                <button type="button" <%--class="btn btn-primary"--%> style="display: none"
                                        id="${students.number}"
                                        name="${students.number}"
                                        data-toggle="modal"
                                        data-target="#${students.studentId}" data-whatever="@mdo"> Launch demo modal
                                </button>

                                <div class="modal fade" id="${students.studentId}" tabindex="-1" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content" style="font-size: 15px;">
                                            <div class="modal-header">
                                                <p style="color: black">${students.name}</p>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close"><span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable"
                                                 style="margin-left: 40px;padding-bottom:20px;color:black;">
                                                <div style="width: 290px;display: inline-block;">
                                                    序号：${students.number}<br>
                                                    学号：${students.studentId}<br>
                                                    年级：${students.grade}<br>
                                                    校纪校规：${students.schoolDisciplineAndRules}<br>
                                                    教学成绩：${students.teachingAchievement}<br>
                                                    政企实践：${students.governmentEnterprisePractice}<br>
                                                    个人荣誉：${students.personalHonor}<br>
                                                    自主管理：${students.selfManagement}<br>
                                                    继续教育：${students.continuingEducation}<br>
                                                    终身学习：${students.lifelongLearning}<br>
                                                    爱心爱校：${students.loveSchool}<br>
                                                    出国留学：${students.studyAbroad}<br>
                                                    高级证书：${students.advancedCertificate}<br>
                                                    专业证书：${students.professionalCertificate}<br>
                                                    总分：${students.totalScore}<br>
                                                    平均数：${students.average}<br>
                                                    班长：${students.monitor}<br>
                                                    人数：${students.numberOfPeople}
                                                </div>
                                                <div style="width: 290px;float:right;">
                                                    身份证号：${students.idNumber}<br>
                                                    班级：${students.className}<br>
                                                    爱党爱校：${students.loveThePartyAndLoveTheCountry}<br>
                                                    公益活动：${students.publicBenefitActivities}<br>
                                                    青年大学习：${students.youthLearning}<br>
                                                    军事学习：${students.militaryLearning}<br>
                                                    文体活动：${students.recreationalActivities}<br>
                                                    创新创业：${students.innovationAndEntrepreneurship}<br>
                                                    时间管理：${students.timeManagement}<br>
                                                    学生组织：${students.studentOrganization}<br>
                                                    国际技能：${students.internationalSkills}<br>
                                                    国际赛事：${students.internationalEvents}<br>
                                                    获得驾照：${students.getDriverLicense}<br>
                                                    技术技能：${students.technicalSkills}<br>
                                                        <%--                                                    班级排名：${students.classRanking}<br>--%>
                                                    班级排名：${students.classRanking}<br>
                                                    辅导员：${students.instructor}<br>
                                                    团支书：${students.leagueBranchSecretary}<br>
                                                    分院：${students.branch}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="row">
                        <div class="col-md-6">
                            第${pageInfo.pageNum}页，共${pageInfo.pages}页，共${pageInfo.total}条记录
                        </div>
                        <ul class="pagination">
                            <li><a href="${pageContext.request.contextPath}/changeInfo/showStudents?page=1">&laquo;</a>
                            </li>
                            <c:forEach items="${pageInfo.navigatepageNums}" var="page">
                                <c:if test="${page==pageInfo.pageNum}">
                                    <li class="page-item active"><a class="page-link" href="#">${page}</a></li>
                                </c:if>
                                <c:if test="${page!=pageInfo.pageNum}">
                                    <li class="page-item"><a class="page-link"
                                                             href="${pageContext.request.contextPath}/changeInfo/showStudents?page=${page}">${page}</a>
                                    </li>
                                </c:if>
                            </c:forEach>
                            <li>
                                <a href="${pageContext.request.contextPath}/changeInfo/showStudents?page=${pageInfo.pages}">&raquo;</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </header>
        </div>
    </div>
    <!-- /content-wrap -->
</div>
<!-- /container -->
<script src="${pageContext.request.contextPath}/js/classie.js"></script>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
<script>

    var wait = 60;

    document.getElementById("close").onclick = function () {
        document.getElementById("originalEmail").value = "";
        document.getElementById("CAPTCHA").value = "";
        document.getElementById("newEmail").value = "";
        document.getElementById("originalPassword").value = "";
        document.getElementById("password").value = "";
        document.getElementById("repassword").value = "";
    }

    function time() {
        if (wait == 0) {
            document.getElementById("sendBtn").disabled = false;
            document.getElementById("sendBtn").innerHTML = "发送";
            wait = 60;
        } else {
            document.getElementById("sendBtn").disabled = true;
            document.getElementById("sendBtn").innerHTML = wait + "秒后可以重新发送";
            wait--;
            setTimeout(function () {
                time()
            }, 1000)
        }
    }

    function PersonalInformation() {
        $("#show").click();
    }

    function changePassword() {
        $("#close").click();
        $("#changePassword").click();
    }

    function changeEmail() {
        $("#close").click();
        $("#changeEmail").click();
    }

    function changePasswordButton() {
        if ($("#originalPassword").val() == "")
            alert("原密码不能为空");
        else if ($("#password").val() == "" || $("#repassword").val() == "")
            alert("密码不能为空");
        else {
            $.ajax({
                url: "${pageContext.request.contextPath}/changePassword",
                data: {
                    originalPassword: $("#originalPassword").val(),
                    password: $("#password").val(),
                    repassword: $("#repassword").val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.originalPassword == "false") {
                        alert("原密码错误");
                    } else {
                        if (data.newPassword == "false") {
                            alert("两次密码不相同");
                        } else {
                            alert("修改成功");
                            window.location.href = "${pageContext.request.contextPath}/login.jsp";
                        }
                    }
                }
            })
        }
    }

    function getCAPTCHA() {
        if ($("#originalEmail").val() == "") {
            alert("原邮箱不能为空");
        } else {
            $.ajax({
                url: "${pageContext.request.contextPath}/getCAPTCHA",
                data: {
                    originalEmail: $("#originalEmail").val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.email == "true") {
                        document.getElementById("changeEmailBtn").removeAttribute("disabled");
                        document.getElementById("newEmail").removeAttribute("disabled");
                        time();
                        alert("已发送至邮箱，请稍候...");

                    } else {
                        alert("原邮箱不是该用户绑定的邮箱，请重新输入");
                    }
                }
            })
        }
    }

    function changeEmailButton() {
        if ($("#originalEmail").val() == "") {
            alert("原邮箱不能为空");
        } else if ($("#CAPTCHA").val() == "") {
            alert("验证码不能为空");
        } else if ($("#newEmail").val() == "") {
            alert("新邮箱不能为空");
        } else {
            $.ajax({
                url: "${pageContext.request.contextPath}/changeEmail",
                data: {
                    CAPTCHA: $("#CAPTCHA").val(),
                    newEmail: $("#newEmail").val()
                },
                success: function (data) {
                    if (data.CAPTCHAResult == "true") {
                        if (data.email == "false") {
                            alert("邮箱格式错误");
                        } else {
                            alert("邮箱修改成功");
                            $("#close").click();
                        }
                    } else {
                        alert("验证码错误");
                    }
                }
            })
        }
    }

    function allMsg(param) {
        document.getElementById(param.toString()).click();
    }

</script>
</body>
</html>