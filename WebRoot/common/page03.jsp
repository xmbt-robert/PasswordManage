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
    <link rel="../stylesheet" type="text/css" href="css/code.css"/>
    <script src="../js/jquery.min_1.7.2.js"></script>
    <script src="../js/modifyPass.js"></script>
    <script src="../js/picCode.js"></script>
    <script src="../js/layer.min.js"></script>
    <title>更改密码</title>
</head>

<body>
<p class="window_title"><a class="close closedown" href="javascript:void(0)">×</a><img
        src="../images/window_icon03.png"/>更改密码</p>
<div class="window_main">
    <!-- <label class="window_tip window_right">修改密码成功！新密码是：feixun*123</label> -->
    <label id="message_error" class="window_tip window_error" style="display:none">该账号为非正常状态，无法更改密码!</label>
    <label id="message_error01" class="window_tip window_error" style="display:none">该账号与密码不匹配!</label>
    <label id="message_error02" class="window_tip window_error" style="display:none">请确保新密码和确认密码输入一致!</label>
    <label id="message_error03" class="window_tip window_error" style="display:none">新修改密码与旧密码不可一致!</label>
    <label id="message_error04" class="window_tip window_error" style="display:none">新密码须符合：密码长度介于7到18位,<br />其中至少包含有大写和小写字母，数字，特殊字符四种中的任意三种组合!</label>
    <label id="message_error5" class="window_tip window_error" style="display:none">该账号不存在!</label>
    <div class="main_cont" style="width:546px">
        <div class="main_cont" style="float:left; width:272px;">
            <p class="window_texttitle">域账号</p>

            <p class="window_textcont"><input type="text" type="text" id="name" value="输入用户名" class="input_form"/></p>

            <p class="window_texttitle">旧密码</p>

            <p class="window_textcont"><input type="password" id="oldPass" disabled="disabled"/></p>

            <p class="window_texttitle">图片验证码</p>

            <p class="window_textcont"><input type="text" style="width:120px" id="picCode" disabled="disabled"/><label
                    class="code"><img src="rendCode" id="checkCode"/></label></p>

            <p align="left"><a href="javascript:void(0)" id="url" onclick="createCode()" style="color: red;">看不清楚,点击换张试试！</a></p> 
        </div>
        <div class="main_cont" style="float:left; margin-left:20px">
            <p class="window_texttitle">新密码</p>

            <p class="window_textcont"><input type="password" disabled="disabled" id="newPass"/></p>

            <p class="window_texttitle">确认密码</p>

            <p class="window_textcont"><input type="password" disabled="disabled" id="confirmPass" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="输入完请先点击空白处"/></p>
        </div>
    </div>
</div>
<div class="window_foot">
<input type="button" class="cancel closedown" value="取消"/>
<input type="button" class="operate" value="修改" disabled="disabled"/>
</div>
</body>
</html>
