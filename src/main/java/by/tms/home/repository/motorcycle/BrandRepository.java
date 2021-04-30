package by.tms.home.repository.motorcycle;

import by.tms.home.entity.motorcycle.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByNameOfBrand(String nameOfBrand);
    Optional<Brand> findByNameOfBrand(String nameOfBrand);
    long count();
}
