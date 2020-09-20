package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.dto.ShopExecution;
import com.linzhenghong.o2o.entity.Area;
import com.linzhenghong.o2o.entity.PersonInfo;
import com.linzhenghong.o2o.entity.Shop;
import com.linzhenghong.o2o.entity.ShopCategory;
import com.linzhenghong.o2o.enums.ShopStateEnum;
import com.linzhenghong.o2o.exception.ShopOperationException;
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


    /**
     * 测试分页查询返回的店铺列表
     */
    @Test
    public void testGetShopList(){
        Shop shopCondition=new Shop();
        ShopCategory shopCategory=new ShopCategory();
        shopCategory.setShopCategoryId(12L);
        shopCondition.setShopCategory(shopCategory);
        ShopExecution shopExecution=shopService.getShopList(shopCondition,1,6);
        System.out.println("店铺列表数"+shopExecution.getShopList().size());
        System.out.println("店铺总数:"+shopExecution.getCount());
    }


    /**
     * 测试更新店铺信息以及图片的处理
     * @throws ShopOperationException
     * @throws FileNotFoundException
     */
    @Test
    public void testModifyShop() throws ShopOperationException,FileNotFoundException{
        Shop shop=new Shop();
        shop.setShopId(1L);
        shop.setShopName("修改后的店铺名称");
        File shopImg=new File("D:/image/xiaohei.jpg");
        InputStream inputStream=new FileInputStream(shopImg);
        ShopExecution shopExecution=shopService.modifyShop(shop,inputStream,"xiaohei.jpg");
        System.out.println("修改后的图片地址为:"+shopExecution.getShop().getShopImg());
    }

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
