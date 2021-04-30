package by.tms.home.service;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.EngineType;

import java.util.List;


public interface EngineTypeService {
    void addToStorage(EngineType engineType);
    EngineType getEngineTypeById(long id) throws EntityNotFoundException;
    boolean deleteEngineTypeById(long id) throws EntityNotFoundException;
    void containsEngineTypeWithSameName(String name) throws EntityAlreadyExistException;
    List<EngineType> getAllEnginesType();
    long getCountOfAllEngineTypes();
}
