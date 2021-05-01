package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.ModelDTO;
import by.tms.home.entity.motorcycle.MotorcycleModel;
import by.tms.home.repository.motorcycle.MotorcycleModelRepository;
import by.tms.home.service.BrandService;
import by.tms.home.service.MotorcycleModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MotorcycleModelServiceImpl implements MotorcycleModelService {

    @Autowired
    private MotorcycleModelRepository motorcycleModelRepository;
    @Autowired
    private BrandService brandService;

    @Override
    public MotorcycleModel addToStorage(MotorcycleModel motorcycleModel) {
        containsMotorcycleModelWithSameName(motorcycleModel.getNameOfModel());
        log.info("New model for save: "+motorcycleModel);
        return motorcycleModelRepository.save(motorcycleModel);
    }

    @Override
    public boolean deleteMotorcycleModelById(long id) throws EntityNotFoundException {
        if (motorcycleModelRepository.existsById(id)) {
            motorcycleModelRepository.deleteById(id);
            log.info("Motorcycle model with id ["+id+"] deleted!");
            return true;
        }
        log.warn("Motorcycle model with id ["+id+"] not found!");
        throw new EntityNotFoundException("Motorcycle model with this id not found!");
    }

    @Override
    public void containsMotorcycleModelWithSameName(String name) throws EntityAlreadyExistException {
        if (motorcycleModelRepository.existsByNameOfModel(name)) {
            log.warn("Motorcycle model with name ["+name+"] already exist!");
            throw new EntityAlreadyExistException("Motorcycle model with this name already exist!");
        }
    }

    @Override
    public MotorcycleModel getMotorcycleModelByName(String name) {
        Optional<MotorcycleModel> byNameOfModel = motorcycleModelRepository.findByNameOfModel(name);
        log.info("Motorcycle model with name ["+name+"] found!");
        return byNameOfModel.orElse(null);
    }

    @Override
    public MotorcycleModel createModelFromModelDTO(MotorcycleModel motorcycleModel, ModelDTO modelDTO) {
        String nameOfModel = modelDTO.getNameOfModel();
        log.info("Creating model from DTO! DTO: "+modelDTO);
        long brandId = (long) modelDTO.getBrandId();
        motorcycleModel.setNameOfModel(nameOfModel);
        motorcycleModel.setBrand(brandService.getBrandById(brandId));
        log.info("Created model: "+motorcycleModel);
        return motorcycleModel;
    }

    @Override
    public List<MotorcycleModel> getModelsByBrandId(long brandId) {
        log.info("Request to get all motorcycle models!");
        return motorcycleModelRepository.findAllByBrand_Id(brandId);
    }
}
