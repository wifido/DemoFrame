package com.sf.sfpp.schedule;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/30
 */
@Component
public class TestProcess {
    public void doJob(){
        System.out.println(new Date());
    }
}
