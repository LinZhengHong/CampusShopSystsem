package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

}
