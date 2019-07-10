<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/p1.jpg" type="image/x-icon">
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">
    <link rel="stylesheet" href="${APP_PATH}/layui/css/layui.css"/>
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <%--<div><a class="navbar-brand" href="#" style="font-size:32px;"></a></div>--%>
        </div>
    </div>
</nav>
<br/>
<br/>
<div class="container">
    <br/>
    <form id="loginForm" action="doLogin" method="post" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i>LOGIN</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" name="loginAccount" id="loginAccount" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" class="form-control" name="password" id="password" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <div class="col-sm-10">
            <img src="${APP_PATH}/validateCodeServlet" id="image" alt="获取验证码"/>
            <strong><a href="#" onclick="validate.changeImg()">换一张</a></strong>
                <%--<div class="checkbox">--%>
                    <label>
                        <input type="checkbox" value="remember-me" id="remember"> 记住我
                    </label>
                    <%--<br/>--%>
                <%--</div>--%>
            </div>
            <input type="text" class="form-control" name="code" id="validateCode"
                   placeholder="请输入验证码"/>

        </div>

        <a class="btn btn-lg btn-success btn-block" onclick="return validate.loginValidate()" > 登录</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/layui/layui.all.js"></script>
<script src="//cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<script type="text/javascript" src="${APP_PATH}/js/validate.js"></script>
<script type="text/javascript">
    <%--异步获取--%>
    validate.checkLogin();
</script>
</body>
</html>