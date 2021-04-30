package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.Brand;
import by.tms.home.entity.motorcycle.MotorcycleModel;
import by.tms.home.repository.motorcycle.BrandRepository;
import by.tms.home.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void addToStorage(Brand brand) {
        containsBrandWithSameName(brand.getNameOfBrand());
        brandRepository.save(brand);
    }

    @Override
    public Brand getBrandById(long id) {
        Optional<Brand> brandById = (Optional<Brand>) brandRepository.findById(id);
        if (brandById.isPresent()) {
            return brandById.get();
        }
        throw new EntityNotFoundException("Brand with this id not found!");
    }

    @Override
    public boolean deleteBrandById(long id) {
        if (brandRepository.existsById(id)) {
            brandRepository.deleteById(id);
            return true;
        }
        throw new EntityNotFoundException("Brand with this id not found");
    }

    @Override
    public void containsBrandWithSameName(String name) {
        if (brandRepository.existsByNameOfBrand(name)) {
            throw new EntityAlreadyExistException("Brand with this name already exist!");
        }
    }

    @Override
    public Brand getByNameOfBrand(String nameOfBrand) throws EntityNotFoundException {
        Optional<Brand> byNameOfBrand = brandRepository.findByNameOfBrand(nameOfBrand);
        if (byNameOfBrand.isPresent()) {
            return byNameOfBrand.get();
        }
        throw new EntityNotFoundException("Brand with this name not found!");
    }

    @Override
    public List<Brand> getAllBrands() {
       return brandRepository.findAll();
    }

    @Override
    public long getCountOfAllBrands() {
        return brandRepository.count();
    }

}
