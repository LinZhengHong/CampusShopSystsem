package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.ProductCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LinZhenHong
 */
@Repository
public interface ProductCategoryDao {

    /**
     * 通过shopId查询店铺商品类别
     * @param shopId
     * @return List<ProductCategory>
     */
    List<ProductCategory> queryProductCategoryList(long shopId);
}
