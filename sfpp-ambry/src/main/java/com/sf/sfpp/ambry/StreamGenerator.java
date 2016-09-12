package com.sf.sfpp.ambry;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.idgen.IDGenerator;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author wujiang
 * @version 1.0.0.
 * @date 2016/9/9
 */
public class StreamGenerator {

    private static File file;
    private static String tempFilePath;

    public StreamGenerator(File file,String tempFilePath){
        this.file = file;
        this.tempFilePath = tempFilePath;
    }

    /**
     * 压缩传入的文件，返回流
     * @return inputstream
     */
    public static InputStream getInputStream() {

        String zipName =  IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM);

        //判断是否有文件夹，如果没有就创建
        File tempF = new File(tempFilePath);

        if(!tempF.exists()){
            tempF.mkdirs();
        }

        /**
         * 压缩并在指定目录生成临时zip文件
         */
        byte[] buffer = new byte[4096];
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(tempFilePath + zipName + ".zip"));
            FileInputStream fileInput = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file.getName()));
            int temp = 0;
            //读取文件并打包
            while ((temp = fileInput.read(buffer)) > 0) {
                out.write(buffer, 0, temp);
            }
            out.closeEntry();
            fileInput.close();
            out.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }

        /**
         * 把压缩包读取为输出流
         */
        File filezip = new File(tempFilePath + zipName + ".zip");
        ByteArrayOutputStream byteOut = null;
        try {
            byteOut = new ByteArrayOutputStream();
            FileInputStream FileIn = new FileInputStream(filezip);
            BufferedInputStream bufferInput = new BufferedInputStream(FileIn);
            int k = bufferInput.read();
            while (k != -1) {
                byteOut.write(k);
                k = bufferInput.read();
            }
            bufferInput.close();
            FileIn.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        /**
         * 输出流转为uploadFile方法的输入流
         */
        ByteArrayInputStream in = new ByteArrayInputStream(byteOut.toByteArray());
        return in;
    }
}
