package com.geektime.muiltdatasource.service.dynamicDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DynamicDataSource extends AbstractRoutingDataSource {

    private Object masterDataSource;

    private List<Object> slaveDataSources;

    private int slaveDataSourceSize;

    private int slaveDataSourcePollPattern = 0; //获取读数据源方式，0：随机，1：轮询

    private AtomicLong counter = new AtomicLong(0);

    private static final Long MAX_POOL = Long.MAX_VALUE;

    private final Lock lock = new ReentrantLock();

    @Override
    public void afterPropertiesSet() {
        if (this.masterDataSource == null) {
            throw new IllegalArgumentException("master dataSource is required");
        }
        setDefaultTargetDataSource(masterDataSource);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceTypeEnum.MASTER.name(), masterDataSource);
        if (this.slaveDataSources == null) {
            slaveDataSourceSize = 0;
        } else {
            for(int i = 0; i< slaveDataSources.size(); i++) {
                targetDataSources.put(DataSourceTypeEnum.SLAVE.name() + i, slaveDataSources.get(i));
            }
            slaveDataSourceSize = slaveDataSources.size();
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {


        DataSourceTypeEnum dataSourceType = DynamicDataSourceHolder.getDataSource();

        if(dataSourceType == null
                || dataSourceType == DataSourceTypeEnum.MASTER
                || slaveDataSourceSize <= 0) {
            return DataSourceTypeEnum.MASTER.name();
        }

        int index = 1;

        if(slaveDataSourcePollPattern == 1) {
            //轮询方式
            long currValue = counter.incrementAndGet();
            if((currValue + 1) >= MAX_POOL) {
                try {
                    lock.lock();
                    if((currValue + 1) >= MAX_POOL) {
                        counter.set(0);
                    }
                } finally {
                    lock.unlock();
                }
            }
            index = (int) (currValue % slaveDataSourceSize);
        } else {
            //随机方式
            index = ThreadLocalRandom.current().nextInt(0, slaveDataSourceSize);
        }
        return dataSourceType.name() + index;
    }

    public void setMasterDataSource(Object masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public void setSlaveDataSources(List<Object> slaveDataSources) {
        this.slaveDataSources = slaveDataSources;
    }

    public void setSlaveDataSourcePollPattern(int slaveDataSourcePollPattern) {
        this.slaveDataSourcePollPattern = slaveDataSourcePollPattern;
    }

}
