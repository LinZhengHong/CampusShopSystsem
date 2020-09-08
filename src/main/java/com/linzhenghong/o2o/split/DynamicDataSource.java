package com.linzhenghong.o2o.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 主从库读写分离
 * @author LinZhenHong
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDbType();
    }
}
