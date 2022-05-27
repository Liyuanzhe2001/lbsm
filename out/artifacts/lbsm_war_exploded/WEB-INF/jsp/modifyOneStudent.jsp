<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" class="no-js">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改单个学生信息</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/demo.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/fonts/font-awesome-4.2.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu_topside.css"/>
    <link href="http://fonts.googleapis.com/css?family=Roboto:400,300,700,900|Roboto+Condensed:400,300,700"
          rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/dropify.min.css">
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
            <header class="codrops-header" style="height: 1000px;">
                <div class="codrops-demos">
                    <%--表单内容--%>
                    <form method="post" id="main_form">
                        <div class="form-group">
                            <label>选择学生&nbsp;*</label>
                            <select class="form-control form-control-sm" id="studentName" name="studentName">
                                <option value="选择学生" selected>选择学生</option>
                                <c:forEach var="student" items="${studentList}">
                                    <option>${student.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>选择加分类型&nbsp;*</label>
                            <select class="form-control form-control-sm" id="bonusType" name="bonusType">
                                <option value="选择加分类型" selected>选择加分类型</option>
                                <option value="loveThePartyAndLoveTheCountry">爱党爱国</option>
                                <option value="schoolDisciplineAndRules">校纪校规</option>
                                <option value="publicBenefitActivities">公益活动</option>
                                <option value="teachingAchievement">教学成绩</option>
                                <option value="youthLearning">青年大学习</option>
                                <option value="governmentEnterprisePractice">政企实践</option>
                                <option value="militaryLearning">军事学习</option>
                                <option value="personalHonor">个人荣誉</option>
                                <option value="recreationalActivities">文体活动</option>
                                <option value="selfManagement">自主管理</option>
                                <option value="innovationAndEntrepreneurship">创新创业</option>
                                <option value="continuingEducation">继续教育</option>
                                <option value="timeManagement">时间管理</option>
                                <option value="lifelongLearning">终身学习</option>
                                <option value="studentOrganization">学生组织</option>
                                <option value="loveSchool">爱心爱校</option>
                                <option value="internationalSkills">国际技能</option>
                                <option value="studyAbroad">出国留学</option>
                                <option value="internationalEvents">国际赛事</option>
                                <option value="advancedCertificate">高级证书</option>
                                <option value="getDriverLicense">获得驾照</option>
                                <option value="professionalCertificate">专业证书</option>
                                <option value="technicalSkills">技术技能</option>
                            </select>
                        </div>
                        <label>加分日期&nbsp;*</label>
                        <div class="form-group">
                            <select class="form-control form-control-sm" id="year" name="year"
                                    style="width: 80px;display: inline-block;">
                                <option>${year}</option>
                                <option>${year+1}</option>
                                <option>${year+2}</option>
                                <option>${year+3}</option>
                            </select>
                            <span>年</span>
                            <select class="form-control form-control-sm" id="month" name="month"
                                    style="width: 70px;display: inline-block;">
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                                <option>6</option>
                                <option>7</option>
                                <option>8</option>
                                <option>9</option>
                                <option>10</option>
                                <option>11</option>
                                <option>12</option>
                            </select>
                            <span>月</span>
                        </div>
                        <label>是否覆盖原内容&nbsp;*</label>
                        <div class="form-group">
                            <input type="radio" id="add" name="cover_add" checked="checked" value="add">
                            <label for="add">追加</label>&nbsp;&nbsp;
                            <input type="radio" id="cover" name="cover_add" value="cover">
                            <label for="cover">覆盖</label>
                        </div>
                        <div class="form-group">
                            <label for="bonusIntroduction" title="奖项+分数">加分介绍&nbsp;*</label>
                            <textarea class="form-control" id="bonusIntroduction" name="bonusIntroduction" rows="5"
                                      style="resize: none;"></textarea>
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-primary" onclick="judge()">提交</button>
                        </div>
                    </form>

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
<script src="${pageContext.request.contextPath}/dist/js/dropify.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if ($("#error").val() == "true") {
            $("#excelBtn").click();
            $("#error").val("");
        }

        // Basic
        $('.dropify').dropify({
            messages: {
                'default': '点击或拖拽文件到这里',
                'replace': '点击或拖拽文件到这里来替换文件',
                'remove': '移除文件',
                'error': '对不起，你上传的文件太大了'
            }
        });

    });

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

    $("#imgUpload").on('change', function () {
        if (typeof (FileReader) != "undefined") {
            var image_holder = $("#image-holder");
            image_holder.empty();

            var reader = new FileReader();
            reader.onload = function (e) {
                $("<img />", {
                    "src": e.target.result,
                    "class": "thumb-image",
                    "width": "150px",
                    "height": "200px"
                }).appendTo(image_holder);

            }
            image_holder.show();
            reader.readAsDataURL($(this)[0].files[0]);
        } else {
            alert("你的浏览器不支持FileReader.");
        }
    });

    function judge() {
        var studentName = $("#studentName").val();
        var bonusType = $("#bonusType").val();
        var bonusIntroduction = $("#bonusIntroduction").val();

        if (studentName == "选择学生") {
            alert("请选择学生");
        } else if (bonusType == "选择加分类型") {
            alert("请选择加分类型");
        } else if (bonusIntroduction == "" || bonusIntroduction == null) {
            alert("加分介绍不能为空");
        } else {
            var year = $("#year").val();
            var month = $("#month").val();
            var coverAdd = $("input[name='cover_add']:checked").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/changeInfo/modifyOneStudent",
                data: {
                    studentName:studentName,
                    bonusType:bonusType,
                    year:year,
                    month:month,
                    coverAdd:coverAdd,
                    bonusIntroduction:bonusIntroduction,
                },
                success: function () {
                    alert("提交成功");
                }
            })
        }

    }

</script>
</body>
</html>