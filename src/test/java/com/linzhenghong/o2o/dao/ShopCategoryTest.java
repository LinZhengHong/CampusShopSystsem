package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryTest extends BaseTest {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory(){
//        ShopCategory testCategory=new ShopCategory();
//        ShopCategory parentCategory=new ShopCategory();
//        parentCategory.setShopCategoryId(1L);
//        testCategory.setParent(parentCategory);
//        List<ShopCategory> shopCategories=shopCategoryDao.queryShopCategory(testCategory);
//        System.out.println(1==shopCategories.size());
//        System.out.println(shopCategories.get(0).getShopCategoryName());
        List<ShopCategory> shopCategoryList=shopCategoryDao.queryShopCategory(null);
        System.out.println(shopCategoryList.size());
    }
}
