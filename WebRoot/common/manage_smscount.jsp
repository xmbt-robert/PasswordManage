<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"s://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%--<base href="<%=basePath%>">--%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Pragma" content="-1"/>
	<script src="../js/jquery.min_1.7.2.js"></script>
	<script src="../js/manage_smscount.js"></script>  
	<script src="../js/date/My97DatePicker/WdatePicker_year.js"></script> 
	<title>自助密码服务平台-短信报表统计</title>
</head>
<body>
	<center>
		<div>
			<img name="tip" src="../images/tip.png"/>
			<label>点击柱图可以查看该月份详细信息！</label>
		</div>
		<div style="margin-top: 10px;" id="chartContainer">	

    	</div>
    </center><br/>
    <div class="search_txt oper_but" style="width: 400px;float: right;"><!-- <span style="float: left;margin-left: 800px;">已发送短信历史数据查询</span> -->
    	<span style="float: right;margin-left: 20px;">
            <input type="text" class="date Wdate" id="select_data" readonly="readonly" value="选择年度" onfocus="if(this.value=='选择年度')this.value='';" onblur="if(this.value=='')this.value='选择年度';" style="float: left;color:#A0A0A0;"/>
            <input type="button" class="operate" id="data_query" value="查询" style="float: left;margin-left: 5px;"/>
           <!--  <input type="button" class="cancel" id="data_cancel" value="重置" style="float: left;"/> -->
        </span>
    </div>
    
	
    <br>
    
</body>
</html>