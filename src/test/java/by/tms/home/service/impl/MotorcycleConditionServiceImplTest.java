package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.motorcycle.MotorcycleCondition;
import by.tms.home.repository.motorcycle.MotorcycleConditionRepository;
import by.tms.home.service.MotorcycleConditionService;
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
class MotorcycleConditionServiceImplTest {

    @Autowired
    private MotorcycleConditionService conditionService;
    @Autowired
    private MotorcycleConditionRepository conditionRepository;

    @Test
    @Order(1)
    void addToStorage() {
        MotorcycleCondition expectedCondition = new MotorcycleCondition(1, "NEW");
        conditionService.addToStorage(expectedCondition);
        MotorcycleCondition actualCondition = null;
        Optional<MotorcycleCondition> byId = (Optional<MotorcycleCondition>) conditionRepository.findById(expectedCondition.getId());
        if (byId.isPresent()) {
            actualCondition = byId.get();
        }
        assertEquals(expectedCondition, actualCondition);
    }

    @Test
    @Order(2)
    void getMotorcycleConditionById() {
        MotorcycleCondition expectedCondition = new MotorcycleCondition(1, "NEW");
        MotorcycleCondition actualCondition = (MotorcycleCondition) conditionService.getMotorcycleConditionById(expectedCondition.getId());
        assertEquals(expectedCondition, actualCondition);
    }

    @Test
    @Order(3)
    void deleteMotorcycleConditionById() {
        MotorcycleCondition expectedCondition = new MotorcycleCondition(1, "NEW");
        boolean actualResult = (boolean) conditionService.deleteMotorcycleConditionById(expectedCondition.getId());
        assertTrue(actualResult);
    }

    @Test
    @Order(4)
    void containsMotorcycleConditionWithSameName() {
        MotorcycleCondition expectedCondition = new MotorcycleCondition(2, "USE");
        conditionService.addToStorage(expectedCondition);
        Throwable thrown = assertThrows(EntityAlreadyExistException.class, ()->
                conditionService.containsMotorcycleConditionWithSameName(expectedCondition.getNameOfCondition()));
        assertNotNull(thrown.getMessage());
    }

    @Test
    @Order(5)
    void getAllConditions() {
        int expectedSize = 1;
        int actualSize = (int) conditionService.getAllConditions().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Order(6)
    void getCountOfAllConditions() {
        int expectedAmount = 1;
        int actualAmount = (int) conditionService.getCountOfAllConditions();
        assertEquals(expectedAmount, actualAmount);
    }
}