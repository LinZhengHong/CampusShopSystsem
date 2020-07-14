package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 批量新增商品类别
     * @param productCategoryList
     * @return effectedNum
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);


    /**
     *删除指定商品类别
     * @param productCategoryId
     * @param shopId
     * @return effectedNum
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);

}
