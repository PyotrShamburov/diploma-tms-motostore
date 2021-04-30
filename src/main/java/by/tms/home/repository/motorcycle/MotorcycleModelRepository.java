package by.tms.home.repository.motorcycle;

import by.tms.home.entity.motorcycle.Brand;
import by.tms.home.entity.motorcycle.MotorcycleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MotorcycleModelRepository extends JpaRepository<MotorcycleModel, Long>{
    boolean existsByNameOfModel(String nameOfModel);
    Optional<MotorcycleModel> findByNameOfModel(String nameOfModel);
    List<MotorcycleModel> findAllByBrand_Id(long brandId);
    long count();
}
