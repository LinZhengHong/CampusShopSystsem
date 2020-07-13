package com.linzhenghong.o2o.dto;

import com.linzhenghong.o2o.entity.ProductCategory;
import com.linzhenghong.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

/**
 * 商品类别执行结果
 * @author LinZhenHong
 */
public class ProductCategoryExecution {
    /**
     * 结果状态
     */
    private int state;

    /**
     * 状态标识
     */
    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    /**
     * 操作失败时
     */
    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum) {
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
    }

    /**
     * 操作成功时
     */
    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum,List<ProductCategory> productCategoryList){
        this.state=productCategoryStateEnum.getState();
        this.stateInfo=productCategoryStateEnum.getStateInfo();
        this.productCategoryList=productCategoryList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
