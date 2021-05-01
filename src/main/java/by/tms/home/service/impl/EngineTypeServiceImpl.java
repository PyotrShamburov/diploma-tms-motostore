package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.EngineType;
import by.tms.home.repository.motorcycle.EngineTypeRepository;
import by.tms.home.service.EngineTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EngineTypeServiceImpl implements EngineTypeService {

    @Autowired
    private EngineTypeRepository engineTypeRepository;

    @Override
    public void addToStorage(EngineType engineType) {
        containsEngineTypeWithSameName(engineType.getNameOfEngineType());
        log.info("New engine type for save: "+engineType);
        engineTypeRepository.save(engineType);
    }

    @Override
    public EngineType getEngineTypeById(long id) throws EntityNotFoundException {
        Optional<EngineType> engineTypeById = (Optional<EngineType>) engineTypeRepository.findById(id);
        if (engineTypeById.isPresent()) {
            log.info("Engine type with id ["+id+"] found!");
            return engineTypeById.get();
        }
        log.warn("Engine type with id ["+id+"] not found!");
        throw new EntityNotFoundException("Engine type with this id not found!");
    }

    @Override
    public boolean deleteEngineTypeById(long id) throws EntityNotFoundException {
        if (engineTypeRepository.existsById(id)) {
            engineTypeRepository.deleteById(id);
            log.info("Engine type with id ["+id+"] deleted!");
            return true;
        }
        log.warn("Engine type with id ["+id+"] not found!");
        throw new EntityNotFoundException("Engine type with this id not found!");
    }

    @Override
    public void containsEngineTypeWithSameName(String name) throws EntityAlreadyExistException {
        if (engineTypeRepository.existsByNameOfEngineType(name)) {
            log.warn("Engine type with name ["+name+"] already exist!");
            throw new EntityAlreadyExistException("Engine type with this name already exist!");
        }
    }

    @Override
    public List<EngineType> getAllEnginesType() {
        log.info("Request to get all engine types!");
        return engineTypeRepository.findAll();
    }

    @Override
    public long getCountOfAllEngineTypes() {
        log.info("Request to get count of engine types!");
        return engineTypeRepository.count();
    }
}
