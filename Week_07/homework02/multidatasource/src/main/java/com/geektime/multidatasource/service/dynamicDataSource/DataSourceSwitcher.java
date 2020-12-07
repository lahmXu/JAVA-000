package com.geektime.multidatasource.service.dynamicDataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
public class DataSourceSwitcher extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> dataSourceKey = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        /**
         * 控制切换逻辑，可以在此加负载均衡算法和切换逻辑
         */
        return dataSourceKey.get();
    }

    public static void clearDataSourceType(){
        log.info("clear dataSource type");
        dataSourceKey.remove();
    }

    public static void setDataSourceKey(String dataSource){
        dataSourceKey.set(dataSource);
    }

    public DataSourceSwitcher(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
}
