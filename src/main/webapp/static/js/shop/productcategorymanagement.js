$(function (message) {
    var listUrl='/o2o/shopadmin/getproductcategorylist';
    var addUrl='/o2o/shopadmin/addproductcategorys';
    var deleteUrl='/o2o/shopadmin/removeproductcategory';
    getList();
    function getList() {
        $.getJSON(
            listUrl, function (data) {
                if (data.success){
                    var dataList=data.data;
                    $('#tb').html('');
                    var tempHtml='';
                    dataList.map(function (item,index) {
                        tempHtml+='<tr class="now"><td>'+item.productCategoryName+'</td>'
                            +'<td>'+item.priority+'</td>'
                            +'<td><a href="#" class="btn btn-danger delete" data-id='+item.productCategoryId+'>删除</a></td></tr>'
                    });
                    $('#tb').append(tempHtml);
                }
            }
        );
    }

    $('#new').click(function () {
        var tempHtml='<tr class="temp"><td><input class="category" type="text" placeholder="请输入商品类别"></td>'
            +'<td><input  class="priority" type="text" placeholder="请输入权重"></td>'
            +'<td><a href="#" class="btn btn-danger delete">删除</a></td></tr>'
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

    $('#tb').on('click','.temp .delete',
        function (e) {
        console.log($(this).parent().parent());
        $(this).parent().parent().remove();
    });

    $('#tb').on('click','.now .delete',function (e) {
        var target = e.currentTarget;
        //在这里也踩了一个很久的坑，productCategoryId总获取不到，原来上面的data-id写成了date-id和confirm写法不行！，我讨厌JS!!!!
        var status=confirm('确定删除吗');
        if (!status){
            return false;
        }
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                data: {
                    productCategoryId: target.dataset.id
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        alert("删除成功");
                        getList();
                    } else {
                        alert("删除失败!");
                    }
                }
            });

    });


});