/*
*
* */

$(function () {
    var shopId=getQueryString("shopId");
    var isEdit=shopId?true:false;
    /*注册店铺url*/
    const initUrl='/o2o/shopadmin/getshopinitinfo';
    const registerShopUrl='/o2o/shopadmin/registershop';
    /*更改店铺信息url*/
    const shopInfoUrl='/o2o/shopadmin/getshopbyid?shopId='+shopId;
    const editShopUrl='/o2o/shopadmin/modifyshop';
    //如果传入shopId则页面时更新页面，如果不传入shopId，则也页面是注册店铺的
    if(!isEdit){
        getShopinitinfo();
    }else{
        getShopInfo(shopId);
    }

    //店铺编辑的信息
    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl,function (data) {
            if (data.success){
                //通过ajax成功请求到shop
                var shop=data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory='<option value="' +shop.shopCategory.shopCategoryId+ '" selected>'
                    +shop.shopCategory.shopCategoryName+'</option>';
                var tempAreaHtml='';
                data.areaList.map(function (item,index) {
                    tempAreaHtml+='<option value="'+item.areaId+'">'
                        +item.areaName+'</option>'
                });
                $('#shop-category').html(shopCategory);
                $('#area').html(tempAreaHtml);
            }
        });
    }
    //获取店铺初始化信息
    function getShopinitinfo() {
        $.getJSON(initUrl,function (data) {
            if (data.success){
                var tempHtml='';
                var tempAreaHtml='';
                data.shopCategoryList.map(function (item,index) {
                    tempHtml+='<option value="'+item.shopCategoryId+'">'
                        +item.shopCategoryName+'</option>'
                });
                data.areaList.map(function (item,index) {
                    tempAreaHtml+='<option value="'+item.areaId+'">'
                        +item.areaName+'</option>'
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });
    }

    //提交注册店铺信息
    $('#submit').click(function () {
        var shop={};
        //这里要传入shopId
        if (isEdit){
            shop.shopId=shopId;
        }
        shop.shopName=$('#shop-name').val();
        shop.shopAddr=$('#shop-addr').val();
        shop.phone=$('#shop-phone').val();
        shop.shopDesc=$('#shop-desc').val();
        //获取shopCategoryId和areaId这里坑了我好久，注意一下
        shop.shopCategory={
            shopCategoryId : $('#shop-category').find("option:selected").val()
        };
        shop.area ={
            areaId : $('#area').find("option:selected").val()
        };
        /*shop.shopCategory={
            shopCategoryId : $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area ={
            areaId : $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };*/
        var shopImg=$('#shop-img')[0].files[0];
        var formData=new FormData();
        formData.append('shopImg',shopImg);
        formData.append('shopStr',JSON.stringify(shop));
        var verifyCodeActual=$('#j_captcha').val();
        if (!verifyCodeActual){
            alert("请输入验证码");
            return;
        }
        formData.append("verifyCodeActual",verifyCodeActual);
        $.ajax({
            //判断是注册还是编辑店铺页面
            url:(isEdit?editShopUrl:registerShopUrl),
            type:'POST',
            data:formData,
            contentType:false,
            processData: false,
            cache:false,
            success:function (data) {
                if(data.success){
                    alert('提交成功');
                }
                else {
                    alert('提交失败'+data.errMsg);
                }
                /*提交后不管成功或者失败，都会更换验证码*/
                $('#captcha_img').click();
            }
        });
    });
});