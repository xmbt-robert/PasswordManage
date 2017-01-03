$(function(){
	smsData();
	$('#select_data').focus(WdatePicker);
	$('#select_data').click(WdatePicker);
});


//获取当前系统年份时间
function currentTime(){ 
  var d=new Date(),str=''; 
  str+=d.getFullYear(); 
  //str+=d.getMonth() + 1+'-'; 
  //str+=d.getDate(); 
  //str+=d.getHours()+'时'; 
 //str+=d.getMinutes()+'分'; 
 //str+= d.getSeconds()+'秒'; 
  return str; 
}

$("#data_query").click(function(){
	if(isNaN($("#select_data").val())){
		alert("请先选择要查询的年份");
	}else{
		smsData();
	}
});

//获取所要查询日期的短信发送数量
function smsData(){
	var selectDate = $("#select_data").val();	
	//如果选择日期为空，就默认为当前系统年度
	if(selectDate == "" || selectDate == "选择年度"){
		selectDate = currentTime();
	}
	var param = {startDate: selectDate};
//	$("#chartimg").attr("src", "doBarChart?startDate="+selectDate);
	$.ajax({
        type: "POST",
        dataType: "json",
        url: "/doBarChart",
        async: true,
        data: param,
        success: function (data) {
        	$("#chartContainer").html('');
        	var chartimg = $("<img id='chartimg'/>").attr("src", "/ShowChart?filename="+data.filename).attr("usemap", "#"+data.filename);
        	$("#chartContainer").append(chartimg);
        	var maparea = $(data.maparea);
        	$(maparea).children().each(function(index, element){
        		
        		$(element).attr("target", "_blank");
//        		var url = $(element).attr("href");    
        		
//        		$(element).bind("click", function(e){
//        			var detailImg = $("<img id='detailImg'/>").attr("src", "/doLineChart?selectYearMonth=2015-1");
//        			$("#chartContainer").append(detailImg);
        			
//        			var detailContainer = $("<iframe/>").attr("src", "/detailInfo?selectYearMonth=2015-1");
//        			$("#chartContainer").append(detailContainer);
        			        			
//        		});
//        		$(element).attr("href", "javascript:void(0);");
        	});
        	$("#chartContainer").prepend(maparea);
        	
        }
    });
}

// 获取对象在浏览器内的绝对Left值
function get_pos_x(obj) {
	var x = obj.offsetLeft;
	while (obj.offsetParent) {
		obj = obj.offsetParent;
		x += obj.offsetLeft;
	}
	return x;
}
// 获取对象在浏览器内的绝对Top值
function get_pos_y(obj) {
	var y = obj.offsetTop;
	while (obj.offsetParent) {
		obj = obj.offsetParent;
		y += obj.offsetTop;
	}
	return y;
}
// 显示提示信息
function ShowTip() {
	alert(111);
	var imgobjs = document.getElementsByName("u_img");
	var div_img_objs = document.getElementsByName("u_img_div");
	for ( var i = 0; i < imgobjs.length; i++) {
		// 获取绝对坐标
		var x = get_pos_x(imgobjs[i]);
		var y = get_pos_y(imgobjs[i]);
		// 显示提示信息
		div_img_objs[i].style.display = "block";
		// 设置绝对坐标
		div_img_objs[i].style.left = x;
		div_img_objs[i].style.top = y;
	}
}
