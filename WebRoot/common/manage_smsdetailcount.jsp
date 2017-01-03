<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%--<base href="<%=basePath%>">
	--%><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Pragma" content="-1"/>
	<script src="../js/jquery.min_1.7.2.js"></script>
	<script src="../js/manage_smscount.js"></script>  
	<title>自助密码服务平台-短信报表统计</title>
</head>
<body>
	<center>
		<div style="margin-top: 10px;">
			<img id="chartimg" src="doLineChart?selectYearMonth=${param.selectYearMonth }">
    	</div>
    </center>
</body>
</html>