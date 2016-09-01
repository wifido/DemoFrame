package com.sf.sfpp.resource.client.file;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/23
 */
public interface FileService {
    /**
     * 保存文件
     *
     * @param originalName
     * @param inputStream
     * @return 文件路径
     */
    String saveFile(String originalName, InputStream inputStream) throws IOException;


    /**
     * 删除对应路径的文件，需要将URL转换成本地路径查出来如果存在就删除
     *
     * @param globalPath
     */
    void deleteFile(String globalPath);
}
