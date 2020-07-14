package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.dto.ProductCategoryExecution;
import com.linzhenghong.o2o.entity.ProductCategory;
import com.linzhenghong.o2o.exception.ProductCategoryOperationException;

import java.util.List;

/**
 * @author LinZhenHong
 */
public interface ProductCategoryService {

    /**
     * 查询指定某个店铺下的所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);


    /**
     * 批量添加商品类别
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;


    /**
     * 将此类别下的商品里的类别id设置为空，再删除掉该商品类别
     * @param ProductCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long ProductCategoryId,long shopId) throws ProductCategoryOperationException;

}
