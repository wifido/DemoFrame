package com.sf.sfpp.ambry;

import com.sf.sfpp.common.utils.ExceptionUtils;
import com.sf.sfpp.common.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    public HTTPUpload(final String ambryUrl) {
        this.ambryUrl = ambryUrl;
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
                connection.setDoOutput(true);
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
                while ((bytes_read = inputStream.read(buffer)) != -1) {
                    os.write(buffer, 0, bytes_read);
                }
                os.close();
                inputStream.close();
                String location = connection.getHeaderField("Location");
                return StrUtils.makeString(ambryUrl,location);
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
        System.out.println(httpUpload.uploadFile(new FileInputStream(file),file.length(),"","","",""));
    }
}
