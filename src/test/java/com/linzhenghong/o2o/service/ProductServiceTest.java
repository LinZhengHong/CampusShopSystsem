package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.dao.ProductDao;
import com.linzhenghong.o2o.dto.ImageHolder;
import com.linzhenghong.o2o.dto.ProductExecution;
import com.linzhenghong.o2o.entity.Product;
import com.linzhenghong.o2o.entity.ProductCategory;
import com.linzhenghong.o2o.entity.Shop;
import com.linzhenghong.o2o.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws FileNotFoundException {
        //不能批量添加两个图片
        Product product=new Product();
        Shop shop=new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory=new ProductCategory();
        productCategory.setProductCategoryId(2L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        //创建缩略图文件流
        File thumbnailFile=new File("D:/image/nziko.jpg");
        InputStream inputStream=new FileInputStream(thumbnailFile);
        ImageHolder thumbnail=new ImageHolder(thumbnailFile.getName(),inputStream);
        //创建两个商品详情图文件流并将他们添加到详情图列表中
        File productImg1=new File("D:/image/tanilang.jpg");
        InputStream inputStream1=new FileInputStream(productImg1);
        File productImg2=new File("D:/image/taileng.jpg");
        InputStream inputStream2=new FileInputStream(productImg2);
        List<ImageHolder> productImagList=new ArrayList<>();
        productImagList.add(new ImageHolder(productImg1.getName(),inputStream1));
        productImagList.add(new ImageHolder(productImg2.getName(),inputStream2));
        //添加商品并验证
        ProductExecution productExecution=productService.addProduct(product,thumbnail,productImagList);
        System.out.println(ProductStateEnum.SUCCESS.getState()==productExecution.getState());
    }
}
