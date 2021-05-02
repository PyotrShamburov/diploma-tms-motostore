package by.tms.home.service;

import by.tms.home.entity.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudinaryServiceTest {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Test
    void getDefaultImage() {
        String urlOfDefaultPhoto = "https://res.cloudinary.com/dh6qfegkp/image/upload/v1619700112/noimage_file_lbwhsc.jpg";
        Image expectedImage = new Image(0, urlOfDefaultPhoto, null);
        Image actualImage = (Image) cloudinaryService.getDefaultImage();
        assertEquals(expectedImage, actualImage);
    }
}