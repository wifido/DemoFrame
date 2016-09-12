package com.sf.sfpp.ambry;

import java.io.File;


/**
 * @author wujiang
 * @version 1.0.0.
 * @date 2016/9/9
 */
public class DeleteTask implements Runnable {

    private String tempFilePath;

    public DeleteTask(String tempFilePath){
        this.tempFilePath = tempFilePath;
    }

    public static void DeleteAll(File file){
        for(File fi:file.listFiles()){
            if(fi.isDirectory()){
                DeleteAll(fi);
            }
            else{
                fi.delete();
            }
        }
        file.delete();
    }

    @Override
    public void run(){
        File file = new File(tempFilePath);
        DeleteAll(file);
    }
}
