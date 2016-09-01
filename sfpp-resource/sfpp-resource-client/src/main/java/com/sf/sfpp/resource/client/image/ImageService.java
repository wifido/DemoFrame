package com.sf.sfpp.resource.client.image;

import com.sf.sfpp.common.domain.ImageObject;

import java.io.IOException;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/9
 */
public interface ImageService {
    /**
     * 保存图像，需要解码输出图像
     * @param imageObject
     * @return
     */
    String saveImage(ImageObject imageObject) throws IOException;
    /**
     * 删除对应路径的图像，需要将URL转换成本地路径查出来如果存在就删除
     * @param globalPath
     */
    void deleteImage(String globalPath);
}
