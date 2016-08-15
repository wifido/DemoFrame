package com.sf.sfpp.web.controller.pcomp;

import com.alibaba.fastjson.JSON;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.domain.ImageObject;
import com.sf.sfpp.common.idgen.IDGenerator;
import com.sf.sfpp.common.utils.ImageKind;
import com.sf.sfpp.common.utils.ImageUtils;
import com.sf.sfpp.resource.client.image.ImageService;
import com.sf.sfpp.web.common.PagePathConstants;
import com.sf.sfpp.web.common.editormd.domain.ImageUploadReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/10
 */
@Controller
public class ImageController {
    private final static Logger log = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    @RequestMapping(PagePathConstants.PCOMP_SOFTWARE_CONTENT_IMAGE_UPLOAD_PATH)
    public void upload(@RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageUploadReturn imageUploadReturn = new ImageUploadReturn();

        String imgageToBase64 = null;
        try {
            imgageToBase64 = ImageUtils.encodeImgageToBase64(ImageUtils.intelligentZip(file.getInputStream(), ImageKind.CONTENT_IMAGE));
        } catch (IOException e) {
            imageUploadReturn.setSuccess(ImageUploadReturn.FAIL);
            imageUploadReturn.setMessage(e.getMessage());
            response.getWriter().write(JSON.toJSONString(imageUploadReturn));
            e.printStackTrace();
            return;
        }
        ImageObject imageObject = getImageObject(Constants.PUBLIC_COMPONENT_SYSTEM, imgageToBase64);
        try {
            imageUploadReturn.setUrl(imageService.saveImage(imageObject));
        } catch (IOException e) {
            imageUploadReturn.setSuccess(ImageUploadReturn.FAIL);
            imageUploadReturn.setMessage("Resource service has exception:" + e.getMessage());
            response.getWriter().write(JSON.toJSONString(imageUploadReturn));
            e.printStackTrace();
            return;
        }
        imageUploadReturn.setSuccess(ImageUploadReturn.SUCCESS);
        response.getWriter().write(JSON.toJSONString(imageUploadReturn));
    }

    private static ImageObject getImageObject(String sys, String imgageToBase64) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageID(IDGenerator.getID(sys));
        imageObject.setImageContent(imgageToBase64);
        return imageObject;
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        ImageService imageService = applicationContext.getBean(ImageService.class);
        FileInputStream fis = new FileInputStream("D:\\sfpp-stat-res\\Java1.jpg");
        String imgageToBase64 = ImageUtils.encodeImgageToBase64(ImageUtils.intelligentZip(fis, ImageKind.AVATAR));
        ImageObject imageObject = getImageObject(Constants.PUBLIC_COMPONENT_SYSTEM, imgageToBase64);
        System.out.println(imageService.saveImage(imageObject));
//        fis = new FileInputStream("D:\\sfpp-stat-res\\221025159213722.jpg");
//        imgageToBase64 = ImageUtils.encodeImgageToBase64(ImageUtils.intelligentZip(fis, ImageKind.TOP_PHOTO));
//        imageObject = getImageObject(Constants.PUBLIC_COMPONENT_SYSTEM, imgageToBase64);
//        System.out.println(imageService.saveImage(imageObject));
    }
}
