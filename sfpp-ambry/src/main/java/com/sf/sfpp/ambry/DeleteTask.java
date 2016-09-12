package com.sf.sfpp.ambry;

import java.io.File;
/**
 * @author wujiang
 * @version 1.0.0.
 * @date 2016/9/9
 */
public class DeleteTask implements Runnable {

    public static void DeleteAll(File f){
        for(File fi:f.listFiles()){
            if(fi.isDirectory()){
                DeleteAll(fi);
            }
            else{
                fi.delete();
            }
        }
        f.delete();
    }

    @Override
    public void run() {
        File f = new File("D:/Program Files (x86)/temp/temp1/");
        DeleteAll(f);
    }
}
