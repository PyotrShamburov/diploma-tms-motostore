package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.DriveType;
import by.tms.home.repository.motorcycle.DriveTypeRepository;
import by.tms.home.service.DriveTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriveTypeServiceImpl implements DriveTypeService {
    @Autowired
    private DriveTypeRepository driveTypeRepository;
    @Override
    public void addToStorage(DriveType driveType) {
        containsDriveTypeWithSameName(driveType.getNameOfDriveType());
        driveTypeRepository.save(driveType);
    }

    @Override
    public DriveType getDriveTypeById(long id) throws EntityNotFoundException {
        Optional<DriveType> driveTypeById = (Optional<DriveType>) driveTypeRepository.findById(id);
        if (driveTypeById.isPresent()) {
            return driveTypeById.get();
        }
        throw new EntityNotFoundException("Drive type with this id not found!");
    }

    @Override
    public boolean deleteDriveTypeById(long id) throws EntityNotFoundException {
        if (driveTypeRepository.existsById(id)) {
            driveTypeRepository.deleteById(id);
            return true;
        }
        throw new EntityNotFoundException("Drive type with this id not found!");
    }

    @Override
    public void containsDriveTypeWithSameName(String name) throws EntityAlreadyExistException {
        if (driveTypeRepository.existsByNameOfDriveType(name)) {
            throw new EntityAlreadyExistException("Drive type with this name already exist!");
        }
    }

    @Override
    public List<DriveType> getAllDriveTypes() {
        return driveTypeRepository.findAll();
    }

    @Override
    public long getCountOfAllDriveTypes() {
        return driveTypeRepository.count();
    }
}
