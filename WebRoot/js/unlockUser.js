//输入框页面的校验
var digital1 = "528";  										// 被锁定可以解锁的账号有两种：512+16 和612+65536+16
var digital2 = "66164";  
var status;
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
    function unlock(objval) {
        var value = $(objval).val();
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/checkValue",
            async: true,
            data: "value=" + value,
            success: function (data) {
                var obj = data;
                if(obj[0] != "" && obj[0] != null){
                	if(obj[0] == digital1 || obj[0] == digital2){
                		status = obj[0];
                        $('#mobile').val(obj[1]);
                        $('.send').removeAttr('disabled');        				//状态为锁定状态的时候，将发送按钮变为可编辑状态
                        $('.send').css('background', '#f5f5f5');
                        $('.send').hover('background', '#e2e2e2');
                        $("#message_error1").hide(); 
                        $('#code').attr('disabled', false);   // 失去焦点且用户名为锁定状态时，将验证码输入框变为可编辑状态
                        return;
                	}
                	 else {
                         $('#code').attr('disabled', true);  						//用户状态为非锁定状态，将验证码输入框屏蔽
                         $("#message_error").show();                                      //显示提示框
                         $('#code').val('');         //用户状态为非锁定状态，将验证码清除
                         $("#message_error1").hide(); 
                         return;
                     }
                }
                else{
                	$('#code').attr('disabled', true);  						//用户状态不存在，将验证码输入框屏蔽
                    $("#message_error1").show();                                      //显示提示框
                    $('#code').val('');         //用户状态为非锁定状态，将验证码清除
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
                $("#message_error").hide();                  //隐藏提示框
                $('#mobile').val('');    			//获得焦点的时候，移除号码
                $("#message_error1").hide();
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
            	unlock(this);
            }
        }
    });
});


$(function () {              //点击解锁
    function unlock() {
        var name = $('#name').val();
        var obj = {name: name,status:status };
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/unLockUser",
            data: obj,
            async: true,       //将AJAX设为同步
            success: function (data) {
                if (data) {
                    layer.alert('密码解锁成功！', 9, function () {
                        layer.closeAll();
                        $(".window_bg,.window_cont").hide();
                    });
                }
                else {
                	layer.alert('密码解锁失败！', 8, function () {
                        layer.closeAll();
                        $(".window_bg,.window_cont").hide();
                    });
                }
            }
        });
    }

    $('.operate').focus(function () {
        unlock();
        $('.operate').attr('disabled', true);
    });
});







