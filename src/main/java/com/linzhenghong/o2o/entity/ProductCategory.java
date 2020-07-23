package com.linzhenghong.o2o.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * 商品类别
 * @author LinZhenHong
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCategory {
    /**
     * 商品类别id
     */
    private Long productCategoryId;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 商品类别名称
     */
    private String productCategoryName;

    /**
     * 权重
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createTime;

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
