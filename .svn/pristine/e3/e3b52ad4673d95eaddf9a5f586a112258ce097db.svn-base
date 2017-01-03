//输入框页面的校验
var digital = "514";  										// 用户账号被禁止状态代号
$(function () {          //弹出框中的关闭按钮事件
    $(".closedown").click(function () {
        $(".window_bg,.window_cont").hide();
    });
});

$(function () {
    $('.operate').attr('disabled', true);
    $('.operate').hide();
    $('.send').attr('disabled', 'true');   					//页面加载的时候，将发送按钮变为不可编辑状态
    $('#code').attr('disabled', 'true');   					//页面加载的时候，将验证码输入框变为不可编辑状态
    $('.send').css('background', '#e2e2e2');
    function resetPass(objval) {
        var value = $(objval).val();
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/checkValue",
            async: true,
            data: "value=" + value,
            success: function (data) {
                var obj = data;
                var span = $('#code').next('span');
                if(obj[0] != "" && obj[0] != null){
                	if(obj[0] != digital){
                		$('#mobile').val(obj[1]);
                        $('.send').removeAttr('disabled');        				//状态为非禁止状态的时候，将发送按钮变为可编辑状态
                        $('.send').css('background', '#f5f5f5');
                        $('.send').hover('background', '#e2e2e2');
                        $("#message_error1").hide();
                        $('#code').attr('disabled', false);          //状态非禁止状态，并且输入的是存在的打开验证码输入框
                	}else {
                         $('#code').attr('disabled', true);  						//用户状态为禁止状态，将验证码输入框屏蔽
                         $('#message_error').show();                                      //显示提示框
                         $('#code').val('');         //用户状态为禁止状态，将验证码清除
                         $('#message_error1').hide(); 
                         span.remove();
                     }
                }
                else{
                	$('#code').attr('disabled', true);  						//用户状态为非锁定状态，将验证码输入框屏蔽
                    $('#message_error1').show();                                      //显示提示框
                    $('#code').val('');         //用户状态为非锁定状态，将验证码清除
                    span.remove();
            	}
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                /*alert("报错！！！");*/
            }
        });
    };

    $('.input_form').unbind().bind({
        focus: function () {                            /*获取焦点*/
            $('.operate').attr('disabled', true);
            if (this.value == this.defaultValue) {
                this.value = "";
                $(this).attr("class", "puton");
                $('#code').attr('disabled', true);
            }
            else {
                $('#message_error').hide();                  //隐藏提示框
                $('#mobile').val('');    			//获得焦点的时候，移除号码
                $('#message_error1').hide(); 
                $('.send').attr('disabled', true);  	//获得焦点的时候，屏蔽发送按钮
                $('#code').attr('disabled', true);   // 将验证码输入框变为可编辑状态(涉及到当用户发送完没按解锁，关闭页面后，再次打开页面)
                return;
            }
        },
        blur: function () {                            /*失去焦点*/
            $('.operate').attr('disabled', true);
            if (this.value == "") {
                this.value = this.defaultValue;
                $(this).attr("class", "default");
                $('#mobile').val('');    			//用户名为空时，移除号码
                $('.send').attr('disabled', 'true');  //屏蔽发送按钮
                return;
            }
            else {
            	resetPass(this);
            }
        }
    });
});

$(function () {              //点击重置
    function resetPass() {
    	var name = $('#name').val();
        var obj = {name: name};
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/resetPass",
            data: obj,
            async: true,       //将AJAX设为同步
            success: function (data) {
                if (data) {
                    var jsonobj = eval(data);
                    layer.alert('重置成功！请牢记您的新密码：' + jsonobj.newPass + '', 9, function () {
                        layer.closeAll();
                        $(".window_bg,.window_cont").hide();
                    });
                } else {
                	layer.alert('密码重置失败！', 8, function () {
                        layer.closeAll();
                        $(".window_bg,.window_cont").hide();
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.alert('密码重置失败，请重试！',9, function () {
                    layer.closeAll();
                    //$(".window_bg,.window_cont").hide();
                });
            }
        });
    }

    $('.operate').click(function () {
        resetPass();
        $('.operate').attr('disabled', true); //防止切换页面刷新
    });
});

