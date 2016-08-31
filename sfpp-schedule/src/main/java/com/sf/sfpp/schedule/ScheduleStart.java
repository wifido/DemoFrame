package com.sf.sfpp.schedule;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/30
 */
public class ScheduleStart {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/spring/spring-config-service.xml");
    }
}
