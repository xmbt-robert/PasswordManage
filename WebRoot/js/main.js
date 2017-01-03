// JavaScript Document
$(function () {
    $("#exit").click(function (){          //退出前对话框确认后，超链接到管理员登陆界面
    	layer.confirm('确定要退出',function(index){
    		window.location.href = "/logout";
    	});
    });
    
    $(".manage").click(function (){  
       	$(this).siblings().removeClass('hover').end().addClass('hover');
       	var url = $(this).attr('url');
        $(".content").load("/"+url);
       });
       
       odd = {"background": "#fff"};//������ʽ
       even = {"background": "#f9f9f9"};//ż����ʽ
       odd_even("#result_table", odd, even);

       function odd_even(id, odd, even) {
           $(id).find("tbody tr").each(function (index, element) {
               if (index % 2 == 1)
                   $(this).css(odd);
               else
                   $(this).css(even);
           });
       }
    loadTabcountDatagrid();
});

function fixWidth(percent) 
{  
    return document.body.clientWidth * percent ; //这里你可以自己做调整  
} 

function loadTabcountDatagrid(){
	
	$("#tabcount_datagrid").datagrid({   
	    url:'/tabCountData',  
	    title:'报表审计统计列表',
	    //width:fixWidth(0.65),
		height: 350,
	    pagination:true,   
	    pageSize:10,   
	    pageNumber:1, 
	    pageList:[5,10,15,20,25,30],
	    rownumbers:true, 
	    singleSelect:true,//是否单选  
	    rownumbers:true//行号
	});
};

//操作状态设置
function operateStatusFormatter(value, row, index){
	if (value == 1){
		return "失败";
	} else if (value == 0){
		return "正常";
	} else {
		return ;
	}
}
