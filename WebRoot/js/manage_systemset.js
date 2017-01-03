$(function(){
	initAd();
	//initSms();
	//initEmail();
	var reg = /^[1-9]*[1-9][0-9]*$/;  //正整数
	$(".cancel_01").click(function(){
		$("div[id='con_listone_1']").find("li[class='tab_txt']").find("input").val("");
	});
	$(".cancel_02").click(function(){
		$("div[id='con_listone_2']").find("li[class='tab_txt']").find("input").val("");
	});
	$(".cancel_03").click(function(){
		$("div[id='con_listone_3']").find("li[class='tab_txt']").find("input").val("");
	});
	$(".cancel_04_01").click(function(){
		$("div[id='con_listone_4']").find("span[class='set_txt']").find("input[id='message_time']").val("");
	});
	$(".cancel_04_02").click(function(){
		$("div[id='con_listone_4']").find("span[class='set_txt']").find("input[id='email_time']").val("");
	});
	$(".cancel_05").click(function(){
		$("div[id='con_listone_5']").find("li[class='tab_txt']").find("input").val("");
		$("#choose").get(0).selectedIndex=0;
	});
	
	/**
	 * ldap相关设置
	 */
	function initAd(){
			$.ajax({
				type: "POST",
				dataType: "json",
				url: "/initAd",
				async: true,
				success: function(data){
					$(".ad_ip").val(data.IP);
					$(".ad_port").val(data.port);
					$(".ad_name").val(data.userName);
					$(".ad_pass").val(data.userPassWd);
				}
			});
	}
	$("#adService").click(function(){   //AD服务设置
		var inputArray = $(":input", "#con_listone_1");
		for(var i=0; i<inputArray.length; i++){
			if($.trim($(inputArray[i]).val()) == ""){
			layer.alert('请先填完所有内容，再修改！', 0, function () {
				layer.closeAll();
				$(inputArray[i]).focus();
            });
			return;
			}
		}
		adServiceSet();
	});
	function adServiceSet(){
		var ip = $("#ip", "#con_listone_1").val();
		var port = $("#port", "#con_listone_1").val();
		var user = $("#name", "#con_listone_1").val();
		var pass = $("#pass", "#con_listone_1").val();
		var param = {ip:ip, port:port, user:user, pass:pass};
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/adServiceSet",
			async: true,
			data: param,
			success: function(data){
				layer.alert('修改成功！', 9, function () {
					layer.closeAll();
					//$("input[type='text']").val("");   
					//$("input[type='password']").val("");
	            });
			}
		});
	}
	
	/**
	 * 短信相关设置
	 */
	$("#smsGateway").click(function(){   //短信网关设置
		var inputArray = $(":input", "#con_listone_2");
		for(var i=0; i<inputArray.length; i++){
			if($.trim($(inputArray[i]).val()) == ""){
				layer.alert('请先填完所有内容，再修改！', 0, function () {
					layer.closeAll();
					$(inputArray[i]).focus();
	            });
				return;
				}
		}
		smsGatewaySet();
	});
	
	function smsGatewaySet(){
		var ip = $("#ip", "#con_listone_2").val();
		var port = $("#port", "#con_listone_2").val();
		var user = $("#name", "#con_listone_2").val();
		var pass = $("#pass", "#con_listone_2").val();
		var param = {ip:ip, port:port, user:user, pass:pass};
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/smsGatewaySet",
			async: true,
			data: param,
			success: function(data){
				layer.alert('修改成功！', 9, function () {
					layer.closeAll();
					//$("input[type='text']").val("");   
					//$("input[type='password']").val("");
	            });
			}
		});
	}
	
	/**
	 * 邮箱相关设置
	 */
	$("#emailSystem").click(function(){   //邮箱设置
		var inputArray = $(":input", "#con_listone_3");
		for(var i=0; i<inputArray.length; i++){
			if($.trim($(inputArray[i]).val()) == ""){
				layer.alert('请先填完所有内容，再修改！', 0, function () {
					layer.closeAll();
					$(inputArray[i]).focus();
	            });
				return;
				}
		}
		emailSystemSet();
	});
	
	function emailSystemSet(){
		var ip = $("#ip", "#con_listone_3").val();
		var port = $("#port", "#con_listone_3").val();
		var user = $("#name", "#con_listone_3").val();
		var pass = $("#pass", "#con_listone_3").val();
		var param = {ip:ip, port:port, user:user, pass:pass};
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/emailSystemSet",
			async: true,
			data: param,
			success: function(data){
				layer.alert('修改成功！', 9, function () {
					layer.closeAll();
					//$("input[type='text']").val("");   
					//$("input[type='password']").val("");
	            });
			}
		});
	}
	
	
	$("#message_operate").click(function(){    //修改短信发送间隔时间
		var time = $("#message_time").val();
		//var reg = /^(0|[1-9]*)$/;
			if(!reg.test(time) || time == null || time == ""){
				layer.alert('请输入一个正整数！', 0, function () {
					layer.closeAll();
					$("#message_time").val("");
					$("#message_time").focus();
	            });
				return;
			}else{
				messageTimeSet(this);
			}
			
	});
	function messageTimeSet(){
		var time = $("#message_time").val();
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/messageTimeSet",
			async: true,
			data: "message_time=" + time,
			success: function(data){
				layer.alert('修改成功！', 9, function () {
					layer.closeAll();
					$("#message_time").val("");
	            });
			}
		});
	}
	
	$("#email_operate").click(function(){    //修改邮件发送间隔时间
		var time = $("#email_time").val();
		//var reg = /^(0|[1-9]*)$/;
			if(!reg.test(time) || time == null || time == ""){
				layer.alert('请输入一个正整数！', 0, function () {
					layer.closeAll();
					$("#email_time").val("");
					$("#email_time").focus();
	            });
				return;
			}else{
				emailTimeSet(this);
			}
	});
	function emailTimeSet(){
		var time = $("#email_time").val();
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/emailTimeSet",
			async: true,
			data: "email_time=" + time,
			success: function(data){
				layer.alert('修改成功！', 9, function () {
					layer.closeAll();
					$("#email_time").val("");
	            });
			}
		});
	}
	
	
	$("#audit_operate").click(function(){    //审计设置
		var choose = $("#choose").val();
		var failCount = $(".failCount").val();
		var lockTime = $(".lockTime").val();
		//var reg = /^([1-9]*)$/;
		
		if(choose == 0){
			layer.alert('请选择某一条件进行设置！', 0, function () {
				layer.closeAll();
            });
			return;
		}else 
			if(!reg.test(failCount) || failCount == null || failCount == ""){
				layer.alert('失败次数必须为一个正整数！', 0, function () {
					layer.closeAll();
					$(".failCount").val("");
					$(".failCount").focus();
	            });
				return;
			}else 
				if(!reg.test(lockTime) || lockTime == null || lockTime == ""){
					layer.alert('锁定时间必须为一个正整数！', 0, function () {
						layer.closeAll();
						$(".lockTime").val("");
						$(".lockTime").focus();
		            });
					return;
			}
			else {
				auditSet(this);
			}
	});
	function auditSet(){
		var type = $("#choose").val();
		var lockTime = $(".lockTime").val();
		var failCount = $(".failCount").val();
		var param = {choose:type,lockTime:lockTime,failCount:failCount};
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/auditSet",
			async: true,
			data: param,
			success: function(param){
				layer.alert('修改成功！', 9, function () {
					layer.closeAll();
					$("#choose").val(0);
					$(".failCount").val("");
					$(".lockTime").val("");
	            });
			}
		});
	}
	
	//验证码查询
	$("#searchContent").click(function(){
		var content = $("#searchInput").val();
		$('#authCodeAbnormal-datagrid').datagrid('load',{ 
			content : content
		});
		$('#authCodeAbnormal-datagrid').datagrid('reload');
	});
});

