package by.tms.home.service;

import by.tms.home.entity.Announcement;
import by.tms.home.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image addImageToDatabase(Image image);
    void deleteImageById(long id);
    Image createImageFromFileAndSetAd(Announcement announcement, MultipartFile multipartFile);
}
