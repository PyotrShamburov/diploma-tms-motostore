package by.tms.home.service;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.MotorcycleType;

import java.util.List;

public interface MotorcycleTypeService {
    void addToStorage(MotorcycleType motorcycleType);
    MotorcycleType getMotorcycleTypeById(long id) throws EntityNotFoundException;
    boolean deleteMotorcycleTypeById(long id) throws EntityNotFoundException;
    void containsMotorcycleTypeWithSameName(String name) throws EntityAlreadyExistException;
    List<MotorcycleType> getAllMotoTypes();
    long getCountOfAllTypes();
}
