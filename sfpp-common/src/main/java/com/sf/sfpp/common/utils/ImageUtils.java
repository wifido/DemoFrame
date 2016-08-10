package com.sf.sfpp.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/9
 */
public class ImageUtils {
    public final static String imageFormat = "png";

    private final static int AVATAR_WIDTH = 50;
    private final static int AVATAR_HEIGHT = 50;

    private final static int CONTENT_WIDTH = 800;
    private final static int CONTENT_HEIGHT = 600;
    private final static int WIDTH_HEIGHT_RATIO = 2;


    public final static ByteArrayOutputStream intelligentZip(InputStream srcImageFile, ImageKind imageKind) throws IOException {
        BufferedImage src = ImageIO.read(srcImageFile); // 读入文件
        int width = src.getWidth(); // 得到源图宽
        int height = src.getHeight(); // 得到源图长
        Image image = null;
        BufferedImage tag = null;
        switch (imageKind) {
            case AVATAR:
                image = src.getScaledInstance(AVATAR_WIDTH, AVATAR_HEIGHT,
                        Image.SCALE_DEFAULT);
                tag = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                break;
            case CONTENT_IMAGE:
//                int scale = Math.min(width / CONTENT_WIDTH, height / CONTENT_HEIGHT);
                int scale = 1;
                if (scale > 0) {
                    width = width / scale;
                    height = height / scale;
                }
            default:
                image = src.getScaledInstance(width, height,
                        Image.SCALE_DEFAULT);
                tag = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
        }
        Graphics g = tag.getGraphics();
        g.drawImage(image, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ImageIO.write(tag, imageFormat, byteOutputStream);// 输出到文件流
        return byteOutputStream;
    }

    public static String encodeImgageToBase64(ByteArrayOutputStream outputStream) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

    public static void decodeBase64ToImage(String base64, String file) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        File file1 = new File(file);
        if(file1.exists()){
            throw new IOException("文件已存在！  ");
        }
        if(!file1.getParentFile().exists()) {
            if(!file1.getParentFile().mkdirs()) {
                throw new IOException("文件目录创建失败  ");
            }
        }
        file1.createNewFile();
        FileOutputStream write = new FileOutputStream(file1);
        byte[] decoderBytes = decoder.decodeBuffer(base64);
        write.write(decoderBytes);
        write.close();
    }
}

