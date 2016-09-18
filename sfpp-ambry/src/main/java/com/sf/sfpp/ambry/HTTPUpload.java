package com.sf.sfpp.ambry;

import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.StrUtils;
import it.sauronsoftware.cron4j.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/9/1
 */
public class HTTPUpload {
    private static final Logger log = LoggerFactory.getLogger(HTTPUpload.class);
    private final String ambryUrl;
    private final String tmpFilePath;

    public HTTPUpload(final String ambryUrl) {
        this.ambryUrl = ambryUrl;
        this.tmpFilePath = StrUtils.makeString(HTTPUpload.class.getResource("").getFile().toString(),"tmp");
        File file1 = new File(tmpFilePath);
        if(!file1.exists()){
            file1.mkdirs();
        }
        Scheduler scheduler = new Scheduler();
        scheduler.schedule("50 13 * * *", new DeleteTask(tmpFilePath));
    }

    public String uploadFile(MultipartFile file1, long size, String ServiceId, String owner, String fileFormat,
            String description) {
        HttpURLConnection connection = null;
        OutputStream os = null;
        DataInputStream is = null;
        InputStream inputStream = null;
        int count = 0;
        while (count <= 3) {
            try {
                File file = new File(StrUtils.makeString(tmpFilePath,"/",file1.getOriginalFilename()));
                file1.transferTo(file);
                if (!fileFormat.equals("image")) {
                    inputStream = new StreamGenerator(file, tmpFilePath).getInputStream();
                } else {
                    inputStream = new FileInputStream(file);
                }
                int length = inputStream.available();
                connection = (HttpURLConnection) new URL(ambryUrl).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/octet-stream");
                //设置是否从httpUrlConnection读入，默认情况下是true;
                connection.setDoOutput(true);
                //设置是否向httpUrlConnection输出,默认为false
                connection.setDoInput(true);
                connection.setRequestProperty("x-ambry-blob-size", length + "");
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
//        HTTPUpload httpUpload = new HTTPUpload("http://10.202.7.85:1174");
//        File file = new File("D:/diagram7.png");
//        final String tempFilePath = "D:/Program Files (x86)/temp/temp1/";
////        InputStream inputStream = new StreamGenerator(file, tempFilePath).getInputStream();
////                System.out.println(httpUpload.uploadFile(file, file.length(), "", "", "", ""));
//
//        Scheduler scheduler = new Scheduler();
//        scheduler.schedule("28 20 * * *", new DeleteTask(tempFilePath));
//        scheduler.start();
        String rootPath=HTTPUpload.class.getResource("").getFile().toString();
        System.out.println(rootPath);
    }
}
