package com.lahmxu.springbean.spring01.XmlAssembly;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring XML装配两个实现
 * 1. 设置注入
 * 2. 构造注入
 *
 */
public class XmlBeanAssemblyTest {
    public static void main(String[] args) {
        String xmlPath = "applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlPath);
        // 设值注入
        System.out.println(applicationContext.getBean("complexAssembly"));
        // 构造注入
        System.out.println(applicationContext.getBean("student"));
    }
}
