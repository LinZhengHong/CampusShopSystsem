package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.dto.ShopExecution;
import com.linzhenghong.o2o.entity.Area;
import com.linzhenghong.o2o.entity.PersonInfo;
import com.linzhenghong.o2o.entity.Shop;
import com.linzhenghong.o2o.entity.ShopCategory;
import com.linzhenghong.o2o.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop() throws FileNotFoundException {
        Shop shop=new Shop();
        PersonInfo owner=new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setShopCategory(shopCategory);
        shop.setArea(area);
        shop.setOwner(owner);
        shop.setShopName("测试店铺3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg=new File("D:/image/xiaoshuji.png");
        InputStream shopImgInputStream=new FileInputStream(shopImg);
        ShopExecution shopExecution=shopService.addShop(shop,shopImgInputStream,shopImg.getName());
        System.out.println(ShopStateEnum.CHECK.getState()==shopExecution.getState());
    }
}
