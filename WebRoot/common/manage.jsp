<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" /> 
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Pragma" content="-1"/>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>    
    <link type="text/css" rel="stylesheet" href="../css/fonts/font-awesome.css"/>
    <link type="text/css" rel="stylesheet" href="../css/fonts/fontface.css"/>
    <link rel="stylesheet" type="text/css" href="../css/datagrid_css/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="../css/datagrid_css/icon.css"/>
    <script src="../js/jquery.min_1.7.2.js"></script>
    <script src="../js/layer.min.js"></script>
    <script src="../js/main.js"></script>
    <script src="../js/jquery.easyui.min.js"></script>
    <script src="../js/easyui-lang-zh_CN.js"></script>   
    <title>自助密码服务平台-验证码查询</title>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>
</head>
<body>
<div class="main">
    <div class="top">
        <div class="top_cont">
            <div class="top_right">
                <a href="#" class="exit" id="exit" style="text-align: right;"><img src="../images/exit.png"/>退出</a>
                <div class="user_photo" ><img src="../images/photo.png"/></div>
                <c:if test="${!empty sessionScope.name}">
                <div class="user_name" >${sessionScope.name}</div>
                <div class="user_mens">${sessionScope.departName}</div>
                </c:if>
            </div>
            <div class="top_left">
                <img src="../images/index_logo.png" class="index_logo"/>

                <div class="top_menu">
                    <a href="javascript:void(0)" class="manage" url="codeSearch"><img src="../images/icon_menu01.png"/>验证码查询</a>
                    <a href="javascript:void(0)" class="manage" url="userSearch"><img src="../images/icon_menu02.png"/>用户查询</a>
                    <a href="javascript:void(0)" class="manage" url="systemSet"><img src="../images/icon_menu03.png"/>系统设置</a>
                    <a href="javascript:void(0)" class="manage " url="SmsReport"><img src="../images/icon_menu05.png"/>短信报表统计</a>
                    <a href="javascript:void(0)" class="manage hover" url="tabCount"><img src="../images/icon_menu04.png"/>操作日志统计</a>
                </div>
            </div>
        </div>
    </div>
    <div class="content">
        <div class="content_manage">
        	<!-- <div style="margin-top:30px"> -->
                <table class="result_table" id="result_table">
                    <tbody>
                    	<tr>
	                        <td>
			                    <table id="tabcount_datagrid" class="easyui-datagrid" >
			                    	<thead>
											<tr>
												<th field="moduleName" width="20%" align="center">模块</th>
												<th field="operator" width="20%" align="center">操作人</th>
												<th field="operateTime" width="25%" align="center">操作时间</th>
												<th field="operateStatus" formatter="operateStatusFormatter" width="10%" align="center">操作状态</th>
												<th field="operateIP" width="20%" align="center">操作IP</th>
											</tr>
									</thead>
								</table>
							</td>
						</tr>
					</tbody>
                </table>
<!--             </div> -->
        </div>
    </div>
    <div class="foot">版权所有 © 上海斐讯数据通信技术有限公司 2008-2013</div>
</div>
<!--弹出窗口 start-->
<div class="window_bg"></div>
<div class="window_cont" id="window_cont"></div>
<!--弹出窗口 end-->
</body>
</html>
