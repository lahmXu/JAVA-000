package com.geektime.multidatasource.service.dynamicDataSource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(1)
public class UseDataSourceAspect {

    @Around("@annotation(useDataSource)")
    public Object dataSourceSwitcher(ProceedingJoinPoint joinPoint, UseDataSource useDataSource) throws Throwable {
        //设置数据源
        log.info("****************current datasource set: {}", useDataSource.value().name());
        DataSourceSwitcher.setDataSourceKey(useDataSource.value().name());
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            DataSourceSwitcher.clearDataSourceType();
        }
    }
}
