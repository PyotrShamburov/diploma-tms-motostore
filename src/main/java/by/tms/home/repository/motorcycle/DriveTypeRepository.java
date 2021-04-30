package by.tms.home.repository.motorcycle;

import by.tms.home.entity.motorcycle.DriveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveTypeRepository extends JpaRepository<DriveType, Long> {
    boolean existsByNameOfDriveType(String nameOfDriveType);
    long count();
}
