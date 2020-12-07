package com.geektime.multidatasource.service.dynamicDataSource;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseDataSource {

    DataSourceType value() default DataSourceType.MASTER;

}
