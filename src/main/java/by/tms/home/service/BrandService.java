package by.tms.home.service;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.Brand;
import by.tms.home.entity.motorcycle.MotorcycleModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    void addToStorage(Brand brand);
    Brand getBrandById(long id) throws EntityNotFoundException;
    boolean deleteBrandById(long id) throws EntityNotFoundException;
    void containsBrandWithSameName(String name) throws EntityAlreadyExistException;
    Brand getByNameOfBrand(String nameOfBrand) throws EntityNotFoundException;
    List<Brand> getAllBrands();
    long getCountOfAllBrands();


}
