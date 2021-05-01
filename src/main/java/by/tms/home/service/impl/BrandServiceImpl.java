package by.tms.home.service.impl;

import by.tms.home.entity.exception.EntityAlreadyExistException;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.Brand;
import by.tms.home.entity.motorcycle.MotorcycleModel;
import by.tms.home.repository.motorcycle.BrandRepository;
import by.tms.home.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void addToStorage(Brand brand) {
        containsBrandWithSameName(brand.getNameOfBrand());
        log.info("New brand for saving: "+brand);
        brandRepository.save(brand);
    }

    @Override
    public Brand getBrandById(long id) {
        Optional<Brand> brandById = (Optional<Brand>) brandRepository.findById(id);
        if (brandById.isPresent()) {
            log.info("Brand with ID found!Id :"+id);
            return brandById.get();
        }
        log.warn("Brand with id ["+id+"] not found!");
        throw new EntityNotFoundException("Brand with this id not found!");
    }

    @Override
    public boolean deleteBrandById(long id) {
        if (brandRepository.existsById(id)) {
            brandRepository.deleteById(id);
            log.info("Brand with id ["+id+"] deleted!");
            return true;
        }
        log.warn("Brand with id ["+id+"] not found!");
        throw new EntityNotFoundException("Brand with this id not found");
    }

    @Override
    public void containsBrandWithSameName(String name) {
        if (brandRepository.existsByNameOfBrand(name)) {
            log.warn("Brand with name ["+name+"] already exists!");
            throw new EntityAlreadyExistException("Brand with this name already exist!");
        }
    }

    @Override
    public Brand getByNameOfBrand(String nameOfBrand) throws EntityNotFoundException {
        Optional<Brand> byNameOfBrand = brandRepository.findByNameOfBrand(nameOfBrand);
        if (byNameOfBrand.isPresent()) {
            log.info("Brand with name ["+nameOfBrand+"] found!");
            return byNameOfBrand.get();
        }
        log.warn("Brand with name ["+nameOfBrand+"] not found!");
        throw new EntityNotFoundException("Brand with this name not found!");
    }

    @Override
    public List<Brand> getAllBrands() {
        log.info("Request to get all brands!");
       return brandRepository.findAll();
    }

    @Override
    public long getCountOfAllBrands() {
        log.info("Request for count brands!");
        return brandRepository.count();
    }

}
