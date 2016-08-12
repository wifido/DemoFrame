package com.sf.sfpp.resource.server.image;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.ImageObject;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.common.utils.ImageUtils;
import com.sf.sfpp.resource.client.image.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/10
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${global.address.root}")
    private String globalRoot;
    @Value("${local.address.root}")
    private String localRoot;

    private String getLocalPath(String ID){
        return new StringBuilder().append(localRoot)
                .append(ID.substring(0+ IDGenerator.SYSTEM_SHARD_LENGTH,0+ IDGenerator.SYSTEM_SHARD_LENGTH+IDGenerator.SYSTEM_SHARD_LENGTH))
                .append(Constants.FOLDER_PATH_SEPARATOR)
                .append(ID.substring(0,0+ IDGenerator.SYSTEM_SHARD_LENGTH))
                .append(Constants.FOLDER_PATH_SEPARATOR)
                .append(ID)
                .append(Constants.FILE_SEPARATOR)
                .append(ImageUtils.imageFormat)
                .toString();
    }

    private String getGlobalPath(String ID){
        return new StringBuilder().append(globalRoot)
                .append(ID.substring(0+ IDGenerator.SYSTEM_SHARD_LENGTH,0+ IDGenerator.SYSTEM_SHARD_LENGTH+IDGenerator.SYSTEM_SHARD_LENGTH))
                .append(Constants.FOLDER_PATH_SEPARATOR)
                .append(ID.substring(0,0+ IDGenerator.SYSTEM_SHARD_LENGTH))
                .append(Constants.FOLDER_PATH_SEPARATOR)
                .append(ID)
                .append(Constants.FILE_SEPARATOR)
                .append(ImageUtils.imageFormat)
                .toString();
    }

    public String saveImage(ImageObject imageObject) throws IOException {
        ImageUtils.decodeBase64ToImage(imageObject.getImageContent(),getLocalPath(imageObject.getImageID()));
        return getGlobalPath(imageObject.getImageID());
    }

    public String getImage(String imagePath) {
        return null;
    }

    public void deleteImage(String imagePath) {

    }
}
