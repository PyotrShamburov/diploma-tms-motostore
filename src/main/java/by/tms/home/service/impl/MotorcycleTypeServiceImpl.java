package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.MotorcycleType;
import by.tms.home.repository.motorcycle.MotorcycleTypeRepository;
import by.tms.home.service.MotorcycleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorcycleTypeServiceImpl implements MotorcycleTypeService {
    @Autowired
    private MotorcycleTypeRepository motorcycleTypeRepository;
    @Override
    public void addToStorage(MotorcycleType motorcycleType) {
        containsMotorcycleTypeWithSameName(motorcycleType.getNameOfMotorcycleType());
        motorcycleTypeRepository.save(motorcycleType);
    }

    @Override
    public MotorcycleType getMotorcycleTypeById(long id) throws EntityNotFoundException {
        Optional<MotorcycleType> typeById = motorcycleTypeRepository.findById(id);
        if (typeById.isPresent()) {
            return typeById.get();
        }
        throw new EntityNotFoundException("Motorcycle type with this id not found!");
    }

    @Override
    public boolean deleteMotorcycleTypeById(long id) throws EntityNotFoundException {
        if (motorcycleTypeRepository.existsById(id)) {
            motorcycleTypeRepository.deleteById(id);
            return true;
        }
        throw new EntityNotFoundException("Motorcycle type with this name not found!");
    }

    @Override
    public void containsMotorcycleTypeWithSameName(String name) throws EntityAlreadyExistException {
        if (motorcycleTypeRepository.existsByNameOfMotorcycleType(name)) {
            throw new EntityAlreadyExistException("Motorcycle type with this name already exist!");
        }
    }

    @Override
    public List<MotorcycleType> getAllMotoTypes() {
        return motorcycleTypeRepository.findAll();
    }

    @Override
    public long getCountOfAllTypes() {
        return motorcycleTypeRepository.count();
    }
}
