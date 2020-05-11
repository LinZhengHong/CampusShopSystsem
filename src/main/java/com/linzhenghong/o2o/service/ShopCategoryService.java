package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.entity.ShopCategory;
import java.util.List;

/**
 * @author LinZhenHong
 */
public interface ShopCategoryService {

    /**
     * 查询店铺类别
     * @param shopCategoryCondition shopCategoryCondition
     * @return shopCategory
     */
    List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition);
}
