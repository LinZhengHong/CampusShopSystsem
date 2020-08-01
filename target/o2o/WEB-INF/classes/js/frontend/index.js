$(function () {
    //定义访问后台，获取头条列表以及一级类列表的URL
    var url='/o2o/frontend/listmainpageinfo';
    //访问后台，获取头条列表以及一级类别列表
    $.getJSON(url,function (data) {
        if (data.success){
            //获取后台传递过来的头条列表
            var headLineList=data.headLineList;
            var lunboHtml='';
            //遍历头条列表，并拼接出轮播图组
            headLineList.map(function (item,index) {
                lunboHtml+=''+'<div class="carousel-item active">' +
                    '<img src="'+item.lineImg+'" class="d-block w-100" alt="'+item.lineName+'">' +
                    '</div>'
            });
            //将轮播图组赋值给前端HTML控件
            $('#wrapper').html(lunboHtml);

            //获取后台传递过来的大类列表
            var shopCategoryList=data.shopCategoryList;
            var categoryHtml='';
            //遍历大类列表,拼接出一行的类别
            //这里有一个问题，就是图片路径问题，需要到Tomcat的server.xml配置路径，也可以在idea中设置虚拟路径
            shopCategoryList.map(function (item,index) {
                categoryHtml += ''+'<div id="card" class="card mt-3 mr-3" style="width: 18rem;" date-category='+item.shopCategoryId+'>'
                +'<img src="'+item.shopCategoryImg+'" class="card-img-top" alt="...">'
                 +'<div class="card-body">'
                    +'<h5 class="card-title">'+item.shopCategoryName+'</h5>'
                    +'<p class="card-text">'+item.shopCategoryDesc+'</p>'
                    +'<a href="#" class="btn btn-primary">按钮</a>'
                    +'</div> '
                    + '</div>';
            });
            $('.row').html(categoryHtml);
        }
    });

    //若点击侧栏

    $('row').on('click','#card',function (e) {
        var shopCategoryId=e.currentTarget.dataset.category;
        var newUrl='/o2o/fontend/shoplist?parentId='+shopCategoryId;
        window.location.href=newUrl;
    });

});