package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.Shop;
import org.springframework.stereotype.Repository;


/**店铺
 * @author LinZhenHong
 */
@Repository
public interface ShopDao {

    /**
     * 新增店铺
     * @param shop
     * @return 1
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     * @param shop
     * @return 1
     */
    int updateShop(Shop shop);
}
