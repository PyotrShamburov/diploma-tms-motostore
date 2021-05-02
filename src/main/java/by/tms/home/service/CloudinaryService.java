package by.tms.home.service;

import by.tms.home.entity.Image;
import ch.qos.logback.core.joran.spi.SimpleRuleStore;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinaryConfig;

    public Image uploadFile(MultipartFile file) {
        Image image = new Image();
        try {
            String base64 = convertMultiPartToBaseString(file);
           Map uploadResult = cloudinaryConfig.uploader().upload("data:"+file.getContentType()+";base64,"+base64, ObjectUtils.emptyMap());
            String url = uploadResult.get("url").toString();
//            String url = "https://res.cloudinary.com/dh6qfegkp/image/upload/v1619520454/ghcnoqxs7vtvdxhk3v1m.jpg";
            image.setUrl(url);
            return image;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String convertMultiPartToBaseString(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public Image getDefaultImage() {
        String urlOfDefaultPhoto = "https://res.cloudinary.com/dh6qfegkp/image/upload/v1619700112/noimage_file_lbwhsc.jpg";
        Image defaultImage = new Image();
        defaultImage.setUrl(urlOfDefaultPhoto);
        return defaultImage;
    }
}
