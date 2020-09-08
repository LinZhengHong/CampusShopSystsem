package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品类
 * @author LinZhenHong
 */
@Repository
public interface ProductDao {


    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id，商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);


    int queryProductCount(@Param("productCondition") Product productCondition);

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
