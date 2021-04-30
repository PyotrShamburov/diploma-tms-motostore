package by.tms.home.service.impl;

import by.tms.home.entity.motorcycle.Motorcycle;
import by.tms.home.entity.motorcycle.MotorcycleDTO;
import by.tms.home.repository.motorcycle.MotorcycleRepository;
import by.tms.home.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MotorcycleServiceImpl implements MotorcycleService {

    @Autowired
    private MotorcycleRepository motorcycleRepository;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MotorcycleModelService motorcycleModelService;
    @Autowired
    private MotorcycleTypeService motorcycleTypeService;
    @Autowired
    private MotorcycleConditionService motorcycleConditionService;
    @Autowired
    private EngineTypeService engineTypeService;
    @Autowired
    private DriveTypeService driveTypeService;

    @Override
    @Transactional
    public Motorcycle saveMotorcycle(Motorcycle motorcycle) {
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
        motorcycle.setTypeOfMotorcycle(motorcycleTypeService.getMotorcycleTypeById(typeId));
        motorcycle.setCondition(motorcycleConditionService.getMotorcycleConditionById(conditionId));
        motorcycle.setEngineDesignType(engineTypeService.getEngineTypeById(engineTypeId));
        motorcycle.setDriveType(driveTypeService.getDriveTypeById(driveTypeId));
        motorcycle.setYearOfIssue(yearOfIssue);
        motorcycle.setCylindersAmount(cylinderAmount);
        motorcycle.setEngineVolume(engineVolume);
        motorcycle.setMileage(mileage);
        return motorcycle;
    }

}
