package com.linzhenghong.o2o.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author LinZhenHong
 */
public class DynamicDataSourceHolder {

    private static Logger logger= LoggerFactory.getLogger(DynamicDataSourceHolder.class);

    private static ThreadLocal<String> contextHolder=new ThreadLocal<>();

    //主库
    public static final String DB_MASTER="master";

    //从库
    public static final String DB_SLAVE="slave";

    //获取线程的DBType
    public static String getDbType(){
        String db=contextHolder.get();
        if (db==null){
            db=DB_MASTER;
        }
        return db;
    }

    //设置线程的DBType
    public static void setDbType(String str){
        logger.debug("所使用的数据源"+str);
        contextHolder.set(str);
    }

    /**
     * 清理连接类型
     */
    public static void clearDbType(){
        contextHolder.remove();
    }
}
