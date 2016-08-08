package com.sf.demo;

import org.springframework.stereotype.Service;
import scala.collection.mutable.StringBuilder;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/8
 */
@Service
public class DemoImpl implements Demo {
    public void printObject(DemoObject demoObject) {
        System.out.println(new StringBuilder().append(demoObject.getB()).append(demoObject.getA()));
    }
}
