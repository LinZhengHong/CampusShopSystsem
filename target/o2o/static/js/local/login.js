$(function () {
    //登录验证的controller URL
    var loginUrl='/o2o/local/logincheck';
    //从地址栏的URL里获取usertype
    var usertype=getQueryString('usertype');
    //登录次数，累积登录三次失败之后自动弹出验证码要求输入
    var loginCount=0;

    $('#submit').click(function () {
        //获取输入账号
        var username=$('#username').val();
        //获取输入的密码
        var password=$('#password').val();
        //获取验证码信息
        var verifyCodeActual=$('#j_captcha').val();
        //是否需要验证码验证，默认为false,不需要
        var needVerify=false;
        if (loginCount>=3){
            //如果登录三次都失败，就需要开启验证码验证
            if (!verifyCodeActual){
                alert("请输入验证码！");
                return;
            }else{
                needVerify=true;
            }
        }
        //访问后台进行登录验证
        $.ajax({
            url:loginUrl,
            async:false,
            cache:false,
            type:'post',
            dataType:'json',
            data:{
                userName:username,
                password:password,
                verifyCodeActual:verifyCodeActual,
                //是否需要验证码校验
                needVerify:needVerify
            },
            success:function (data) {
                if (data.success){
                    alert("登录成功！");
                    if (usertype==1){
                        //若用户在前端展示系统页面则自动链接到前端展示系统首页
                        window.location.href='/o2o/frontend/index';
                    }else{
                        //若用户是在店家管理系统页面则自动链接到店铺列表页中
                        window.location.href='/o2o/shopadmin/shoplist';
                    }
                }else{
                    alert("登录失败:"+data.errMsg);
                    loginCount++;
                    if (loginCount>=3){
                        //登录失败三次，需要做验证码校验
                        $('#verifyPart').removeAttr("hidden");
                    }
                    $('#captcha_img').click();
                }
            }
        });










    });
});