package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.BaseTest;
import com.linzhenghong.o2o.entity.Product;
import com.linzhenghong.o2o.entity.ProductCategory;
import com.linzhenghong.o2o.entity.ProductImg;
import com.linzhenghong.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Test
    public void testQueryProductByProductId() throws Exception{
        long productId=1;
        //初始化两个商品详情图实例作为productId为1的商品下的详情图片
        //批量插入到商品详情图片表中
        ProductImg productImg1=new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);

        ProductImg productImg2=new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);

        List<ProductImg> productImgList=new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);


        int effectedNum=productImgDao.batchInsertProductImg(productImgList);
        //查询productId为1的商品信息并校验返回的详情图实例列表size是否为2
        Product product=productDao.queryProductById(productId);
        System.out.println(product.getProductImgList().size()==2);

        //删除新增的这两个商品详情图实例
        effectedNum=productImgDao.deleteProductImgByProductId(productId);
        System.out.println(effectedNum==2);
    }

    @Test
    public void testDUpdateProduct() throws Exception{
        Product product=new Product();
        ProductCategory productCategory=new ProductCategory();
        Shop shop=new Shop();
        shop.setShopId(1L);
        productCategory.setProductCategoryId(2L);
        product.setProductId(1L);
        product.setProductName("第二个产品");
        product.setProductCategory(productCategory);
        product.setShop(shop);

        //修改productId为1的商品的名称
        int effectedNum=productDao.updateProduct(product);
        System.out.println(effectedNum==1);
    }
}
