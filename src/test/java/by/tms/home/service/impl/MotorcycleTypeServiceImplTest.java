package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.motorcycle.MotorcycleType;
import by.tms.home.repository.motorcycle.MotorcycleTypeRepository;
import by.tms.home.service.MotorcycleTypeService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MotorcycleTypeServiceImplTest {

    @Autowired
    private MotorcycleTypeService typeService;
    @Autowired
    private MotorcycleTypeRepository typeRepository;

    @Test
    @Order(1)
    void addToStorage() {
        MotorcycleType expectedType = new MotorcycleType(1, "SPORT");
        typeService.addToStorage(expectedType);
        MotorcycleType actualType = null;
        Optional<MotorcycleType> byId = (Optional<MotorcycleType>) typeRepository.findById(expectedType.getId());
        if (byId.isPresent()) {
            actualType = (MotorcycleType) byId.get();
        }
        assertEquals(expectedType, actualType);
    }

    @Test
    @Order(2)
    void getMotorcycleTypeById() {
        MotorcycleType expectedType = new MotorcycleType(1, "SPORT");
        MotorcycleType actualType = (MotorcycleType) typeService.getMotorcycleTypeById(expectedType.getId());
        assertEquals(expectedType, actualType);
    }

    @Test
    @Order(3)
    void deleteMotorcycleTypeById() {
        MotorcycleType expectedType = new MotorcycleType(1, "SPORT");
        boolean actualResult = (boolean) typeService.deleteMotorcycleTypeById(expectedType.getId());
        assertTrue(actualResult);
    }

    @Test
    @Order(4)
    void containsMotorcycleTypeWithSameName() {
        MotorcycleType expectedType = new MotorcycleType(2, "STREET");
        typeService.addToStorage(expectedType);
        Throwable thrown = assertThrows(EntityAlreadyExistException.class, ()->
                typeService.containsMotorcycleTypeWithSameName(expectedType.getNameOfMotorcycleType()));
        assertNotNull(thrown.getMessage());
    }

    @Test
    @Order(5)
    void getAllMotoTypes() {
        int expectedSize = 1;
        int actualSize = (int) typeService.getAllMotoTypes().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Order(6)
    void getCountOfAllTypes() {
        int expectedAmount = 1;
        int actualAmount = (int) typeService.getCountOfAllTypes();
        assertEquals(expectedAmount, actualAmount);
    }
}