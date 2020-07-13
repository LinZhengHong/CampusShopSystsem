$(function () {
    var listUrl='/o2o/shopadmin/getproductcategorylist';
    var addUrl='/o2o/shopadmin/addproductcategorys';
    var deleteUrl='/o2o/shopadmin/removeproductcategory';
    getList();
    function getList() {
        $.getJSON(
            listUrl,
            function (data) {
                if (data.success){
                    var dataList=data.data;
                    $('#tb').html('');
                    var tempHtml='';
                    dataList.map(function (item,index) {
                        tempHtml+='<tr><td>'+item.productCategoryName+'</td>'
                            +'<td>'+item.priority+'</td>'
                            +'<td><a href="#" class="btn btn-danger" id="'+item.productCategoryId+'">删除</a></td></tr>'
                    });
                    $('#tb').append(tempHtml);
                }
            }
        );
    }

    $('#new').click(function () {
        var tempHtml='<tr class="temp"><td><input class="category" type="text" placeholder="请输入商品类别"></td>'
            +'<td><input  class="priority" type="text" placeholder="请输入权重"></td>'
            +'<td><a href="#" class="btn btn-danger">删除</a></td></tr>'
        $('#tb').append(tempHtml);
    });

    $('#submit').click(function () {
        var tempArr=$('.temp');
        var productCategoryList=[];
        tempArr.map(function (index,item) {
            var tempObj={};
            //在这里也踩了一下坑
            tempObj.productCategoryName=$(item).find('.category').val();
            tempObj.priority=$(item).find('.priority').val();
            if (tempObj.productCategoryName&&tempObj.priority){
                productCategoryList.push(tempObj);
            }
        });

        $.ajax({
            url:addUrl,
            type:'POST',
            data:JSON.stringify(productCategoryList),
            contentType:'application/json',
            success:function (data) {
                if (data.success){
                    alert("提交成功");
                    //重新调用了getList渲染了页面，也就是按权重排序了
                    getList();
                }else{
                    alert("提交失败");
                }
            }
        });

    });
});