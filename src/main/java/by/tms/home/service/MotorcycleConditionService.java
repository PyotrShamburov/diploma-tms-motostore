package by.tms.home.service;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.MotorcycleCondition;

import java.util.List;

public interface MotorcycleConditionService {
    void addToStorage(MotorcycleCondition motorcycleCondition);
    MotorcycleCondition getMotorcycleConditionById(long id) throws EntityNotFoundException;
    boolean deleteMotorcycleConditionById(long id) throws EntityNotFoundException;
    void containsMotorcycleConditionWithSameName(String name) throws EntityAlreadyExistException;
    List<MotorcycleCondition> getAllConditions();
    long getCountOfAllConditions();
}
