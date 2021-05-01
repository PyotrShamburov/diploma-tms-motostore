package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.motorcycle.Brand;
import by.tms.home.entity.motorcycle.MotorcycleModel;
import by.tms.home.repository.motorcycle.BrandRepository;
import by.tms.home.service.BrandService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BrandServiceImplTest {

    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandRepository brandRepository;
    private Brand expectedBrand;

    @BeforeEach
    void setUp(){
        List<MotorcycleModel> models = new ArrayList<>();
        models.add(Mockito.mock(MotorcycleModel.class));
        models.add(Mockito.mock(MotorcycleModel.class));
        expectedBrand = new Brand(1,"BMW", models);
    }

    @Test
    @Order(1)
    void addToStorage() {
        brandService.addToStorage(expectedBrand);
        Optional<Brand> byId = (Optional<Brand>) brandRepository.findById(expectedBrand.getId());
        Brand actualBrand = null;
        if (byId.isPresent()) {
            actualBrand = byId.get();
        }
        assertEquals(expectedBrand, actualBrand);
    }

    @Test
    @Order(2)
    void getBrandById() {
        Brand actualBrand = (Brand) brandService.getBrandById(expectedBrand.getId());
        assertEquals(expectedBrand, actualBrand);
    }

    @Test
    @Order(3)
    void deleteBrandById() {
        boolean actualResult = (boolean) brandService.deleteBrandById(expectedBrand.getId());
        assertTrue(actualResult);
    }

    @Test
    @Order(4)
    void containsBrandWithSameName() {
        brandService.addToStorage(expectedBrand);
        Throwable thrown = assertThrows(EntityAlreadyExistException.class, ()->
                brandService.containsBrandWithSameName(expectedBrand.getNameOfBrand()));
        assertNotNull(thrown.getMessage());
    }

    @Test
    @Order(5)
    void getByNameOfBrand() {
        expectedBrand.setId(2);
        Brand actualBrand = (Brand) brandService.getByNameOfBrand(expectedBrand.getNameOfBrand());
        assertEquals(expectedBrand, actualBrand);
    }

    @Test
    @Order(6)
    void getAllBrands() {
        int expectedSize = 1;
        int actualSize = (int) brandService.getAllBrands().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Order(7)
    void getCountOfAllBrands() {
        int expectedCount = 1;
        int actualCount = (int) brandService.getCountOfAllBrands();
        assertEquals(expectedCount, actualCount);
    }
}