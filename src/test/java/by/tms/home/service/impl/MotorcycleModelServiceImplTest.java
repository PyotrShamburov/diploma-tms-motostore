package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.motorcycle.Brand;
import by.tms.home.entity.motorcycle.ModelDTO;
import by.tms.home.entity.motorcycle.MotorcycleModel;
import by.tms.home.repository.motorcycle.MotorcycleModelRepository;
import by.tms.home.service.BrandService;
import by.tms.home.service.MotorcycleModelService;
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
class MotorcycleModelServiceImplTest {

    @Autowired
    private MotorcycleModelService modelService;
    @Autowired
    private MotorcycleModelRepository modelRepository;
    @Autowired
    private BrandService brandService;
    private MotorcycleModel expectedModel;
    private Brand brand;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1);
        brand.setNameOfBrand("BMW");
        expectedModel = new MotorcycleModel(1, "GS", brand);
    }

    @Test
    @Order(1)
    void addToStorage() {
        brandService.addToStorage(brand);
        modelService.addToStorage(expectedModel);
        Optional<MotorcycleModel> byId = (Optional<MotorcycleModel>) modelRepository.findById(expectedModel.getId());
        MotorcycleModel actualModel = null;
        if (byId.isPresent()) {
            actualModel = byId.get();
        }
        assertEquals(expectedModel, actualModel);

    }

    @Test
    @Order(2)
    void deleteMotorcycleModelById() {
        boolean actualResult = (boolean) modelService.deleteMotorcycleModelById(expectedModel.getId());
        assertTrue(actualResult);
    }

    @Test
    @Order(3)
    void containsMotorcycleModelWithSameName() {
        modelService.addToStorage(expectedModel);
        Throwable thrown = assertThrows(EntityAlreadyExistException.class, ()->
                modelService.containsMotorcycleModelWithSameName(expectedModel.getNameOfModel()));
        assertNotNull(thrown.getMessage());
    }

    @Test
    @Order(4)
    void getMotorcycleModelByName() {
        MotorcycleModel actualModel = (MotorcycleModel) modelService.getMotorcycleModelByName(expectedModel.getNameOfModel());
        expectedModel.setId(2);
        assertEquals(expectedModel, actualModel);
    }

    @Test
    @Order(5)
    void createModelFromModelDTO() {
        MotorcycleModel expectedFromDTO = new MotorcycleModel(0, "GS", brand);
        ModelDTO modelDTO = new ModelDTO(1, "GS");
        MotorcycleModel actualModel = (MotorcycleModel) modelService.createModelFromModelDTO(new MotorcycleModel(), modelDTO);
        assertEquals(expectedFromDTO, actualModel);

    }

    @Test
    @Order(6)
    void getModelsByBrandId() {
        expectedModel.setId(2);
        List<MotorcycleModel> modelsByBrandId = (List<MotorcycleModel>) modelService.getModelsByBrandId(brand.getId());
        boolean actualContains = (boolean) modelsByBrandId.contains(expectedModel);
        assertTrue(actualContains);
    }
}