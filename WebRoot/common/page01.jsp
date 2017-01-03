<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" /> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Pragma" content="-1"/>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>
    <link href="../css/input_form.css" type="text/css" rel="stylesheet"/>
    <script src="../js/jquery.min_1.7.2.js"></script>
    <script src="../js/input_form.js"></script>
    <script src="../js/unlockUser.js"></script>
    <script src="../js/messageCode.js"></script>
    <script src="../js/layer.min.js"></script>
    <title>解锁账号</title>
</head>
<body>
<%@ include file="sendCode.jsp" %>
<!-- 导入发送验证码倒计时文件 -->
<p class="window_title"><a class="close closedown" href="javascript:void(0)">×</a><img
        src="../images/window_icon01.png"/>解锁账号</p>

<div class="window_main">
    <label id="message_error" class="window_tip window_error" style="display:none">该账号为非锁定状态，无法解锁!</label>
    <label id="message_error1" class="window_tip window_error" style="display:none">该账号不存在!</label>
    <div class="main_cont">
        <!-- <p class="window_texttitle">域账号</p> -->
        <p class="window_textcont"><input type="text" id="name" value="输入用户名" class="input_form"/></p>

        <p class="window_texttitle">手机验证</p>

        <p class="window_textcont"><input type="text" disabled="disabled" id="mobile" style="width:145px"/><input
                type="button" class="send" value="发送验证码"/></p>

        <p class="window_texttitle">短信验证码</p>

        <p class="window_textcont"><input type="text" id="code" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="输入完请先点击空白处" style="float: left;"/></p>
    </div>
</div>
<div class="window_foot"><input type="button" class="cancel closedown" value="取消"/>
<input type="button" class="operate" value="解锁" id="unlock"/></div>
</body>
</html>