function initAd(){
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/initAd",
		async: true,
		success: function(data){
			$(".ad_ip").val(data.IP);
			$(".ad_port").val(data.port);
			$(".ad_name").val(data.userName);
			$(".ad_pass").val(data.userPassWd);
		}
	});
}

function initSms(){
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/initSms",
		async: true,
		success: function(data){
			$(".sms_ip").val(data.IP);
			$(".sms_port").val(data.port);
			$(".sms_name").val(data.userName);
			$(".sms_pass").val(data.userPassWd);
		}
	});
}

function initEmail(){
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "/initEmail",
		async: true,
		success: function(data){
			$(".email_ip").val(data.IP);
			$(".email_port").val(data.port);
			$(".email_name").val(data.userName);
			$(".email_pass").val(data.userPassWd);
		}
	});
}

/**
 * 验证码异常修改
 */
/*function formatOper(val,row,index){  
    return '<a href="#" onclick="editUser('+index+')">修改</a>';  
}

function editUser(index){  
    $('#authCodeAbnormal-datagrid').datagrid('selectRow',index);
    var row = $('#authCodeAbnormal-datagrid').datagrid('getSelected');  
    if (row){  
        $('#dlg').dialog('open').dialog('setTitle','修改短信次数');  
        $('#fm').form('load',row);  
        url = '/systemSetData?id='+row.id;  
    }  
}*/

/**
 * 管理员操作
 */
var url;
var type;
function newUser() {   //添加用户弹出窗
	$("#dlg").css("display", "");
    $("#dlg").dialog("open").dialog('setTitle', '添加'); ;
    $("#fm").form("clear");
    //document.getElementById("hidtype").value="submit";
}

function saveUser() {  //保存用户
	var valid = $("#fm").form("validate");
	if(valid){
		var staffId = $("#staffId").val();
        var user = $("#user").val();
        var param = {staffId: staffId, user: user};
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "/adminAddData",
			async: true,
			data: param,
			success: function(data){
	            if (data == "success") {
	                $.messager.alert("提示信息", "保存成功");
	                $("#dlg").dialog("close");
	                $("#admin-datagrid").datagrid("reload");
	            }
	            else {
	                $.messager.alert("提示信息", "保存失败");
	            }
			}
		});
	}
}	

function destroyUser() {   //删除用户
    var row = $('#admin-datagrid').datagrid('getSelected');
    if (row && row.id) {
        var param = {id: row.id};
            	$.ajax({
        			type: "POST",
        			dataType: "json",
        			url: "/adminDeleteData",
        			async: true,
        			data: param,
        			success: function(data){
        	            if (data == "success") {
        	                //$.messager.alert("提示信息", "删除成功");
        	                $.messager.confirm("提示信息", "确定要删除此选中数据？",function(r){
        	                if(r){
        	                $("#dlg").dialog("close");
        	                $("#admin-datagrid").datagrid("reload");
        	                }
        	                else{
        	                	$("#dlg").dialog("close");
        	                }
        	               });
        	            }
        	            else {
        	                $.messager.alert("提示信息", "删除失败");
        	            }
        			}
        		});
    }
    else{
    	$.messager.alert("提示信息", "请先选中要删除的数据");
        $("#dlg").dialog("close");
    }
}


function lockStatusFormatter(value, row, index){
	if (value == 1){
		return "锁定";
	} else if (value == 0){
		return "正常";
	} else {
		return ;
	}
}

