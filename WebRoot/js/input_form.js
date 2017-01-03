//输入框页面的校验
//var obj = $(".window_textcont").eq(0).children("input");
$(function () {
    $('.input_form').bind({
        focus: function () {  //获取焦点
            $("#unlock").attr("disabled", "disabled");
            var span = $(this).next('span');
            if (this.value == this.defaultValue) {
                this.value = "";
                $(this).attr("class", "puton");  //屏蔽按钮输入框
                span.remove();
            }
            else {
                span.remove();
            }
        },
        blur: function () {   //失去焦点
            var span = $(this).next('span');
            if (this.value == "") {
                this.value = this.defaultValue;
                $(this).attr("class", "default");
                span.remove();
            }
            if (this.value != "") {
                if (span.size() <= 0) {
                    $(this).after('<span><img src="../images/icon_pass.png"></span>');
                    span.remove();
                } else {
                    span.html('').html('<img src="../images/icon_pass.png">');
                }
            }
            else {
                if (span.size() <= 0) {
                    $(this).after('<span><img src="../images/icon_error.png"></span>');
                } else {
                    span.html('').html('<img src="../images/icon_error.png">');
                }
            }

            var ck = $(this).attr('ck');
            checking(this.value);
        }
    });
    function checking(value) {
        //alert(value);
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/checkValue",
            data: "value=" + value,
            success: function (data) {
                if (data == 1) {
                    return 0;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {

            }
        });
    }
}); 


