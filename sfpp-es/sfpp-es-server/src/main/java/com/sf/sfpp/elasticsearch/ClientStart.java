package com.sf.sfpp.elasticsearch;

import com.sf.sfpp.elasticsearch.pcomp.PcompSearchService;
import com.sf.sfpp.elasticsearch.pcomp.SortRule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/18
 */
public class ClientStart {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        PcompSearchService pcompSearchService = (PcompSearchService) applicationContext.getBean("pcompSearchService");
        System.out.println(pcompSearchService.getAllRelated("Java", SortRule.BY_CORRELATION));
    }
}
