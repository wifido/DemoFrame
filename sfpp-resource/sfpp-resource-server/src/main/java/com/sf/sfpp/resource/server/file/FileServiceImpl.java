package com.sf.sfpp.resource.server.file;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.common.utils.FileUtils;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.resource.client.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
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
    private final static Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${global.file.address.root}")
    private String globalRoot;
    @Value("${local.file.address.root}")
    private String localRoot;

    private String getLocalPath(String ID) {
        return StrUtils.makeString(localRoot,
                ID.substring(0 + IDGenerator.SYSTEM_SHARD_LENGTH, 0 + IDGenerator.SYSTEM_SHARD_LENGTH + IDGenerator.SYSTEM_SHARD_LENGTH - 1),
                Constants.FOLDER_PATH_SEPARATOR,
                ID.substring(0, 0 + IDGenerator.SYSTEM_SHARD_LENGTH),
                Constants.FOLDER_PATH_SEPARATOR,
                ID);
    }

    private String getGlobalPath(String ID) {
        return StrUtils.makeString(globalRoot,
                ID.substring(0 + IDGenerator.SYSTEM_SHARD_LENGTH, 0 + IDGenerator.SYSTEM_SHARD_LENGTH + IDGenerator.SYSTEM_SHARD_LENGTH - 1),
                Constants.FOLDER_PATH_SEPARATOR,
                ID.substring(0, 0 + IDGenerator.SYSTEM_SHARD_LENGTH),
                Constants.FOLDER_PATH_SEPARATOR,
                ID);
    }

    private String globalPathToLocalPath(String globalPath) {
        return StrUtils.makeString(localRoot,
                globalPath.substring(globalPath.indexOf(globalRoot) + globalRoot.length()));
    }

    @Override
    public String saveFile(String originalName, InputStream inputStream) throws IOException {
        String Id = StrUtils.makeString(IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM), originalName);
        FileOutputStream fileOutputStream = new FileOutputStream(FileUtils.getFile(getLocalPath(Id)));
        int b = 0;
        while ((b = inputStream.read()) != -1) {
            fileOutputStream.write(b);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        inputStream.close();
        log.info("{} is saved, local path:[{}]", originalName, getLocalPath(Id));
        return getGlobalPath(Id);
    }

    @Override
    public void deleteFile(String globalPath) {
        String localPath = globalPathToLocalPath(globalPath);
        File file = new File(localPath);
        if(file.isFile() && file.exists()) {
            file.delete();
            log.info("{} is deleted!",globalPath);
        } else {
            log.info("{} does not exist or is not a file!",globalPath);
        }
    }

}
