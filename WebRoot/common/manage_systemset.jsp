<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Pragma" content="-1"/>
    <link href="../css/common.css" type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="../css/datagrid_css/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="../css/datagrid_css/icon.css"/>
    <link type="text/css" rel="stylesheet" href="../css/fonts/font-awesome.css"/> 
    <link type="text/css" rel="stylesheet" href="../css/fonts/fontface.css"/>
    <script type="text/javascript" src="../js/jquery.min_1.7.2.js"></script>
    <script src="../js/layer.min.js"></script>
	<script src="../js/manage_systemset.js"></script>
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
    <script type="text/javascript">
     	$(function(){
     		loadAuthCodeAbnormalDatagrid();
     		loadAdminDatagrid();
     	});
     	
        function setTab(name, cursel, n) {
            for (var i=1; i <= n; i++) {
                var menu = document.getElementById(name + i);
                var con = document.getElementById("con_" + name + "_" + i);
                menu.className = i == cursel ? "hover" : "";
                con.style.display = i == cursel ? "block" : "none"; 
                
                if(i==6){
                	$('#authCodeAbnormal-datagrid').datagrid("reload");
                } 
                if(i==7){
                	$('#admin-datagrid').datagrid("reload");
                }
            }
            
        }
        
    /*     $(function() {
            // 其他代码

            // 第一次加载时自动变化大小
            $('#authCodeAbnormal-datagrid').resizeDataGrid(20, 20, 200, 600);

            // 当窗口大小发生变化时，调整DataGrid的大小
            $(window).resize(function() {
                $('#authCodeAbnormal-datagrid').resizeDataGrid(20, 20, 200, 600);
            });
        }); */
        
       
        	/**
        	 * 修改DataGrid对象的默认大小，以适应页面宽度。
        	 * 
        	 * @param heightMargin
        	 *            高度对页内边距的距离。
        	 * @param widthMargin
        	 *            宽度对页内边距的距离。
        	 * @param minHeight
        	 *            最小高度。
        	 * @param minWidth
        	 *            最小宽度。
        	 * 
        	 */
      /*   $.fn.extend({
        	resizeDataGrid : function(heightMargin, widthMargin, minHeight, minWidth) {
        		var height = $(document.tbody).height() - heightMargin;
        		var width = $(document.tbody).width() - widthMargin;

        		height = height < minHeight ? minHeight : height;
        		width = width < minWidth ? minWidth : width;

        		$(this).datagrid('resize', {
        			height : height,
        			width : width
        		});
        	}
        }); */
        
            function fixWidth(percent) 
	    {  
	        return document.body.clientWidth * percent ; //这里你可以自己做调整  
	    } 
        
        function loadAuthCodeAbnormalDatagrid(){   //验证码异常列表
        	$('#authCodeAbnormal-datagrid').datagrid({   
    		    url:'/authCodeAbnormalSearch',
    		    title:'验证码异常列表',
    		    //width: 920,
    		    width:fixWidth(0.65),
				height: 180,
				//fitColumns: false,
				remoteSort:false,
				sortable: true,//允许此列被排序
			    sortName:'sendCount',
			    sortName:'failCount',
			    sortName:'operateTime',
			    sortOrder:'desc',
			    hideColumn:'operate',
    		    pagination:true,   
    		    pageSize:4,   
    		    pageNumber:1, 
    		    pageList:[4,8,12,16,20],
    		    singleSelect:true,//是否单选  
    		    rownumbers:true,//行号
    		});
        }
        
        function loadAdminDatagrid(){      //管理员列表
        	$('#admin-datagrid').datagrid({   
    		    url:'/adminSetData', 
    		    fitcolumns:"true",
    		    toolbar:"#toolbar",
    		    title:'管理员列表',
    		    width:fixWidth(0.65),
    		    //width: 930,
				height: 200,
				remoteSort:false,
			    sortable: true,//允许此列被排序
			    sortName:'createTime',
			    sortOrder:'desc',
    		    pagination:true,   
    		    pageSize:4,   
    		    pageNumber:1, 
    		    pageList:[4,8,12,16,20],
    		    singleSelect:true,//是否单选  
    		    rownumbers:true,//行号
    		});  
        }
    	
	</script>
</head>

