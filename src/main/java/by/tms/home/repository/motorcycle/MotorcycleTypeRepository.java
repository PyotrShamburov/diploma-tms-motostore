package by.tms.home.repository.motorcycle;

import by.tms.home.entity.motorcycle.MotorcycleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleTypeRepository extends JpaRepository<MotorcycleType, Long> {
    boolean existsByNameOfMotorcycleType(String nameOfMotorcycleType);
    long count();
}
