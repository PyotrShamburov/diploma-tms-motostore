package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.MotorcycleCondition;
import by.tms.home.repository.motorcycle.MotorcycleConditionRepository;
import by.tms.home.service.MotorcycleConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorcycleConditionServiceImpl implements MotorcycleConditionService {
    @Autowired
    private MotorcycleConditionRepository motorcycleConditionRepository;

    @Override
    public void addToStorage(MotorcycleCondition motorcycleCondition) {
        containsMotorcycleConditionWithSameName(motorcycleCondition.getNameOfCondition());
        motorcycleConditionRepository.save(motorcycleCondition);
    }

    @Override
    public MotorcycleCondition getMotorcycleConditionById(long id) throws EntityNotFoundException {
        Optional<MotorcycleCondition> motorcycleConditionById = motorcycleConditionRepository.findById(id);
        if (motorcycleConditionById.isPresent()) {
            return motorcycleConditionById.get();
        }
        throw new EntityNotFoundException("Motorcycle condition with this id not found!");
    }

    @Override
    public boolean deleteMotorcycleConditionById(long id) throws EntityNotFoundException {
        if (motorcycleConditionRepository.existsById(id)) {
            motorcycleConditionRepository.deleteById(id);
            return true;
        }
        throw new EntityNotFoundException("Motorcycle condition with this name not found!");
    }

    @Override
    public void containsMotorcycleConditionWithSameName(String name) throws EntityAlreadyExistException {
        if (motorcycleConditionRepository.existsByNameOfCondition(name)) {
            throw new EntityAlreadyExistException("Motorcycle condition with this name already exist!");
        }
    }

    @Override
    public List<MotorcycleCondition> getAllConditions() {
       return motorcycleConditionRepository.findAll();
    }

    @Override
    public long getCountOfAllConditions() {
        return motorcycleConditionRepository.count();
    }
}
