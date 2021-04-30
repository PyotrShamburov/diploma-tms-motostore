package by.tms.home.repository.motorcycle;

import by.tms.home.entity.motorcycle.MotorcycleCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleConditionRepository extends JpaRepository<MotorcycleCondition, Long> {
    boolean existsByNameOfCondition(String nameOfCondition);
    long count();
}
