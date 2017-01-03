$(function(){
	$(".cancel").click(function(){
		$("input[type='text']").val("");   //清空所有type为text格式的文本框
		 $("select[name='choose']  option[value='0'] ").attr("selected",true); //恢复所有name=choose的选择的默认值
	});
	
	$(".operate").click(function(){	
		var userName = $("#userName").val();
		var realName = $("#realName").val();
		$('#userSearch-datagrid').datagrid('load',{ 
			realName : realName,
			userName : userName
		});
	});
});

var url;
var type;
function editUser() {   //编辑用户弹出窗
	var row = $('#userSearch-datagrid').datagrid('getSelected');
	if(!row){
		$.messager.alert("提示信息", "请先选中要编辑的数据");
        $("#dlg").dialog("close");
	}else{
		$("#dlg").css("display", "");
	    $("#dlg").dialog("open").dialog('setTitle', '编辑'); ;
	    $("#fm").form("clear");
	    $("#username").val(row.SamAccountName);
	    $("#realUsername").val(row.cn);
	   	$("#mobile").val(row.mobile);
	}
}

function saveUser() {  //保存用户
	var valid = $("#fm").form("validate");
	var row = $('#userSearch-datagrid').datagrid('getSelected');
    if (valid) {
    	 var username = $("#username").val();
    	 var mobile = $("#mobile").val();
    	 var param = {username: username, mobile: mobile};
            	
        $.ajax({
			type: "POST",
			dataType: "json",
			url: "/editUser",
			async: true,
			data: param,
			success: function(data){
	            if (data == "success") {
	                $.messager.alert("提示信息", "保存成功");
	                $("#dlg").dialog("close");
	                $("#userSearch-datagrid").datagrid("reload");
	            }
	            else {
	                $.messager.alert("提示信息", "保存失败");
	            }
			}
		});
	}
}


function userStatusFormatter(value, row, index){
	if (value == 512 || value == 8389120 || value == 544){
		return "正常";
	} else if (value == 514){
		return "禁止";
	} else if (value == 528 || value == 66164){
		return "锁定";
	} else {
		return "未知";
	}
}