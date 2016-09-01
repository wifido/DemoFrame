package com.sf.sfpp.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/25
 */
public class ExceptionUtils {
    /**
     * 获取异常堆栈信息，用于不同日志实现的异常堆栈打印（slf4j）
     * @param e
     * @return
     */
    public static String getStackTrace(Exception e){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) ObjectUtils.getObjectSize(e));
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        e.printStackTrace(printStream);
        return byteArrayOutputStream.toString();
    }

//    public static void main(String[] args) {
//        try {
//            throw new Exception("asd");
//        } catch (Exception e) {
//            System.out.println(getStackTrace(e));
//        }
//    }
}
