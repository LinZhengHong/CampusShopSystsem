package com.linzhenghong.o2o.web.frontend;

import com.linzhenghong.o2o.dto.ProductExecution;
import com.linzhenghong.o2o.entity.Product;
import com.linzhenghong.o2o.entity.ProductCategory;
import com.linzhenghong.o2o.entity.Shop;
import com.linzhenghong.o2o.service.ProductCategoryService;
import com.linzhenghong.o2o.service.ProductService;
import com.linzhenghong.o2o.service.ShopService;
import com.linzhenghong.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LinZhenHong
 */

@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及该店铺下面的商品类别列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopDetailPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        //获取前台传过来的shopId
        long shopId= HttpServletRequestUtil.getLong(request,"shopId");
        Shop shop=null;
        List<ProductCategory> productCategoryList=null;
        if (shopId!=-1){
            //获取店铺为shopId的店铺信息
            shop=shopService.getByShopId(shopId);
            //获取店铺下面的商品类别列表
            productCategoryList=productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop",shop);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }


    @RequestMapping(value = "/listproductsbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listProductsByShop(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        //获取页码
        int pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取一页需要显示的条数
        int pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        //获取店铺Id
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        //空值判断
        if ((pageIndex>-1)&&(pageSize>-1)&&(shopId>-1)){
            //尝试获取商品类别Id
            long productCategoryId=HttpServletRequestUtil.getInt(request,"productCategoryId");
            //尝试获取模糊查询的商品名
            String productName=HttpServletRequestUtil.getString(request,"productName");
            //组合查询条件
            Product productCondition=compactProductCondition4Search(shopId,productCategoryId,productName);
            //按照传入的查询条件以及分页信息返回相应商品列表以及总数
            ProductExecution productExecution=productService.getProductList(productCondition,pageIndex,pageSize);
            modelMap.put("productList",productExecution.getProductList());
            modelMap.put("count",productExecution.getCount());
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    private Product compactProductCondition4Search(long shopId,long productCategoryId,String productName) {
        Product productCondition=new Product();
        Shop shop=new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId!=-1L){
            //查询某个商品类别下面的商品列表
            ProductCategory productCategory=new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName!=null){
            //查询名字里包含productName的店铺列表
            productCondition.setProductName(productName);
        }
        //只允许选出状态为上架的商品
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}