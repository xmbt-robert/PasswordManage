function createCode() {
    var span = $('#checkCode').next('span');
    span.remove();                              //当点击换新图片验证码时，将错误和正确的图标全部移除，并将焦点放在输入框上
    $('#picCode').focus();
    var imgObj = $('#checkCode');
    var src = imgObj.attr("src");     //返回checkCode 属性值
    imgObj.attr("src", chgUrl(src));
    // 为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
    function chgUrl(url) {
        var timestamp = (new Date()).valueOf();
        var newUrl = url.substring(0, 17);
        if ((url.indexOf("?") >= 0)) {
            newUrl = url + "&t=" + timestamp;
        } else {
            newUrl = url + "?t=" + timestamp;
        }
        return newUrl;
    }
}
$(function () {
    $('#picCode').blur(function () {           // 失去焦点
        var span = $('#checkCode').next('span');
        var inputCode = document.getElementById("picCode").value;
        var code;
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/checkCode",
            async: true,       //将AJAX设为同步
            success: function (data) {
                code = data;
                if (inputCode.length <= 0) {    // 输入框值为空时
                    if ($('#picCode').val(''))
                        $('#picCode').val('请输入验证码');
                    return false;
                }
                if (inputCode != code) {              //输入值与图片验证码不相同时（这里区分大小写）
                    if (span.size() <= 0) {
                        $('#checkCode').after('<span><img src="images/icon_error.png"></span>');
                    } else {
                        span.html('').html('<img src="images/icon_error.png">');
                    }
                    return false;
                } else if (inputCode == code) {        //输入值与图片验证码相同时
                    $('#newPass').removeAttr('disabled');        //用户名和密码都正确的时候，将更改密码框打开
                    $('#confirmPass').removeAttr('disabled');
                    if (span.size() <= 0) {
                        $('#checkCode').after('<span><img src="images/icon_pass.png"></span>');
                    } else {
                        span.html('').html('<img src="images/icon_pass.png">');
                    }
                }
            },
        });
        $('#picCode').focus(function () {
            var span = $('#checkCode').next('span');
            if ($('#picCode').val(''))   // 获取焦点时且输入框值为空的时候
                span.remove();
        });
    });
});
