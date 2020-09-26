$(function () {
    //绑定账号的controller url
    var bindUrl='/o2o/local/bindlocalauth';
    //从地址栏获取userType
    //当usertype=1时，则为前端展示系统，后则为店家管理系统
    var usertype=getQueryString("usertype");
    $('#submit').click(function () {
        var username=$('#username').val();
        var password=$('#password').val();
        var verifyCodeActual=$('#j_captcha').val();
        /*var needVerify=false;*/
        if (!verifyCodeActual){
            alert("请输入验证码");
            return;
        }
        //访问后台，绑定账号
        $.ajax({
            url:bindUrl,
            async:false,
            cache:false,
            type:"post",
            dataType:'json',
            data:{
                userName:username,
                password:password,
                verifyCodeActual:verifyCodeActual
            },
            success:function (data) {
                if (data.success){
                    alert("绑定成功");
                    if (usertype==1){
                        //若用户在前端展示系统页面则自动退回到前端展示系统首页
                        window.location.href="/o2o/frontend/index";
                    }else{
                        //若用户在店铺管理页面则自动退回店铺列表中
                        window.location.href="/o2o/shopadmin/shoplist";
                    }
                }else {
                    alert("提交失败"+data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });

});