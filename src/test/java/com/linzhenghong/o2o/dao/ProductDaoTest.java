package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.entity.Product;
import com.linzhenghong.o2o.entity.ProductCategory;
import com.linzhenghong.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Autowired
    private ProductDao productDao;

    @Test
    public void testInsertProduct(){
        Shop shop1=new Shop();
        shop1.setShopId(1L);
        ProductCategory productCategory=new ProductCategory();
        productCategory.setProductCategoryId(2L);
        //初始化三个商品实例并添加进shopId为1的店铺里
        //同时商品类别Id为1
        Product product1=new Product();
        product1.setProductName("商品1");
        product1.setProductDesc("商品Desc1");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(productCategory);

        Product product2=new Product();
        product2.setProductName("商品2");
        product1.setProductDesc("商品Desc2");
        product2.setImgAddr("test2");
        product2.setPriority(1);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(productCategory);

        Product product3=new Product();
        product3.setProductName("商品3");
        product3.setProductDesc("商品Desc3");
        product3.setImgAddr("test3");
        product3.setPriority(3);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop1);
        product3.setProductCategory(productCategory);


        /**单步不乱,那么乱序出现在循环端的可能性先排除。
         * 因为你的执行命令是一行一条连续发出且间隔时间几乎没有，
         * 可能造成这数据库服务器在接收/处理这几万条数据时发生了乱序。
         */
        //是否添加成功
        int effectedNum=productDao.insertProduct(product1);
        System.out.println(effectedNum==1);

        effectedNum=productDao.insertProduct(product2);
        System.out.println(effectedNum==1);

        effectedNum=productDao.insertProduct(product3);
        System.out.println(effectedNum==1);



    }
}
