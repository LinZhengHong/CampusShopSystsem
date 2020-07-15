package com.linzhenghong.o2o.dao;

import com.linzhenghong.o2o.entity.ProductImg;

import java.util.List;

/**
 * @author LinZhenHong
 */
public interface ProductImgDao {
    List<ProductImg> queryProductImgList(long productId);

    /**
     * 批量添加商品详情图片
     * @param productImg
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImg);

    int deleteProductImgByProductId(long productId);
}
