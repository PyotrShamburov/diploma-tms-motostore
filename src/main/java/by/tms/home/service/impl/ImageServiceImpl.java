package by.tms.home.service.impl;

import by.tms.home.entity.Announcement;
import by.tms.home.entity.Image;
import by.tms.home.repository.ImageRepository;
import by.tms.home.service.CloudinaryService;
import by.tms.home.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CloudinaryService cloudinaryService;


    @Override
    public Image addImageToDatabase(Image image) {
        log.info("New saved image: "+image);
        return imageRepository.save(image);
    }

    @Override
    public void deleteImageById(long id) {
        log.info("Image deleted! Id :"+id);
        imageRepository.deleteById(id);
    }

    @Override
    public Image createImageFromFileAndSetAd(Announcement announcement, MultipartFile multipartFile) {
        Image image = (Image) cloudinaryService.uploadFile(multipartFile);
        log.info("New image from file: "+image);
        image.setAnnouncement(announcement);
        return addImageToDatabase(image);
    }

}
