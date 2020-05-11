/*
*
* */

$(function () {
    const initUrl='/o2o/shopadmin/getshopinitinfo';
    const registerShopUrl='/o2o/shopadmin/registershop';
    alert(initUrl);
    getShopinitinfo();
    function getShopinitinfo() {
        $.getJSON(initUrl,function (data) {
            if (data.success){
                let tempHtml='';
                let tempAreaHtml='';
                data.shopCategoryList.map(function (item,index) {
                    tempHtml+='<option date-id="'+item.shopCategoryId+'">'
                        +item.shopCategoryName+'</option>'
                });
                data.areaList.map(function (item,index) {
                    tempAreaHtml+='<option date-id="'+item.areaId+'">'
                        +item.areaName+'</option>'
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml)
            }
        });
        $('#submit').click(function () {
            const shop={};
            shop.shopName=$('#shop-name').val();
            shop.shopAddr=$('#shop-addr').val();
            shop.shopPhone=$('#shop-phone').val();
            shop.shopCategory={
                shopCategoryId:$('#shop-category').find('option').not(function () {
                    return!this.selected();
                }).data('id')
            };
            shop.area ={
                areaId:$('#area').find('option').not(function () {
                    return!this.selected();
                }).data('id')
            };
            const shopImg=$('#shop-img')[0].files[0];
            const formData=new FormData();
            formData.append('shopImg',shopImg);
            formData.append('shopStr',JSON.stringify(shop));
            const verifyCodeActual=$('#j_captcha').val();
            if (!verifyCodeActual){
                alert("请输入验证码");
                return;
            }
            formData.append("verifyCodeActual",verifyCodeActual);
            $.ajax({
                url:registerShopUrl,
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

        })
    }
});