$(function () {
    var loading=false;
    var maxItems=999;
    var pageSize=10;
    var listUrl='/o2o/frontend/listshops';
    var searchDirUrl='/o2o/frontend/listshopspageinfo';
    var pageNum=1;
    var parentId=getQueryString('parentId');
    var areaId='';
    var shopCategoryId='';
    var shopName='';

    function getSearchDivData() {
        var url=searchDirUrl+'?'+'parentId='+parentId;
        $.getJSON(url,function (data) {
            if (data.success){
                var shopCategoryList=data.shopCategoryList;
                var html='';
                html+='<a href="#" class="button mr-5" data-category-id="">全部类别</a>';
                shopCategoryList.map(function (item,index) {
                    html+='<a href="#" class="button mr-5" data-cateogory-id='
                        +item.shopCategoryId
                        +'>'
                        +item.shopCategoryName
                        +'</a>';
                });
                $('#shopList-search-div').html(html);
                var selectOptions='<option selected>全部街道</option>';
                var areaList=data.areaList;
                areaList.map(function (item,index) {
                    selectOptions+='<option value="'
                        +item.areaId
                        +'">'
                        +item.areaName
                        +'</option>'
                });
                $('#area-search').html(selectOptions);
            }
        });
    }

    getSearchDivData();

    //无限滚动(SUI JS有个大坑！！！！)
    function addItems(pageSize,pageIndex) {
        //生成新条目HTML
        var url=listUrl+'?'
            +'pageIndex='+pageIndex
            +'&pageSize='+pageSize
            +'&parentId='+parentId
            +'&areaId='+areaId
            +'&shopCategoryId='+shopCategoryId
            +'&shopName='+shopName;
        loading=true;
        $.getJSON(url,function (data) {
            if (data.success){
                maxItems=data.count;
                var html='';
                data.shopList.map(function (item,index) {
                    //注意这里的shopId是加双引号的，如果出错要检查这里
                    html+=''+'<div class="card mb-3 mt-4" style="max-width: 540px;" data-shop-id="'+item.shopId+'">'
                        +'<div class="row no-gutters">'
                        +'<div class="col-md-4">'
                        +'<img src="'+item.shopImg+'" class="card-img" alt="...">'
                        +'</div>'
                        +'<div class="col-md-8">'
                        +'<div class="card-body">'
                        +'<h5 class="card-title">'+item.shopName+'</h5>'
                        +'<p class="card-text">'+item.shopDesc+'</p>'
                        +'<p class="card-text"><small class="text-muted">'+new Date(item.lastEditTime).Format("yyyy-MM-dd")+'</small><a class="ml-5" href="#">点击查看</a></p>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                        +'</div>'
                });
                $('.list-div').append(html);
                var total=$('.list-div .card').length;
                if (total >= maxItems){
                    //加载完毕，则注销无限加载事件，以防不必要的加载
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    //删除加载提示符
                    $('.infinite-scroll-preloader').remove();
                }
                pageNum+=1;
                loading=false;
                $.refreshScroller();
            }
        });
    }

    //预先加载20条数据
    addItems(pageSize,pageNum);
    $(document).on('infinite','.infinite-scroll-bottom',function () {
        if (loading)
            return;
        addItems(pageSize,pageNum);
    });

    $('.shop-list').on('click','.card',function (e) {
        var shopId=e.currentTarget.dataset.shopId;
        window.location.href='/o2o/frontend/shopdetail?shopId='+shopId;
    });

    $('#shopList-search-div').on('click','.button',function (e) {
        if (parentId){  //如果传递过来的是一个父类下的子类
            shopCategoryId=e.target.dataset.categoryId;
            if ($(e.target).hasClass('button-fill')){
                $(e.target).removeClass('button-fill');
                shopCategoryId='';
            }else{
                $(e.target).addClass('button-fill').siblings().removeClass('button-fill');
            }
            $('.list-div').empty();
            pageNum=1;
            addItems(pageSize,pageNum);
        }else{  //如果传递过来的父类为空，则按照父类来查询
            parentId=e.target.dataset.categoryId;
            if ($(e.target).hasClass('button-fill')){
                $(e.target).removeClass('button-fill');
                shopCategoryId='';
            }else{
                $(e.target).addClass('button-fill').siblings().removeClass('button-fill');
            }
            $('.list-div').empty();
            pageNum=1;
            addItems(pageSize,pageNum);
            parentId='';
        }
    });

    $('#search').on('input',function (e) {
        shopName = e.target.value;
        $('.list-div').empty();
        pageNum=1;
        addItems(pageSize,pageNum);
    });

    $('#area-search').on('change',function (e) {
        areaId=e.target.value;
        $('.list-div').empty();
        pageNum=1;
        addItems(pageSize,pageNum);
    });

    $.init();
    $.detachInfiniteScroll();
});