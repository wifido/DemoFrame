package com.sf.sfpp.resource.client.file;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/23
 */
public interface FileService {
    /**
     * 保存文件
     *
     * @param file
     * @return 文件路径
     */
    String saveFile(CommonsMultipartFile file) throws IOException;

    /**
     * 查出对应路径的文件，需要将URL转换成本地路径查出来
     *
     * @param fileId
     * @return
     */
    String getFile(String fileId);

    /**
     * 删除对应路径的文件，需要将URL转换成本地路径查出来如果存在就删除
     *
     * @param fileId
     */
    void deleteFile(String fileId);
}
