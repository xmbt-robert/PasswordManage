$(function() {
	var txt = document.getElementById("name"), name_tx = document
			.getElementById("name_tx");
	var pwd = document.getElementById("password"), pwd_tx = document
			.getElementById("password_tx");
	pwd_tx.onfocus = function() {
		if (this.value != "请输入密码")
			return;
		this.style.display = "none";
		pwd.style.display = "";
		pwd.value = "";
		pwd.focus();
	};
	pwd.onblur = function() {
		if (this.value != "")
			return;
		this.style.display = "none";
		pwd_tx.style.display = "";
		pwd_tx.value = "请输入密码";
	};

	name_tx.onfocus = function() {
		if (this.value != "请输入用户名")
			return;
		this.style.display = "none";
		txt.style.display = "";
		txt.value = "";
		txt.focus();
	};
	txt.onblur = function() {
		if (this.value != "")
			return;
		this.style.display = "none";
		name_tx.style.display = "";
		name_tx.value = "请输入用户名";
	};
	
	
    //对输入的用户名，密码进行校验
	function loginCheck() {
		var name = $("#name").val();
		var password = $("#password").val();
		var param = {
			name : name,
			password : password,
		};	
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "/loginCheck",
			async : true,
			data : param,
			success : function(data) {
				    if (data == "success") { //用户登录成功
				    	//window.location.href = "manage";
				    	login_countdown();
						return;
				    }else { //登录失败
					 if(name == "" && password == ""){
						 $('.message').html("请先输入用户名或密码！").show();
						 $("#name_tx").hide();
						 $("#name").show();
						 $("#name").focus();
						 return;
					 }else 
						 if(name == ""){
							$('.message').html("用户名不可为空！").show();
							$("#name_tx").hide();
							 $("#name").show();
							 $("#name").focus();
							return;
					 }else 
						if(password == ""){
							$('.message').html("密码不可为空！").show();
							$("#password_tx").hide();
							$("#password").show();
							$("#password").focus();
							return;
					 }else
						if (data == "validate"){ //无权登录
							$('.message').html("对不起，您不是管理员，无权访问！").show();
							$("#name").focus();
							return;
						}else{
							$('.message').html("登入账号或密码错误，请重新输入").show();
							$("#password").val("");
							$("#password").focus();
							return;
					 }
					
				}
			}
		});
	}

	$('.login_button').click(function() {
		loginCheck();
		if($("#rememberLoginInfo").is(':checked') == false){     //如果没有选中记住信息，那么将清除cookies
			$.cookie(COOKIE_NAME, $("#name").val(), { path: '/',expires: -1 });  //删除cookie  
	        $.cookie(COOKIE_PASS, $("#password").val(), { path: '/',expires: -1 });
		}
	});
	
	
	$("#name_tx").hide();
	$("#name").show();
	$("#name").focus();
	$("#password_tx").hide();
	$("#password").show();
	var COOKIE_NAME = 'name';  
	var COOKIE_PASS = 'password';  
	
	 if( $.cookie(COOKIE_NAME) ){  
	    $("#name").val(  $.cookie(COOKIE_NAME) );  
	}  
	 if( $.cookie(COOKIE_PASS)){  
		 $("#rememberLoginInfo").attr("checked","true");
		 $("#password").val($.base64.decode($.cookie(COOKIE_PASS)) ); 
		}
	 
	$("#rememberLoginInfo").click(function(){ 
		    if($(this).attr("checked") == "checked"){ 
		        $.cookie(COOKIE_NAME,  $("#name").val() , { path: '/', expires: 1}); 
		        $.cookie(COOKIE_PASS, $.base64.encode($("#password").val()),{path:'/', expires:1}); 
		        //var date = new Date();  
		        //date.setTime(date.getTime() + (3 * 24 * 60 * 60 * 1000)); //三天后的这个时候过期  
		        //$.cookie(COOKIE_NAME, $("#name").val(), { path: '/', expires: date });  
		    } 
		});
	//alert($("#rememberLoginInfo").attr("checked"));
	
	
	//输入用户名 促发事件 
	$("#name").blur(function(){
		var userNow = $(this).val();
		name = $.cookie(COOKIE_NAME);
			if(userNow == name){       //判断现在输入的用户名  和 当时保存在cookie的用户名是否一致 
				//$("#password").val(password);   //如果一致，则把 第一次 保存在cookie的密码 自动填入 
				$("#password_tx").hide();
				$("#password").show();
				//$("#password").focus();
			}
			else{
				$("#password").val('');
				$("#password").focus();
			}
			return;
		
	});
	
	 $(".login_input").keyup(function(){  //回车登陆事件
			if(event.keyCode == 13 && $("#password").val() == ""){
				$("#password").focus();
			}else if (event.keyCode == 13 && $("#password").val() != "" && $("#name").val() != ""){
				$(".login_button").click();
				event.returnValue = false;
			}
		});
	

	//超链接到密码自助服务页面
	$(".ForgotPass").click(function() {
		window.location.href = "/index";
	});
});

var n = 2; //设定2秒时间    
function login_countdown() {   //登陆倒计时
    if (n > 0) {
    	$(".login_button").val("登 录 中...");
    	$(".login_button").attr("disabled",true);
    	$("#name").attr("readonly",true);
    	$("#password").attr("readonly",true);
    	$("#rememberLoginInfo").attr("disabled",true);
    	$('.message').html("登录成功，本页将在 " + n + " 秒后转入首页！").show();
        n--;
        setTimeout("login_countdown()", 1000);
    } 
    else {
        window.location.href = "/manage";
    }
}



		


