$(function(){
	$(".cancel").click(function(){          //重置按钮
		$("input[type='text']").val("");   //清空所有type为text格式的文本框
		$("#choose").children("option").removeAttr("selected");    //重置默认的下拉值
		$("#choose").children("option").eq(0).attr("selected", "selected");
		$("ul[class='select_option']").children("li").removeClass("hover");
		$("ul[class='select_option']").children("li").eq(0).addClass("hover");
		$("ul[class='select_option']").children("li").removeClass("selected");
		$("ul[class='select_option']").children("li").eq(0).addClass("selected");
		$("div[class='select_showbox']").html($("ul[class='select_option']").children("li").eq(0).html());
	});
	$(".operate").click(function(){    //查询按钮
		var a = $(".start").val();
		var b = $(".end").val();
		var valid = compare(a,b);
		var userName = $("#name", "#codeSearch").val();
		var startDate = $("#startDate", "#codeSearch").val();
		var endDate = $("#endDate", "#codeSearch").val();
		var statusChoose = $("#choose", "#codeSearch").val();
		
		if(valid){
			$('#codesearch-datagrid').datagrid('load',{ 
				userName : userName,
				startDate : startDate,
				endDate : endDate,
				expiresStatus : statusChoose
			});
			$('#codesearch-datagrid').datagrid('reload');
		}
	}); 
});
function expiresStatusFormatter(value, row, index){
	if (value == 1){
		return "正常";
	} else if (value == 2){
		return "过期";
	} else if (value == 3){
		return "使用";
	} else {
		return ;
	}
}
/**
 * 日期比较
 */
function compare(a, b) {
	var arr = a.split("-");
	var starttime = new Date(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
	var starttimes = starttime.getTime();
	var arrs = b.split("-");
	var endtime = new Date(arrs[0], arrs[1], arrs[2], arrs[3], arrs[4], arrs[5]);
	var endtimes = endtime.getTime();
	if (starttimes > endtimes) {
		layer.alert('开始时间不能大于结束时间！', 0, function () {
			layer.closeAll();
        });
		return false;
	} else
		return true;
}

/*//datagrid初始化  
$('#code').datagrid({  
    title:'验证码信息列表',  
    //iconCls:'icon-edit',//图标  
    //width: 700,  
    height: 'auto',  
    nowrap: false,  
    striped: true,  
    border: true,  
    collapsible:false,//是否可折叠的  
    fit: true,//自动大小  
    url:'/codeSearch',  
    //sortName: 'code',  
    //sortOrder: 'desc',  
    remoteSort:false,   
    idField:'fldId',  
    singleSelect:false,//是否单选  
    pagination:true,//分页控件  
    rownumbers:true,//行号  
    frozenColumns:[[  
        {field:'ck',checkbox:true}  
    ]],    
});  
//设置分页控件  
var p = $('#code').datagrid('getPager');  
$(p).pagination({  
    pageSize: 10,//每页显示的记录条数，默认为10  
    pageList: [5,10,15],//可以设置每页记录条数的列表  
    beforePageText: '第',//页数文本框前显示的汉字  
    afterPageText: '页    共 {pages} 页',  
    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
    onBeforeRefresh:function(){ 
        $(this).pagination('loading'); 
        alert('before refresh'); 
        $(this).pagination('loaded'); 
    } 
});  */