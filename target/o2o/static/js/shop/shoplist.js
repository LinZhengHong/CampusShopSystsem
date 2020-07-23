$(function () {
    getlist();
    function getlist(e) {
        $.ajax({
            url:"/o2o/shopadmin/getshoplist",
            type:"get",
            dataType:"json",
            success:function (data) {
                if (data.success){
                    handleList(data.shopList);
                    handleUser(data.user);
                }
            }
        });
    }
    function handleUser(data) {
        $('#username').text(data.name);
    }
    function handleList(data) {
        var html='';
        data.map(function (item,index) {
            html+='<tr><td>'+item.shopName+'</td>'
                +'<td>'+shopStatus(item.enableStatus)+'</td>'
                +'<td>'+goShop(item.enableStatus,item.shopId)+'</td></tr>'
        });
        $('#tb').html(html);
    }

    function shopStatus(status) {
        if (status==0){
            return '审核中';
        }else if (status==-1){
            return '店铺非法';
        }else if (status==1){
            return '审核通过';
        }
    }

    function goShop(status,id) {
        if (status==1){
            return '<a href="/o2o/shopadmin/shopmanagement?shopId='+id+'">进入</a>';
        }else{
            return '';
        }
    }
});