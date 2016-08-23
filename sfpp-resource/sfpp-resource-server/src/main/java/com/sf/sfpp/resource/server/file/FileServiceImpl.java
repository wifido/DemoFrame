package com.sf.sfpp.resource.server.file;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.resource.client.file.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/23
 */
@Service
public class FileServiceImpl implements FileService {
    @Value("${global.file.address.root}")
    private String globalRoot;
    @Value("${local.file.address.root}")
    private String localRoot;

    private String getLocalPath(String ID) {
        return new StringBuilder().append(localRoot)
                .append(ID.substring(0 + IDGenerator.SYSTEM_SHARD_LENGTH, 0 + IDGenerator.SYSTEM_SHARD_LENGTH + IDGenerator.SYSTEM_SHARD_LENGTH - 1))
                .append(Constants.FOLDER_PATH_SEPARATOR)
                .append(ID.substring(0, 0 + IDGenerator.SYSTEM_SHARD_LENGTH))
                .append(Constants.FOLDER_PATH_SEPARATOR)
                .append(ID)
                .toString();
    }

    private String getGlobalPath(String ID) {
        return new StringBuilder().append(globalRoot)
                .append(ID.substring(0 + IDGenerator.SYSTEM_SHARD_LENGTH, 0 + IDGenerator.SYSTEM_SHARD_LENGTH + IDGenerator.SYSTEM_SHARD_LENGTH - 1))
                .append(Constants.FOLDER_PATH_SEPARATOR)
                .append(ID.substring(0, 0 + IDGenerator.SYSTEM_SHARD_LENGTH))
                .append(Constants.FOLDER_PATH_SEPARATOR)
                .append(ID)
                .toString();
    }

    @Override
    public String saveFile(CommonsMultipartFile file) throws IOException {
        String Id = StrUtils.makeString(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM), file.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(getLocalPath(Id));
        InputStream inputStream = file.getInputStream();
        int b = 0;
        while ((b = inputStream.read()) != -1) {
            fileOutputStream.write(b);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
        return getGlobalPath(Id);
    }

    @Override
    public String getFile(String filePath) {
        return null;
    }

    @Override
    public void deleteFile(String filePath) {

    }
}
