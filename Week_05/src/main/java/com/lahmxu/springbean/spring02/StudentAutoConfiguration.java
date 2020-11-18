package com.lahmxu.springbean.spring02;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 默认关闭，配置文件实现开启加载
 */
@Log4j2
@ConditionalOnProperty(prefix = "student.config", name = "enabled", havingValue = "true", matchIfMissing = false)
public class StudentAutoConfiguration {
    @Bean
    public Student student() {
        log.info("******************* init StudentAutoConfiguration *********************");
        Student student = Student.create();
        log.info("init student bean: {}",student.toString());
        return student;
    }
}
