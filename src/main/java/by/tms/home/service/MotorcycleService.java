package by.tms.home.service;

import by.tms.home.entity.motorcycle.Motorcycle;
import by.tms.home.entity.motorcycle.MotorcycleDTO;

public interface MotorcycleService {
    Motorcycle saveMotorcycle(Motorcycle motorcycle);
    Motorcycle setInfoFieldsFromDTO(Motorcycle motorcycle, MotorcycleDTO motorcycleDTO);
}
