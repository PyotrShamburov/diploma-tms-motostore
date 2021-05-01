package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.motorcycle.DriveType;
import by.tms.home.repository.motorcycle.DriveTypeRepository;
import by.tms.home.service.DriveTypeService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriveTypeServiceImplTest {

    @Autowired
    private DriveTypeService driveTypeService;
    @Autowired
    private DriveTypeRepository driveTypeRepository;

    @Test
    @Order(1)
    void addToStorage() {
        DriveType driveType = new DriveType(1, "CHAIN");
        driveTypeService.addToStorage(driveType);
        DriveType driveTypeFromDB = null;
        Optional<DriveType> byId = (Optional<DriveType>) driveTypeRepository.findById(1L);
        if (byId.isPresent()) {
            driveTypeFromDB = byId.get();
        }
        assertEquals(driveType.getNameOfDriveType(), driveTypeFromDB.getNameOfDriveType());
    }

    @Test
    @Order(2)
    void getDriveTypeById() {
        DriveType expectedDriveType = new DriveType(1, "CHAIN");
        DriveType actualDriveType = (DriveType) driveTypeService.getDriveTypeById(expectedDriveType.getId());
        assertEquals(expectedDriveType, actualDriveType);
    }

    @Test
    @Order(3)
    void deleteDriveTypeById() {
        DriveType expectedDriveType = new DriveType(1, "CHAIN");
        boolean actual = (boolean) driveTypeService.deleteDriveTypeById(expectedDriveType.getId());
        assertTrue(actual);
    }

    @Test
    @Order(4)
    void containsDriveTypeWithSameName() {
        DriveType expectedDriveType = new DriveType(2, "BELT");
        driveTypeService.addToStorage(expectedDriveType);
        Throwable thrown = assertThrows(EntityAlreadyExistException.class, () ->
                driveTypeService.containsDriveTypeWithSameName(expectedDriveType.getNameOfDriveType()));
        assertNotNull(thrown.getMessage());
    }

    @Test
    @Order(5)
    void getAllDriveTypes() {
        int expectedSize = 1;
        List<DriveType> actualDriveTypes = (List<DriveType>) driveTypeService.getAllDriveTypes();
        assertEquals(expectedSize, actualDriveTypes.size());
    }

    @Test
    @Order(6)
    void getCountOfAllDriveTypes() {
        int expectedAmount = 1;
        int actualAmount = (int) driveTypeService.getCountOfAllDriveTypes();
        assertEquals(expectedAmount, actualAmount);
    }
}