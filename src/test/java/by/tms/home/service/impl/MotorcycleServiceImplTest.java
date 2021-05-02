package by.tms.home.service.impl;

import by.tms.home.entity.motorcycle.*;
import by.tms.home.repository.motorcycle.MotorcycleRepository;
import by.tms.home.service.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MotorcycleServiceImplTest {

    @Autowired
    private MotorcycleService motorcycleService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MotorcycleModelService motorcycleModelService;
    @Autowired
    private DriveTypeService driveTypeService;
    @Autowired
    private EngineTypeService engineTypeService;
    @Autowired
    private MotorcycleConditionService conditionService;
    @Autowired
    private MotorcycleTypeService typeService;
    private Motorcycle motorcycle;
    private MotorcycleCondition condition;
    private MotorcycleType motorcycleType;
    private EngineType engineType;
    private DriveType driveType;
    private Brand brand;
    private MotorcycleModel motorcycleModel;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1);
        brand.setNameOfBrand("BMW");
        motorcycleModel = new MotorcycleModel(1, "GS", brand);
        condition = new MotorcycleCondition(1, "NEW");
        engineType = new EngineType(1, "ROW");
        driveType = new DriveType(1, "CHAIN");
        motorcycleType = new MotorcycleType(1, "SPORT");
        motorcycle = new Motorcycle(1,brand , motorcycleModel, motorcycleType,
                2001, condition, 4, engineType, 600, driveType, 34000);
    }

    @Test
    @Order(1)
    void saveMotorcycle() {
        brandService.addToStorage(brand);
        motorcycleModelService.addToStorage(motorcycleModel);
        driveTypeService.addToStorage(driveType);
        engineTypeService.addToStorage(engineType);
        conditionService.addToStorage(condition);
        typeService.addToStorage(motorcycleType);
        Motorcycle actualMotorcycle = (Motorcycle) motorcycleService.saveMotorcycle(motorcycle);
        assertEquals(motorcycle, actualMotorcycle);
    }

    @Test
    @Order(2)
    void setInfoFieldsFromDTO() {
        MotorcycleDTO motorcycleDTO = new MotorcycleDTO("BMW", "GS", 1,
                2001, 1, 4, 1, 600, 1, 34000);
        Motorcycle actualMoto = new Motorcycle();
        actualMoto.setId(1);
        actualMoto.setBrand(brand);
        actualMoto.setModel(motorcycleModel);
        Motorcycle actualMotoFromDTO = (Motorcycle) motorcycleService.setInfoFieldsFromDTO(actualMoto, motorcycleDTO);
        assertEquals(motorcycle, actualMotoFromDTO);


    }
}