package by.tms.home.service;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.ModelDTO;
import by.tms.home.entity.motorcycle.MotorcycleModel;

import java.util.List;

public interface MotorcycleModelService {
    MotorcycleModel addToStorage(MotorcycleModel motorcycleModel);
    boolean deleteMotorcycleModelById(long id) throws EntityNotFoundException;
    void containsMotorcycleModelWithSameName(String name) throws EntityAlreadyExistException;
    MotorcycleModel getMotorcycleModelByName(String name);
    MotorcycleModel createModelFromModelDTO(MotorcycleModel motorcycleModel, ModelDTO modelDTO);
    List<MotorcycleModel> getModelsByBrandId(long brandId);
}
