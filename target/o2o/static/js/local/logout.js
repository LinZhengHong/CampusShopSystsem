$(function () {
    //退出登录（LocalAuthController）的Url
    var logoutUrl="/o2o/local/logout";
    $('#log-out').click(function () {
        //清除session
        $.ajax({
            url:"/o2o/local/logout",
            type:"post",
            async:false,
            cache:false,
            dataType:'json',
            success:function (data) {
                if (data.success){
                    var usertype=$('#log-out').attr("usertype");
                    //清除成功后返回登录界面
                    window.location.href="/o2o/local/login?usertype="+usertype;
                    return false;
                }
            },
            error:function (data,error) {
                alert(error);
            }
        });
    });

});