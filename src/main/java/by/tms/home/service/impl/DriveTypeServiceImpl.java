package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.DriveType;
import by.tms.home.repository.motorcycle.DriveTypeRepository;
import by.tms.home.service.DriveTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DriveTypeServiceImpl implements DriveTypeService {
    @Autowired
    private DriveTypeRepository driveTypeRepository;
    @Override
    public void addToStorage(DriveType driveType) {
        containsDriveTypeWithSameName(driveType.getNameOfDriveType());
        log.info("New drive type saved! "+driveType);
        driveTypeRepository.save(driveType);
    }

    @Override
    public DriveType getDriveTypeById(long id) throws EntityNotFoundException {
        Optional<DriveType> driveTypeById = (Optional<DriveType>) driveTypeRepository.findById(id);
        if (driveTypeById.isPresent()) {
            log.info("Drive type with id ["+id+"] found!");
            return driveTypeById.get();
        }
        log.warn("Drive type with id ["+id+"] not found!");
        throw new EntityNotFoundException("Drive type with this id not found!");
    }

    @Override
    public boolean deleteDriveTypeById(long id) throws EntityNotFoundException {
        if (driveTypeRepository.existsById(id)) {
            driveTypeRepository.deleteById(id);
            log.info("Drive type with id ["+id+"] deleted!");
            return true;
        }
        log.warn("Drive type with id ["+id+"] not found!");
        throw new EntityNotFoundException("Drive type with this id not found!");
    }

    @Override
    public void containsDriveTypeWithSameName(String name) throws EntityAlreadyExistException {
        if (driveTypeRepository.existsByNameOfDriveType(name)) {
            log.warn("Drive type with name ["+name+"] already exist!");
            throw new EntityAlreadyExistException("Drive type with this name already exist!");
        }
    }

    @Override
    public List<DriveType> getAllDriveTypes() {
        log.info("Request to get all drive types!");
        return driveTypeRepository.findAll();
    }

    @Override
    public long getCountOfAllDriveTypes() {
        log.info("Request to get count of drive types!");
        return driveTypeRepository.count();
    }
}
