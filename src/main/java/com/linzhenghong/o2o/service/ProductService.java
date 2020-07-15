package com.linzhenghong.o2o.service;

import com.linzhenghong.o2o.dto.ImageHolder;
import com.linzhenghong.o2o.dto.ProductExecution;
import com.linzhenghong.o2o.entity.Product;
import com.linzhenghong.o2o.exception.ProductOperationException;

import java.util.List;

/**
 * @author LinZhenHong
 */
public interface ProductService {


    /**
     * 添加商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail,List<ImageHolder> productImgList) throws ProductOperationException;

}
