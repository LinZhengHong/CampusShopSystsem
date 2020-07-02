package com.linzhenghong.o2o.service.impl;

import com.linzhenghong.o2o.dao.ProductCategoryDao;
import com.linzhenghong.o2o.entity.ProductCategory;
import com.linzhenghong.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LinZhenHong
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    /**
     * 查询指定某个店铺下的所有商品类别信息
     *
     * @param shopId
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }
}
