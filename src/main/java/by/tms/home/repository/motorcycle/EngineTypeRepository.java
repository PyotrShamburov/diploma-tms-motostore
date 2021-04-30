package by.tms.home.repository.motorcycle;

import by.tms.home.entity.motorcycle.EngineType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineTypeRepository extends JpaRepository<EngineType, Long> {
    boolean existsByNameOfEngineType(String nameOfEngineType);
    long count();
}
