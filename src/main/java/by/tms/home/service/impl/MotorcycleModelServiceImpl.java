package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.ModelDTO;
import by.tms.home.entity.motorcycle.MotorcycleModel;
import by.tms.home.repository.motorcycle.MotorcycleModelRepository;
import by.tms.home.service.BrandService;
import by.tms.home.service.MotorcycleModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorcycleModelServiceImpl implements MotorcycleModelService {

    @Autowired
    private MotorcycleModelRepository motorcycleModelRepository;
    @Autowired
    private BrandService brandService;

    @Override
    public MotorcycleModel addToStorage(MotorcycleModel motorcycleModel) {
        containsMotorcycleModelWithSameName(motorcycleModel.getNameOfModel());
        return motorcycleModelRepository.save(motorcycleModel);
    }

    @Override
    public boolean deleteMotorcycleModelById(long id) throws EntityNotFoundException {
        if (motorcycleModelRepository.existsById(id)) {
            motorcycleModelRepository.deleteById(id);
            return true;
        }
        throw new EntityNotFoundException("Motorcycle model with this name not found!");
    }

    @Override
    public void containsMotorcycleModelWithSameName(String name) throws EntityAlreadyExistException {
        if (motorcycleModelRepository.existsByNameOfModel(name)) {
            throw new EntityAlreadyExistException("Motorcycle model with this name already exist!");
        }
    }

    @Override
    public MotorcycleModel getMotorcycleModelByName(String name) {
        Optional<MotorcycleModel> byNameOfModel = motorcycleModelRepository.findByNameOfModel(name);
        return byNameOfModel.orElse(null);
    }

    @Override
    public MotorcycleModel createModelFromModelDTO(MotorcycleModel motorcycleModel, ModelDTO modelDTO) {
        String nameOfModel = modelDTO.getNameOfModel();
        long brandId = (long) modelDTO.getBrandId();
        motorcycleModel.setNameOfModel(nameOfModel);
        motorcycleModel.setBrand(brandService.getBrandById(brandId));
        return motorcycleModel;
    }

    @Override
    public List<MotorcycleModel> getModelsByBrandId(long brandId) {
        return motorcycleModelRepository.findAllByBrand_Id(brandId);
    }
}
