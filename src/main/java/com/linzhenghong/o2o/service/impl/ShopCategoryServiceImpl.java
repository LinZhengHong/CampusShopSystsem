package com.linzhenghong.o2o.service.impl;

import com.linzhenghong.o2o.dao.ShopCategoryDao;
import com.linzhenghong.o2o.entity.ShopCategory;
import com.linzhenghong.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    /**
     * 查询店铺类别
     *
     * @param shopCategoryCondition shopCategoryCondition
     * @return shopCategory
     */
    @Override
    public List<ShopCategory> queryShopCategory(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
