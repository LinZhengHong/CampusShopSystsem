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
