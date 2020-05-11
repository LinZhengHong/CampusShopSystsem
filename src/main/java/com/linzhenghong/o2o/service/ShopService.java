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
     * 添加店铺
     * @param shop
     * @param shopImg
     * @return shopExecution
     */
    ShopExecution addShop(Shop shop, InputStream shopImg,String filename) throws ShopOperationException;
}
