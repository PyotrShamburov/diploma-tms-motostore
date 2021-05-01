package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.MotorcycleCondition;
import by.tms.home.repository.motorcycle.MotorcycleConditionRepository;
import by.tms.home.service.MotorcycleConditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MotorcycleConditionServiceImpl implements MotorcycleConditionService {

    @Autowired
    private MotorcycleConditionRepository motorcycleConditionRepository;

    @Override
    public void addToStorage(MotorcycleCondition motorcycleCondition) {
        containsMotorcycleConditionWithSameName(motorcycleCondition.getNameOfCondition());
        log.info("New condition for save: "+motorcycleCondition);
        motorcycleConditionRepository.save(motorcycleCondition);
    }

    @Override
    public MotorcycleCondition getMotorcycleConditionById(long id) throws EntityNotFoundException {
        Optional<MotorcycleCondition> motorcycleConditionById = motorcycleConditionRepository.findById(id);
        if (motorcycleConditionById.isPresent()) {
            log.info("Motorcycle condition with id ["+id+"] found!");
            return motorcycleConditionById.get();
        }
        log.warn("Motorcycle condition with id ["+id+"] not found!");
        throw new EntityNotFoundException("Motorcycle condition with this id not found!");
    }

    @Override
    public boolean deleteMotorcycleConditionById(long id) throws EntityNotFoundException {
        if (motorcycleConditionRepository.existsById(id)) {
            motorcycleConditionRepository.deleteById(id);
            log.info("Motorcycle condition with id ["+id+"] deleted!");
            return true;
        }
        log.warn("Motorcycle condition with id ["+id+"] not found!");
        throw new EntityNotFoundException("Motorcycle condition with this id not found!");
    }

    @Override
    public void containsMotorcycleConditionWithSameName(String name) throws EntityAlreadyExistException {
        if (motorcycleConditionRepository.existsByNameOfCondition(name)) {
            log.warn("Motorcycle condition with name ["+name+"] already exist!");
            throw new EntityAlreadyExistException("Motorcycle condition with this name already exist!");
        }
    }

    @Override
    public List<MotorcycleCondition> getAllConditions() {
        log.info("Request for get all conditions!");
        return motorcycleConditionRepository.findAll();
    }

    @Override
    public long getCountOfAllConditions() {
        log.info("Request for get count of all conditions!");
        return motorcycleConditionRepository.count();
    }
}
