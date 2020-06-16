package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.dto.ShopExecution;
import com.linzhenghong.o2o.entity.Shop;
import com.linzhenghong.o2o.exception.ShopOperationException;

import java.io.File;
import java.io.InputStream;

/**店铺dao
 * @author LinZhenHong
 */
public interface ShopService {


    /**
     *根据shopCondition分页返回相应店铺列表数据
     * @param shopCondition
     * @param pageIndex 这里为啥不是rowIndex尼，因为前端只认页数，而dao层只认函数，所以编写一个转换工具类PageCalculator
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

    /**
     * 根据shopId查询店铺
     * @param shopId
     * @return shop
     */
    Shop getByShopId(long shopId);


    /**
     * 更新店铺的信息以及图片的处理
     * @param shop
     * @param shopImg
     * @param filename
     * @return shopExecution
     */
    ShopExecution modifyShop(Shop shop,InputStream shopImg,String filename)throws ShopOperationException;


    /**
     * 添加店铺
     * @param shop
     * @param shopImg
     * @return shopExecution
     */
    ShopExecution addShop(Shop shop, InputStream shopImg,String filename) throws ShopOperationException;
}
