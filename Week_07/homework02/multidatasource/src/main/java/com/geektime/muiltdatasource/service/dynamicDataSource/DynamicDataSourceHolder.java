package com.geektime.muiltdatasource.service.dynamicDataSource;

/**
 * 数据源类型存储类
 */
public class DynamicDataSourceHolder {
    private static final ThreadLocal<DataSourceTypeEnum> holder = new ThreadLocal<DataSourceTypeEnum>();

    public static void putDataSource(DataSourceTypeEnum dataSource){
        holder.set(dataSource);
    }

    public static DataSourceTypeEnum getDataSource(){
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

    public static void master(){
        holder.set(DataSourceTypeEnum.MASTER);
    }

    public static void slave(){
        holder.set(DataSourceTypeEnum.SLAVE);
    }
}
