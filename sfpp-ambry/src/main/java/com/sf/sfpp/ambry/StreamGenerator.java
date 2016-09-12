package com.sf.sfpp.ambry;

import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.idgen.IDGenerator;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author wujiang
 * @version 1.0.0.
 * @date 2016/9/12
 */
public class StreamGenerator {

    private static File file;

    public StreamGenerator(File file){
        this.file = file;
    }
    /**
     * 压缩传入的文件，返回流
     * @return inputstream
     */
    public static InputStream getInputStream() {

        //zip存放主路径
        String tempFilePath = "D:/Program Files (x86)/apache-tomcat-7.0.70/temp/temp1/";

        String zipName =  IDGenerator.getID(Constants.PUBLIC_COMPONENT_SYSTEM);
        //String zipName = "1234";
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
