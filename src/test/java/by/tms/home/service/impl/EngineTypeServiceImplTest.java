package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.motorcycle.EngineType;
import by.tms.home.repository.motorcycle.EngineTypeRepository;
import by.tms.home.service.EngineTypeService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.metamodel.EntityType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EngineTypeServiceImplTest {

    @Autowired
    private EngineTypeService engineTypeService;
    @Autowired
    private EngineTypeRepository typeRepository;

    @Test
    @Order(1)
    void addToStorage() {
        EngineType expectedType = new EngineType(1, "ROW");
        engineTypeService.addToStorage(expectedType);
        EngineType actualType = null;
        Optional<EngineType> byId = (Optional<EngineType>) typeRepository.findById(expectedType.getId());
        if (byId.isPresent()) {
             actualType = (EngineType) byId.get();
        }
        assertEquals(expectedType, actualType);

    }

    @Test
    @Order(2)
    void getEngineTypeById() {
        EngineType expectedType = new EngineType(1, "ROW");
        EngineType actualType = (EngineType) engineTypeService.getEngineTypeById(expectedType.getId());
        assertEquals(expectedType, actualType);
    }

    @Test
    @Order(3)
    void deleteEngineTypeById() {
        EngineType expectedType = new EngineType(1, "ROW");
        boolean actualDelete = (boolean) engineTypeService.deleteEngineTypeById(expectedType.getId());
        assertTrue(actualDelete);
    }

    @Test
    @Order(4)
    void containsEngineTypeWithSameName() {
        EngineType expectedType = new EngineType(2, "OPPOSITE");
        engineTypeService.addToStorage(expectedType);
        Throwable thrown = assertThrows(EntityAlreadyExistException.class, () ->
                engineTypeService.containsEngineTypeWithSameName(expectedType.getNameOfEngineType()));
        assertNotNull(thrown.getMessage());

    }

    @Test
    @Order(5)
    void getAllEnginesType() {
        int expectedSize = 1;
        List<EngineType> allEnginesType = (List<EngineType>) engineTypeService.getAllEnginesType();
        assertEquals(expectedSize, allEnginesType.size());
    }

    @Test
    @Order(6)
    void getCountOfAllEngineTypes() {
        int expectedAmount = 1;
        int countOfAllEngineTypes = (int) engineTypeService.getCountOfAllEngineTypes();
        assertEquals(expectedAmount, countOfAllEngineTypes);
    }
}