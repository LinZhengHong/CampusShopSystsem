package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.Product;
import org.springframework.stereotype.Repository;

/**
 * 商品类
 * @author LinZhenHong
 */
@Repository
public interface ProductDao {

    /**
     * 插入商品
     * @param product
     * @return Integer
     */
    int insertProduct(Product product);
}
