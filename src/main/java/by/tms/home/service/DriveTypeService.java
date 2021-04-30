package by.tms.home.service;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.DriveType;

import java.util.List;

public interface DriveTypeService {
    void addToStorage(DriveType driveType);
    DriveType getDriveTypeById(long id) throws EntityNotFoundException;
    boolean deleteDriveTypeById(long id) throws EntityNotFoundException;
    void containsDriveTypeWithSameName(String name) throws EntityAlreadyExistException;
    List<DriveType> getAllDriveTypes();
    long getCountOfAllDriveTypes();
}
