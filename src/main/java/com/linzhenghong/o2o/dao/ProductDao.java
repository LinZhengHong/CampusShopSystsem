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

    /**
     * 通过productId查询唯一的衫品信息
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * 更新商品信息
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 删除指定商品下的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);



}
