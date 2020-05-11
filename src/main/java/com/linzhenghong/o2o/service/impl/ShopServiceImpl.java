package com.linzhenghong.o2o.service.impl;

import com.linzhenghong.o2o.dao.ShopDao;
import com.linzhenghong.o2o.dto.ShopExecution;
import com.linzhenghong.o2o.entity.Shop;
import com.linzhenghong.o2o.enums.ShopStateEnum;
import com.linzhenghong.o2o.exception.ShopOperationException;
import com.linzhenghong.o2o.service.ShopService;
import com.linzhenghong.o2o.util.ImageUtil;
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

/**店铺实现类
 * @author LinZhenHong
 */
@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopDao shopDao;

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
     * @param shop
     * @param shopImg
     */
    private void addShopImg(Shop shop, InputStream shopImgInputStream,String filename) {
        //获取shop图片目录的相对路径
        String dest= PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr= ImageUtil.generateThumbnail(shopImgInputStream,filename,dest);
        shop.setShopImg(shopImgAddr);
    }

    /**
     * 把File转化为CommonsMultipartFile
     */
    /*public FileItem createFileItem(File file) {
        //DiskFileItemFactory()：构造一个配置好的该类的实例
        //第一个参数threshold(阈值)：以字节为单位.在该阈值之下的item会被存储在内存中，在该阈值之上的item会被当做文件存储
        //第二个参数data repository：将在其中创建文件的目录.用于配置在创建文件项目时，当文件项目大于临界值时使用的临时文件夹，默认采用系统默认的临时文件路径
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        //fieldName：表单字段的名称；第二个参数 ContentType；第三个参数isFormField；第四个：文件名
        FileItem item = factory.createItem(null, "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(file);
            os = item.getOutputStream();
            while((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                //从buffer中得到数据进行写操作
                os.write(buffer, 0, bytesRead);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(os != null) {
                    os.close();
                }
                if(fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return item;
    }*/

}
