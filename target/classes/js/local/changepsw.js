$(function () {
    //修改平台密码的controller url
    var url='/o2o/local/changelocalpwd';
    //从地址栏的URL里获取usertype
    //uesrtype=1则为customer,其余为shopowner
    var usertype=getQueryString("usertype");
    $('#submit').click(function () {
        //获取账号
        var userName=$('#username').val();
        //获取原密码
        var password=$('#password').val();
        //获取新密码
        var newPassword=$('#newPassword').val();
        //获取确认密码
        var confimPassword=$('#confirmPassword').val();
        if (newPassword!=confimPassword){
            alert("两次密码输入不一致");
            return;
        }
        //添加表单信息
        var formData=new FormData();
        formData.append("userName",userName);
        formData.append("password",password);
        formData.append("newPassword",newPassword);
        formData.append("confirmPassword",confimPassword);
        var verifyCodeActual=$('#j_captcha').val();
        if (!verifyCodeActual){
            alert("请输入验证码");
            return;
        }
        formData.append("verifyCodeActual",verifyCodeActual);
        //将参数formData传到后台
        $.ajax({
            url:url,
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if (data.success){
                    alert("提交成功！")
                    if (usertype==1){
                        //若用户在前端展示系统页面则自动退回到前端展示系统首页
                        window.location.href="/o2o/frontend/index";
                    }else {
                        //若用户在店铺管理页面则自动退回店铺列表中
                        window.location.href="/o2o/shopadmin/shoplist";
                    }
                } else {
                    alert("提交失败"+data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });

    $('#back').click(function () {
        window.location.href="/o2o/shopadmin/shoplist";
    });

});