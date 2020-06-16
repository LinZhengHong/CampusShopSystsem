package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**店铺
 * @author LinZhenHong
 */
@Repository
public interface ShopDao {

    /**
     * 分页查询店铺，可输入的条件有：店铺名（模糊）,店铺状态，店铺类别，区域Id,owner
     * @param shopCondition
     * @param rowIndex(从第几行开始查询)
     * @param pageSize（显示几条数据）
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**p
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition")Shop shopCondition);

    /**
     * 根据shopId查询店铺
     * @param shopId
     * @return shop
     */
    Shop queryByShopId(long shopId);

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
