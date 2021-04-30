package by.tms.home.repository;

import by.tms.home.entity.Announcement;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Optional<Announcement> getById(long id);
    void deleteById(long id);
    boolean existsById(long id);
    long count();
    List<Announcement> findAllByMotorcycle_Brand_IdIsGreaterThanEqualAndMotorcycle_Brand_IdIsLessThanEqual(long brandStartId, long brandEndId);
    List<Announcement> findAllByMotorcycle_Brand_IdIsGreaterThanEqualAndMotorcycle_Brand_IdIsLessThanEqualAndMotorcycle_TypeOfMotorcycle_IdIsGreaterThanEqualAndMotorcycle_TypeOfMotorcycle_IdIsLessThanEqualAndMotorcycle_Condition_IdIsGreaterThanEqualAndMotorcycle_Condition_IdIsLessThanEqualAndMotorcycle_EngineDesignType_IdIsGreaterThanEqualAndMotorcycle_EngineDesignType_IdIsLessThanEqualAndMotorcycle_DriveType_IdIsGreaterThanEqualAndMotorcycle_DriveType_IdIsLessThanEqualAndMotorcycle_YearOfIssueIsGreaterThanEqualAndMotorcycle_YearOfIssueIsLessThanEqualAndMotorcycle_EngineVolumeIsGreaterThanEqualAndMotorcycle_EngineVolumeIsLessThanEqualAndPrice_ValueIsGreaterThanEqualAndPrice_ValueIsLessThanEqual(
            long brandStartId, long brandEndId, long motoTypeStartId, long motoTypeEndId, long conditionStartId, long conditionEndId, long engineTypeStartId, long engineTypeEndId, long driveTypeStartId, long driveTypeEndId, int yearOfIssueStart, int yearOfIssueEnd, int engineVolumeStart, int engineVolumeEnd, int priceValueStart, int priceValueEnd);
    List<Announcement> findTopByMotorcycle_Id(long amountOfTop);

}
