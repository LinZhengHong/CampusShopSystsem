package com.linzhenghong.o2o.service.impl;

import com.linzhenghong.o2o.dao.ShopDao;
import com.linzhenghong.o2o.dto.ShopExecution;
import com.linzhenghong.o2o.entity.Shop;
import com.linzhenghong.o2o.enums.ShopStateEnum;
import com.linzhenghong.o2o.exception.ShopOperationException;
import com.linzhenghong.o2o.service.ShopService;
import com.linzhenghong.o2o.util.ImageUtil;
import com.linzhenghong.o2o.util.PageCalculator;
import com.linzhenghong.o2o.util.PathUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;

/**店铺实现类
 * @author LinZhenHong
 */
@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopDao shopDao;


    /**
     * 根据shopCondition分页返回相应店铺列表数据
     *
     * @param shopCondition
     * @param pageIndex     这里为啥不是rowIndex尼，因为前端只认页数，而dao层只认函数，所以编写一个转换工具类PageCalculator
     * @param pageSize
     * @return
     */
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex= PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList=shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count=shopDao.queryShopCount(shopCondition);
        ShopExecution shopExecution=new ShopExecution();
        if (shopCondition!=null){
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        }else{
            //shopCondition为空，则返回错误的状态
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }

    /**
     * 根据shopId查询店铺
     *
     * @param shopId
     * @return shop
     */
    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    /**
     * 更新店铺的信息以及图片的处理
     *
     * @param shop
     * @param shopImg
     * @param filename
     * @return shopExecution
     */
    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImg, String filename)throws ShopOperationException {

        if (shop==null||shop.getShopId()==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            try {
                //1判断是否需要处理图片
                if (shopImg != null && filename != null && !"".equals(filename)) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, shopImg, filename);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            }catch (Exception e) {
                //抛出更新错误的异常
                throw new ShopOperationException("modifyShop error"+e.getMessage());
            }
        }
    }

    /**
     * 添加店铺
     * @param shop 店铺
     * @param shopImgInputStream 店铺图片
     * @return shopExecution
     */
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String filename) throws ShopOperationException{
        //空值判断
        if(shop==null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum=shopDao.insertShop(shop);
            if(effectedNum<=0){
                throw new ShopOperationException("店铺创建失败");
            }else{
                if(shopImgInputStream !=null){
                    //存储图片
                    try {
                        addShopImg(shop,shopImgInputStream,filename);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg error: "+e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectedNum=shopDao.updateShop(shop);
                    if (effectedNum<=0){
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        }catch (Exception e){
            throw new ShopOperationException("addShop error: "+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }


    /**
     * 获取店铺图片
     */
    private void addShopImg(Shop shop, InputStream shopImgInputStream,String filename) {
        //获取shop图片目录的相对路径
        String dest= PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr= ImageUtil.generateThumbnail(shopImgInputStream,filename,dest);
        shop.setShopImg(shopImgAddr);
    }

}
