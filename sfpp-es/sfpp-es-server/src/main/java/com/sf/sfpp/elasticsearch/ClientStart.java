package com.sf.sfpp.elasticsearch;

import com.sf.sfpp.elasticsearch.pcomp.PcompSearchService;
import com.sf.sfpp.elasticsearch.pcomp.SortRule;
import com.sf.sfpp.pcomp.common.exception.PcompException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 这个仅仅是测试用
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/18
 */
public class ClientStart {
    public static void main(String[] args) throws PcompException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        PcompSearchService pcompSearchService = (PcompSearchService) applicationContext.getBean("pcompSearchService");
        System.out.println(pcompSearchService.getAllRelated("Java", SortRule.BY_CORRELATION));
    }
}
