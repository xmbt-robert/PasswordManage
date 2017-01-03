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
    <script src="../js/jquery.min_1.7.2.js"></script>
    <script src="../js/index.js"></script>
    <script src="../js/layer.min.js"></script>
    <title>自助密码服务平台</title>
</head>
<body>
<div class="main">
    <div class="top">
        <div class="top_cont">
            <div class="top_right">
                <!-- <a href="#" class="exit"><img src="images/exit.png" />退出</a> -->
                <img src="../images/manager.png" /></br><a href="javascript:void(0)" class="manager" style="font-size: 15px">管理员登陆</a>
            </div>
            <div class="top_left"><img src="../images/index_logo.png" class="index_logo"/>

                <div class="tip"><img src="../images/tip.png"/>

                    <p>小贴士：如果你的手机有变更，请通过HR修改手机号码，确保手机号码是最新的。</p></div>
            </div>
        </div>
    </div>
    <div class="content">
        <div class="content_menu">
            <table>
                <tr>
                    <td><a href="#" class="icon_01 small_window" id="icon_01"><label>解锁账号</label></a></td>
                    <td><a href="#" class="icon_02 small_window" id="icon_02"><label>忘记密码</label></a></td>
                    <td><a href="#" class="icon_03 small_window" id="icon_03"><label>更改密码</label></a></td>
                    <!-- <td><a href="#" class="icon_04 large_window"><label>更新资料</label></a></td> -->
                </tr>
            </table>
        </div>
    </div>
    <div class="foot">版权所有 © 上海斐讯数据通信技术有限公司 2008-201８</div>
</div>
<!--弹出窗口 start-->
<div class="window_bg"></div>
<!-- <div class="window_cont" id="window_cont"></div> -->
<!--弹出窗口 end-->
<!-- 改用iframe -->
<div class="window_cont" id="window_cont">
</div>
</body>
</html>
