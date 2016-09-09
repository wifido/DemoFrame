package com.sf.sfpp.ambry;

import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @author Hash Zhang,wujiang
 * @version 1.0.0
 * @date 2016/9/8
 */
public class HTTPUpload {
    private static final Logger log = LoggerFactory.getLogger(HTTPUpload.class);
    private final String ambryUrl;

    public HTTPUpload(final String ambryUrl) {
        this.ambryUrl = ambryUrl;
    }

    /**
     * 压缩传入的文件，返回流
     * @param file
     * @return inputstream
     */
    public static InputStream getInputStream(File file) {

        //zip存放主路径
        String tempFilePath = "C:/content/";

        //生成的压缩包名称
        String zipName = "ZipFile";

        /**
         * 压缩
         */
        byte[] buffer = new byte[4096];
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(tempFilePath + zipName + ".zip"));
            FileInputStream fileInput = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file.getName()));
            int temp = 0;
            //读入需要下载的文件的内容，打包到zip文件
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


    public String uploadFile(InputStream inputStream, long size, String ServiceId, String owner, String fileFormat, String description) {
        HttpURLConnection connection = null;
        OutputStream os = null;
        DataInputStream is = null;
        int count = 0;
        while (count <= 3) {
            try {
                connection = (HttpURLConnection) new URL(ambryUrl).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/octet-stream");
                //设置是否从httpUrlConnection读入，默认情况下是true;
                connection.setDoOutput(true);
                //设置是否向httpUrlConnection输出,默认为false
                connection.setDoInput(true);
                connection.setRequestProperty("x-ambry-blob-size", size + "");
                connection.addRequestProperty("x-ambry-service-id", ServiceId);
                connection.addRequestProperty("x-ambry-owner-id", owner);
                connection.addRequestProperty("x-ambry-content-type", fileFormat);
                connection.addRequestProperty("x-ambry-um-description", description);
                connection.connect();

                os = new BufferedOutputStream(connection.getOutputStream());

                byte[] buffer = new byte[4096];
                int bytes_read;
                //只要可以读取到数据，就输出写到buffer中
                while ((bytes_read = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, bytes_read);
                }
                //数据读取完关闭inputStream
                os.close();
                inputStream.close();
                String location = connection.getHeaderField("Location");
                return StrUtils.makeString(ambryUrl, location);
            } catch (Exception e) {
                count++;
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e1) {
                }
                log.warn(ExceptionUtils.getStackTrace(e));
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    log.warn(ExceptionUtils.getStackTrace(e));
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    log.warn(ExceptionUtils.getStackTrace(e));
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException {
        HTTPUpload httpUpload = new HTTPUpload("http://10.202.7.85:1174");
        File file = new File("D:/diagram7.png");
        InputStream inputStream = httpUpload.getInputStream(file);
        System.out.println(httpUpload.uploadFile(inputStream, file.length(), "", "", "", ""));
    }
}