<body>
<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px; display:none;"
       closed="true" buttons="#dlg-buttons"> 
       <div class="ftitle"> 
           添加管理员
       </div> 
       <form id="fm" method="post"> 
       <div class="fitem"> 
           <label> 
               工号 
           </label> 
           <input name="staffId" id="staffId" class="easyui-validatebox" required="true" /> 
       </div> 
       <div class="fitem"> 
           <label> 
               用户名
           </label> 
           <input name="user" id="user" class="easyui-validatebox" required="true" /> 
           <!-- <input input name="user" id="user" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Tooltip on top"/> -->
       </div> 
       <div id="dlg-buttons"> 
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveUser()">保存</a> 
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">取消</a> 
    	</div> 
    	<!-- <input type="hidden" name="action" id="hidtype" /> 
       	<input type="hidden" name="ID" id="Nameid" /> -->
       </form> 
</div>
<div class="main">
    <div class="content">
        <div class="content_manage">
            <div class="sys_set">
                <div class="menubox">
                    <ul>
                        <li id="listone1" onclick="setTab('listone',1,7),initAd()" class="hover">AD服务</li>
                        <li id="listone2" onclick="setTab('listone',2,7),initSms()">短信网关</li>
                        <li id="listone3" onclick="setTab('listone',3,7),initEmail()">邮箱设置</li>
                        <li id="listone4" onclick="setTab('listone',4,7)">发送时间设置</li>
                        <li id="listone5" onclick="setTab('listone',5,7)">审计设置</li>
                        <li id="listone6" onclick="setTab('listone',6,7)">验证码异常报表</li>
                        <li id="listone7" onclick="setTab('listone',7,7)">管理员管控</li>
                    </ul>
                </div>
                <div class="contentbox">
                    <div id="con_listone_1" class="hover">
                        <ul class="tab_cont">
                            <li class="tab_txt">IP<br/><input type="text" id="ip" class="ad_ip"/></li>
                            <li class="tab_txt">Port<br/><input type="text" id="port" class="ad_port"/></li>
                            <li class="tab_txt">用户名<br/><input type="text" id="name" class="ad_name"/></li>
                            <li class="tab_txt">密码<br/><input type="password" id="pass" class="ad_pass"/></li>
                        </ul>
                        <div class="oper_but" style="text-align:center; margin:30px 0">
                        <input type="button" class="operate" id="adService" value="修改"/>
                        <input type="button" class="cancel_01" value="重置"/></div>
                    </div>
                    <div id="con_listone_2" style="display:none">
                        <ul class="tab_cont">
                            <li class="tab_txt">IP<br/><input type="text"  id="ip" class="sms_ip"/></li>
                            <li class="tab_txt">Port<br/><input type="text" id="port" class="sms_port"/></li>
                            <li class="tab_txt">用户名<br/><input type="text" id="name" class="sms_name"/></li>
                            <li class="tab_txt">密码<br/><input type="password" id="pass" class="sms_pass"/></li>
                        </ul>
                        <div class="oper_but" style="text-align:center; margin:30px 0">
                        <input type="button" class="operate" id="smsGateway" value="修改"/>
                        <input type="button" class="cancel_02" value="重置"/></div>
                    </div>
                    <div id="con_listone_3" style="display:none">
                        <ul class="tab_cont">
                            <li class="tab_txt">IP<br/><input type="text" id="ip" class="email_ip"/></li>
                            <li class="tab_txt">Port<br/><input type="text" id="port" class="email_port"/></li>
                            <li class="tab_txt">用户名<br/><input type="text" id="name" class="email_name"/></li>
                            <li class="tab_txt">密码<br/><input type="password" id="pass" class="email_pass"/></li>
                        </ul>
                        <div class="oper_but" style="text-align:center; margin:30px 0">
                        <input type="button" class="operate" id="emailSystem" value="修改"/>
                        <input type="button" class="cancel_03" value="重置"/></div>
                    </div>
                    <div id="con_listone_4" style="display:none">
                        <table class="set_table">
                            <tr>
                                <td width="50%" style="border-right:1px solid #dcdcdc">
                                    <div class="set_cont">
                                        <span class="set_txt"><img
                                                src="../images/icon_phone.png"/><label>短信发送间隔时间<br/><input type="text" id="message_time" data-toggle="tooltip" data-placement="top" title="请输入一个正整数"/>&nbsp;&nbsp;分</label></span>

                                        <div class="oper_but" style="text-align:center; margin:30px 0">
                                        <input type="button" class="operate" id="message_operate" value="修改"/>
                                        <input type="button" class="cancel_04_01" value="重置"/></div>
                                    </div>
                                </td>
                                <td width="50%">
                                    <div class="set_cont">
                                        <span class="set_txt"><img
                                                src="../images/icon_email.png"/><label>邮件发送间隔时间<br/><input type="text"  id="email_time" data-toggle="tooltip" data-placement="top" title="请输入一个正整数"/>&nbsp;&nbsp;分</label></span>

                                        <div class="oper_but" style="text-align:center; margin:30px 0">
                                        		<input type="button" class="operate" id="email_operate" value="修改"/>
                                                <input type="button" class="cancel_04_02" value="重置"/></div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div id="con_listone_5" style="display:none">
                        <ul class="tab_cont">
                            <li class="tab_txt">条件设置<br/>
                            	<div class="select_box">
                                <select name="choose" id="choose" style="width: 200px;height: 32px">
                                    <option value="0" selected="selected">--请选择--</option>
                                    <option value="1">IP地址和用户</option>
                                    <option value="2">IP地址</option>
                                    <option value="3">用户</option>
                                </select>
                                </div>
                            </li>
                            <li class="tab_txt">失败次数<br/>
                                <div class="select_box"><input type="text" class="failCount" id="failCount" data-toggle="tooltip" data-placement="top" title="请输入一个正整数"/></div>
                            </li>
                            <li class="tab_txt">锁定时间（分钟）<br/><input type="text" class="lockTime" id="lockTime" data-toggle="tooltip" data-placement="top" title="请输入一个正整数"/></li>
                        </ul>
                        <div class="oper_but" style="text-align:center; margin:30px 0">
                        <input type="button" class="operate" id="audit_operate" value="修改"/>
                        <input type="button" class="cancel_05" value="重置"/></div>
                    </div>
                    <div id="con_listone_6" style="display:none;">
                    <div id="search">
                      <input  id="searchInput" value="请输入要查询的条件" onfocus="if(this.value=='请输入要查询的条件')this.value='';" onblur="if(this.value=='')this.value='请输入要查询的条件';" style="width:200px;color:#A0A0A0;"></input>
                      <span><a href="javascipt:void(0)" id="search"><img src="../images/searchbox_button.png" id="searchContent" height="20px" /></a></span>
                    </div>
                    <table class="result_table" id="result_table_6">
	                    <!-- <thead>
	                    <tr>
	                        <th colspan="6"><span class="icon_span"><i class="icon-list"></i></span>验证码异常列表</th>
	                    </tr>
	                    </thead> -->
	                    <tbody>
	                      <tr>
	                        <td>
	                          <table id="authCodeAbnormal-datagrid" class="easyui-datagrid" >
								<thead>
									<tr>
										<th field="IP" width="20%" align="center">IP</th>
										<th field="userName" width="20%" align="center">用户名</th>
										<th field="sendCount" sortable="true" width="10%" align="center">发送次数</th>
										<th field="failCount" sortable="true" width="10%" align="center">失败次数</th>
										<th field="operateTime" sortable="true" width="15%" align="center">操作时间</th>
										<th field="lockStatus" formatter="lockStatusFormatter" width="10%" align="center">状态</th>
										<!-- <th field="operate" formatter="formatOper" width="5%" align="center">操作</th> -->
									</tr>
								</thead>
						      </table>
	                        </td>
	                      </tr>
						</tbody>
                      </table>
                    </div>
                    <div id="con_listone_7" style="display:none;">
                     <div id="toolbar">
				        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="newUser()" style="color: red;">添加管理用户</a>
				        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()" style="color: red;">删除管理用户</a>
    				</div>
                    <table class="result_table" id="result_table_7">
	                    <tbody>
	                      <tr>
	                        <td>
	                          <table id="admin-datagrid" class="easyui-datagrid" >
								<thead>
									<tr>
										<th field="staffId" width="25%" align="center">工号</th>
										<th field="user" width="25%" align="center">用户名</th>
										<th field="createTime" sortable="true" width="40%" align="center">创建时间</th>
									</tr>
								</thead>
						      </table>
	                        </td>
	                      </tr>
						</tbody>
                      </table>
                    </div>
                </div>
            </div>
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
