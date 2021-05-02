package by.tms.home.service.impl;

import by.tms.home.entity.Image;
import by.tms.home.repository.ImageRepository;
import by.tms.home.service.ImageService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ImageServiceImplTest {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;
    private Image image;

    @BeforeEach
    void setUp() {
        image = new Image(1, "url", null);
    }

    @Test
    @Order(1)
    void addImageToDatabase() {
        Image actualImage = (Image) imageService.addImageToDatabase(image);
        assertEquals(image, actualImage);
    }

    @Test
    @Order(2)
    void deleteImageById() {
        imageService.deleteImageById(image.getId());
        boolean actualExist = (boolean) imageRepository.existsById(image.getId());
        assertFalse(actualExist);
    }

}