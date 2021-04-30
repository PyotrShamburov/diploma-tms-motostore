package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.EngineType;
import by.tms.home.repository.motorcycle.EngineTypeRepository;
import by.tms.home.service.EngineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EngineTypeServiceImpl implements EngineTypeService {
    @Autowired
    private EngineTypeRepository engineTypeRepository;

    @Override
    public void addToStorage(EngineType engineType) {
        containsEngineTypeWithSameName(engineType.getNameOfEngineType());
        engineTypeRepository.save(engineType);
    }

    @Override
    public EngineType getEngineTypeById(long id) throws EntityNotFoundException {
        Optional<EngineType> engineTypeById = (Optional<EngineType>) engineTypeRepository.findById(id);
        if (engineTypeById.isPresent()) {
            return engineTypeById.get();
        }
        throw new EntityNotFoundException("Engine type with this id not found!");
    }

    @Override
    public boolean deleteEngineTypeById(long id) throws EntityNotFoundException {
        if (engineTypeRepository.existsById(id)) {
            engineTypeRepository.deleteById(id);
            return true;
        }
        throw new EntityNotFoundException("Engine type with this name not found!");
    }

    @Override
    public void containsEngineTypeWithSameName(String name) throws EntityAlreadyExistException {
        if (engineTypeRepository.existsByNameOfEngineType(name)) {
            throw new EntityAlreadyExistException("Engine type with this name already exist!");
        }
    }

    @Override
    public List<EngineType> getAllEnginesType() {
        return engineTypeRepository.findAll();
    }

    @Override
    public long getCountOfAllEngineTypes() {
        return engineTypeRepository.count();
    }
}
