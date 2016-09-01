package com.sf.sfpp.common.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/24
 */
public class FileUtils {
    public static File getFile(String path) throws IOException {
        File file1 = new File(path);
        if(file1.exists()){
            throw new IOException("文件已存在！  ");
        }
        if(!file1.getParentFile().exists()) {
            if(!file1.getParentFile().mkdirs()) {
                throw new IOException("文件目录创建失败  ");
            }
        }
        file1.createNewFile();
        return file1;
    }
}
