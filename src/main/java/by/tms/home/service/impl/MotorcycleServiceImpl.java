package by.tms.home.service.impl;

import by.tms.home.entity.motorcycle.Motorcycle;
import by.tms.home.entity.motorcycle.MotorcycleDTO;
import by.tms.home.repository.motorcycle.MotorcycleRepository;
import by.tms.home.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class MotorcycleServiceImpl implements MotorcycleService {

    @Autowired
    private MotorcycleRepository motorcycleRepository;
    @Autowired
    private MotorcycleTypeService motorcycleTypeService;
    @Autowired
    private MotorcycleConditionService motorcycleConditionService;
    @Autowired
    private EngineTypeService engineTypeService;
    @Autowired
    private DriveTypeService driveTypeService;

    @Override
    public Motorcycle saveMotorcycle(Motorcycle motorcycle) {
        log.info("New motorcycle for save: "+motorcycle);
       return motorcycleRepository.save(motorcycle);
    }

    @Override
    public Motorcycle setInfoFieldsFromDTO(Motorcycle motorcycle, MotorcycleDTO motorcycleDTO) {
        long typeId = (long) motorcycleDTO.getTypeId();
        long conditionId = (long) motorcycleDTO.getConditionId();
        long driveTypeId = (long) motorcycleDTO.getDriveTypeId();
        long engineTypeId = (long) motorcycleDTO.getEngineTypeId();
        int cylinderAmount = (int) motorcycleDTO.getCylinderAmount();
        int yearOfIssue = (int) motorcycleDTO.getYearOfIssue();
        int mileage = motorcycleDTO.getMileage();
        int engineVolume = (int) motorcycleDTO.getEngineVolume();
        log.info("Moto DTO for motorcycle: "+motorcycleDTO);
        motorcycle.setTypeOfMotorcycle(motorcycleTypeService.getMotorcycleTypeById(typeId));
        motorcycle.setCondition(motorcycleConditionService.getMotorcycleConditionById(conditionId));
        motorcycle.setEngineDesignType(engineTypeService.getEngineTypeById(engineTypeId));
        motorcycle.setDriveType(driveTypeService.getDriveTypeById(driveTypeId));
        motorcycle.setYearOfIssue(yearOfIssue);
        motorcycle.setCylindersAmount(cylinderAmount);
        motorcycle.setEngineVolume(engineVolume);
        motorcycle.setMileage(mileage);
        log.info("New motorcycle: "+motorcycle);
        return motorcycle;
    }

}
