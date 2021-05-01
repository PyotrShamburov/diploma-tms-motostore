package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.MotorcycleType;
import by.tms.home.repository.motorcycle.MotorcycleTypeRepository;
import by.tms.home.service.MotorcycleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MotorcycleTypeServiceImpl implements MotorcycleTypeService {

    @Autowired
    private MotorcycleTypeRepository motorcycleTypeRepository;

    @Override
    public void addToStorage(MotorcycleType motorcycleType) {
        containsMotorcycleTypeWithSameName(motorcycleType.getNameOfMotorcycleType());
        log.info("New moto type for save: "+motorcycleType);
        motorcycleTypeRepository.save(motorcycleType);
    }

    @Override
    public MotorcycleType getMotorcycleTypeById(long id) throws EntityNotFoundException {
        Optional<MotorcycleType> typeById = motorcycleTypeRepository.findById(id);
        if (typeById.isPresent()) {
            log.info("Moto type with id ["+id+"] found!");
            return typeById.get();
        }
        log.warn("Moto type with id ["+id+"] not found!");
        throw new EntityNotFoundException("Motorcycle type with this id not found!");
    }

    @Override
    public boolean deleteMotorcycleTypeById(long id) throws EntityNotFoundException {
        if (motorcycleTypeRepository.existsById(id)) {
            motorcycleTypeRepository.deleteById(id);
            log.info("Moto type with id ["+id+"] deleted!");
            return true;
        }
        log.warn("Moto type with id ["+id+"] not found!");
        throw new EntityNotFoundException("Motorcycle type with this id not found!");
    }

    @Override
    public void containsMotorcycleTypeWithSameName(String name) throws EntityAlreadyExistException {
        if (motorcycleTypeRepository.existsByNameOfMotorcycleType(name)) {
            log.warn("Moto type with name ["+name+"] already exist!");
            throw new EntityAlreadyExistException("Motorcycle type with this name already exist!");
        }
    }

    @Override
    public List<MotorcycleType> getAllMotoTypes() {
        log.info("Request to get all moto types!");
        return motorcycleTypeRepository.findAll();
    }

    @Override
    public long getCountOfAllTypes() {
        log.info("Request to get count of moto types!");
        return motorcycleTypeRepository.count();
    }
}
