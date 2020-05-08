package com.linzhenghong.o2o.util;

/**
 * 路径地址处理工具类
 */
public class PathUtil {

    /**
     * 获取系统的分隔符
     */
    private static String seperator=System.getProperty("file.separator");

    /**
     * 返回项目图片的根路径
     * @return basePath
     */
    public static String getImgBasePath(){
        //获取系统
        String os=System.getProperty("os.name");
        String basePath="";
        //判断不同的系统，图片存放的位置
        if(os.toLowerCase().startsWith("win")){
            basePath="D:/image/";
        }else{
            basePath="/home/linzhenhong/image/";
        }
        basePath=basePath.replace("/",seperator);
        return basePath;
    }

    /**
     * 返回项目图片的子路径
     * @param shopId
     * @return imagePath
     */
    public static String getShopImagePath(long shopId) {
        String imagePath = "upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
