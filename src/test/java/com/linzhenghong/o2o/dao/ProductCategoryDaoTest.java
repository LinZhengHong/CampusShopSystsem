package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商铺类别的测试
 */
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testQueryByShopId() throws Exception{
        long shopId=1;
        List<ProductCategory> productCategories=productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("该店铺自定义类别数为："+productCategories.size());
    }

    @Test
    public void testBatchInsertProductCategory() {
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("店铺商品类别4");
        productCategory1.setPriority(1);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(1L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("店铺商品类别5");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);

        List<ProductCategory> productCategories = new ArrayList<ProductCategory>();
        productCategories.add(productCategory1);
        productCategories.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategories);
        System.out.println(effectedNum == 2);
    }

    @Test
    public void testDeleteProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setShopId(1L);
        productCategory.setProductCategoryId(8L);
        int effectedNum=productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(),productCategory.getShopId());
        System.out.println(effectedNum==1);
    }
}
