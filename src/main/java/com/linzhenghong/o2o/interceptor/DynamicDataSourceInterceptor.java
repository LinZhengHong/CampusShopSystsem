package com.linzhenghong.o2o.interceptor;

import com.linzhenghong.o2o.split.DynamicDataSourceHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * 主从库读写的拦截器，来判断数据库执行哪些操作，以使用哪个数据源（master or slave）
 * @author LinZhenHong
 */
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
@Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {


    private static Logger logger= LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
    private static final String REGEX=".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean synchronizationActive= TransactionSynchronizationManager.isActualTransactionActive();
        Object[] objects=invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) objects[0];
        String lookupKey=DynamicDataSourceHolder.DB_MASTER;
        if (synchronizationActive!=true){
            //读方法
            if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)){
                //selectKey为自增id查询主键(SELECT LAST_INSERT_ID())方法，使用主库
                if (mappedStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                    lookupKey= DynamicDataSourceHolder.DB_MASTER;
                }else{
                    BoundSql boundSql=mappedStatement.getBoundSql(objects[1]);
                    String sql=boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]","");
                    if (sql.matches(REGEX)){
                        lookupKey=DynamicDataSourceHolder.DB_MASTER;
                    }else{
                        lookupKey=DynamicDataSourceHolder.DB_SLAVE;
                    }
                }
            }
        }else{
            lookupKey=DynamicDataSourceHolder.DB_MASTER;
        }
        logger.debug("设置方法[{}] use [{}] Strategy,SqlCommandType[{}]..",mappedStatement.getId(),lookupKey);
        DynamicDataSourceHolder.setDbType(lookupKey);
        return invocation.proceed();
    }

    /**
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor){
            return Plugin.wrap(target,this);
        }else {
            return target;
        }
    }

    /**
     * 类初始化设置参数
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
