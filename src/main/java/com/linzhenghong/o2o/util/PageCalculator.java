package com.linzhenghong.o2o.util;

/**
 * 分页页数转换工具类
 * @author LinZhenHong
 */
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex>0)?(pageIndex-1)*pageSize:0;
    }
}
