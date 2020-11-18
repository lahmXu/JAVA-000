package com.lahmxu.springbean.spring01.AnnotationAssembly;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 注解装配
 * 定义Bean注解
 *  1. @Component 下面三个注解和Component注解一样
 *  2. @Controller
 *  3. @Service
 *  4. @Repository
 * 装配Bean注解
 *  1. @Autowired: 默认按照Bean的类型进行装配。
 *  2. @Resource: 和@Autowired功能相同，不同的是@Resource默认按照Bean的名称进行装配
 *  3. @Qualifier: 与@Autowired注解配合使用，会默认的按Bean类型装配修改为按Bean实例名称装配。Bean的实例名称由@Qualifier注解参数决定
 */
public class AnnotationAssemblyTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(StudentConfig.class);
        Student student = applicationContext.getBean(Student.class);
        System.out.println(student);
    }
}
