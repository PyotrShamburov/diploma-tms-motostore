package by.tms.home.service.impl;

import by.tms.home.entity.*;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.MotorcycleCondition;
import by.tms.home.entity.PriceDTO;
import by.tms.home.repository.AnnouncementRepository;
import by.tms.home.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MotorcycleTypeService typeService;
    @Autowired
    private MotorcycleConditionService conditionService;
    @Autowired
    private EngineTypeService engineTypeService;
    @Autowired
    private DriveTypeService driveTypeService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public Announcement addAdToDatabase(Announcement announcement) {
        setDateOfPublishingToAd(announcement);
       return announcementRepository.save(announcement);
    }

    @Override
    public Announcement getAdById(long id) {
        Optional<Announcement> byId = (Optional<Announcement>) announcementRepository.getById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new EntityNotFoundException("Ad with this id not found!");
    }

    @Override
    @Transactional
    public void deleteAdById(long id) {
        if (announcementRepository.existsById(id)) {
            announcementRepository.deleteById(id);
            return;
        }
        throw new EntityNotFoundException("Ad with this ID not found!");
    }

    @Override
    public void setDateOfPublishingToAd(Announcement ad) {
        LocalDate localDate = LocalDate.now();
        String pattern = "dd MMMM yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        localDate.format(dateTimeFormatter);
        ad.setDateOfPublishing(localDate.toString());
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }


    @Override
    public List<Announcement> findAdBySearchCriteria(AnnouncementSearchDTO nonCheckedSearchDTO) {
        AnnouncementSearchDTO readyMadeSearchDTO = (AnnouncementSearchDTO) createReadyMadeSearchDTO(nonCheckedSearchDTO);
        long brandStartId = readyMadeSearchDTO.getBrandStartId();
        long brandEndId = readyMadeSearchDTO.getBrandEndId();
        long typeStartId = readyMadeSearchDTO.getTypeStartId();
        long typeEndId = readyMadeSearchDTO.getTypeEndId();
        long conditionStartId = readyMadeSearchDTO.getConditionStartId();
        long conditionEndId = readyMadeSearchDTO.getConditionEndId();
        long engineTypeStartId = readyMadeSearchDTO.getEngineTypeStartId();
        long engineTypeEndId = readyMadeSearchDTO.getEngineTypeEndId();
        long driveTypeStartId = readyMadeSearchDTO.getDriveTypeStartId();
        long driveTypeEndId = readyMadeSearchDTO.getDriveTypeEndId();
        int lowestYearOfIssue = readyMadeSearchDTO.getLowestYearOfIssue();
        int highestYearOfIssue = readyMadeSearchDTO.getHighestYearOfIssue();
        int lowestEngineVolume = readyMadeSearchDTO.getLowestEngineVolume();
        int highestEngineVolume = readyMadeSearchDTO.getHighestEngineVolume();
        int lowestPrice = readyMadeSearchDTO.getLowestPrice();
        int highestPrice = readyMadeSearchDTO.getHighestPrice();
        List<Announcement> resultOfSearch = (List<Announcement>) announcementRepository.findAllByMotorcycle_Brand_IdIsGreaterThanEqualAndMotorcycle_Brand_IdIsLessThanEqualAndMotorcycle_TypeOfMotorcycle_IdIsGreaterThanEqualAndMotorcycle_TypeOfMotorcycle_IdIsLessThanEqualAndMotorcycle_Condition_IdIsGreaterThanEqualAndMotorcycle_Condition_IdIsLessThanEqualAndMotorcycle_EngineDesignType_IdIsGreaterThanEqualAndMotorcycle_EngineDesignType_IdIsLessThanEqualAndMotorcycle_DriveType_IdIsGreaterThanEqualAndMotorcycle_DriveType_IdIsLessThanEqualAndMotorcycle_YearOfIssueIsGreaterThanEqualAndMotorcycle_YearOfIssueIsLessThanEqualAndMotorcycle_EngineVolumeIsGreaterThanEqualAndMotorcycle_EngineVolumeIsLessThanEqualAndPrice_ValueIsGreaterThanEqualAndPrice_ValueIsLessThanEqual(
                brandStartId, brandEndId, typeStartId, typeEndId, conditionStartId, conditionEndId, engineTypeStartId, engineTypeEndId, driveTypeStartId, driveTypeEndId,
                lowestYearOfIssue, highestYearOfIssue, lowestEngineVolume, highestEngineVolume, lowestPrice, highestPrice);
        log.info("Result of search: "+resultOfSearch);
        return resultOfSearch;
    }


    @Override
    public AnnouncementSearchDTO createReadyMadeSearchDTO(AnnouncementSearchDTO searchDTO) {
        checkBrandSearchCriteria(searchDTO);
        checkMotoTypeSearchCriteria(searchDTO);
        checkConditionSearchCriteria(searchDTO);
        checkDriveTypeSearchCriteria(searchDTO);
        checkEngineTypeSearchCriteria(searchDTO);
        checkEngineVolumeSearchCriteria(searchDTO);
        checkYearOfIssueSearchCriteria(searchDTO);
        checkPriceSearchCriteria(searchDTO);
        log.info("Checked and ready to use searchDTO :"+searchDTO);
        return searchDTO;
    }

    @Override
    public List<Announcement> getRandomAdToHomePage(long amount) {
        return announcementRepository.findTopByMotorcycle_Id(amount);
    }

    @Override
    public boolean isAnnouncementHasNoPhotos(long adId) {
        Announcement adById = (Announcement) getAdById(adId);
        log.info("Ad photo check is performed!");
        return adById.getPhotos().size() == 0;
    }

    @Override
    public void addDefaultPhotoToAd(long adId, Image image) {
        if (isAnnouncementHasNoPhotos(adId)) {
            Announcement adById = (Announcement) getAdById(adId);
            image.setAnnouncement(adById);
            imageService.addImageToDatabase(image);
            log.info("Default image added to AD :"+adById);
        }
    }

    @Override
    public void photoCheckAndFinishOrSetDefaultPhoto(long adId) {
        if (isAnnouncementHasNoPhotos(adId)) {
            Image defaultImage = (Image) cloudinaryService.getDefaultImage();
            log.info("New default image :"+defaultImage);
            addDefaultPhotoToAd(adId, defaultImage);
        }
    }

    @Override
    public void updateAdComments(long adId, String newComments) {
        Announcement adById = (Announcement) getAdById(adId);
        adById.setComments(newComments);
        announcementRepository.save(adById);
    }

    @Override
    public void updateMileage(long adId, int newMileage) {
        Announcement adById = (Announcement) getAdById(adId);
        log.info("Old mileage :"+adById.getMotorcycle().getMileage());
        adById.getMotorcycle().setMileage(newMileage);
        announcementRepository.save(adById);
        log.info("New saved moto: "+getAdById(adId));
    }

    @Override
    public void updateCondition(long adId, long conditionId) {
        Announcement adById = (Announcement) getAdById(adId);
        MotorcycleCondition motorcycleConditionById = (MotorcycleCondition) conditionService.getMotorcycleConditionById(conditionId);
        adById.getMotorcycle().setCondition(motorcycleConditionById);
        announcementRepository.save(adById);
    }

    @Override
    public void updateOwnerInfo(long adId, OwnerDTO ownerDTO) {
        Announcement adById = (Announcement) getAdById(adId);
        Owner owner = (Owner) adById.getOwner();
        owner.setName(ownerDTO.getName());
        owner.setPhoneNumber(ownerDTO.getPhoneNumber());
        owner.setCity(ownerDTO.getCity());
        adById.setOwner(owner);
        announcementRepository.save(adById);
    }

    @Override
    public void updatePriceOfAd(long adId, PriceDTO priceDTO) {
        Announcement adById = (Announcement) getAdById(adId);
        Price adByIdPrice = (Price) adById.getPrice();
        adByIdPrice.setCurrency(Currency.valueOf(priceDTO.getCurrency()));
        adByIdPrice.setValue(priceDTO.getValue());
        adById.setPrice(adByIdPrice);
        announcementRepository.save(adById);
    }

    public void checkBrandSearchCriteria(AnnouncementSearchDTO searchDTO) {
        if (searchDTO.getBrandStartId() == 0) {
            searchDTO.setBrandStartId(1);
            searchDTO.setBrandEndId(brandService.getCountOfAllBrands());
        } else {
            long brandEndId = searchDTO.getBrandStartId();
            searchDTO.setBrandEndId(brandEndId);
        }
        log.info("Search DTO from brand check case - "+searchDTO);
    }

    public void checkMotoTypeSearchCriteria(AnnouncementSearchDTO searchDTO) {
        if (searchDTO.getTypeStartId() == 0) {
            searchDTO.setTypeStartId(1);
            searchDTO.setTypeEndId(typeService.getCountOfAllTypes());
        } else {
            long typeEndId = (long) searchDTO.getTypeStartId();
            searchDTO.setTypeEndId(typeEndId);
        }
        log.info("Search DTO from type check case - "+searchDTO);
    }

    public void checkConditionSearchCriteria(AnnouncementSearchDTO searchDTO) {
        if (searchDTO.getConditionStartId() == 0) {
            searchDTO.setConditionStartId(1);
            searchDTO.setConditionEndId(conditionService.getCountOfAllConditions());
        } else {
            long conditionEndId = searchDTO.getConditionStartId();
            searchDTO.setConditionEndId(conditionEndId);
        }
        log.info("Search DTO from condition check case - "+searchDTO);
    }

    public void checkEngineTypeSearchCriteria(AnnouncementSearchDTO searchDTO) {
        if (searchDTO.getEngineTypeStartId() == 0) {
            searchDTO.setEngineTypeStartId(1);
            searchDTO.setEngineTypeEndId(engineTypeService.getCountOfAllEngineTypes());
        } else {
            long engineTypeEndId = (long) searchDTO.getEngineTypeStartId();
            searchDTO.setEngineTypeEndId(engineTypeEndId);
        }
        log.info("Search DTO from engine type check case - "+searchDTO);
    }

    public void checkDriveTypeSearchCriteria(AnnouncementSearchDTO searchDTO) {
        if (searchDTO.getDriveTypeStartId() == 0) {
            searchDTO.setDriveTypeStartId(1);
            searchDTO.setDriveTypeEndId(driveTypeService.getCountOfAllDriveTypes());
        } else {
            long driveTypeEndId = (long) searchDTO.getDriveTypeStartId();
            searchDTO.setDriveTypeEndId(driveTypeEndId);
        }
        log.info("Search DTO from drive type check case - "+searchDTO);
    }

    public void checkYearOfIssueSearchCriteria(AnnouncementSearchDTO searchDTO) {
        int lowestYearOfIssue = (int) searchDTO.getLowestYearOfIssue();
        int highestYearOfIssue = (int) searchDTO.getHighestYearOfIssue();
        if (highestYearOfIssue == 1980) {
            highestYearOfIssue = 2021;
            searchDTO.setHighestYearOfIssue(highestYearOfIssue);
        }
        if (lowestYearOfIssue > highestYearOfIssue) {
            searchDTO.setHighestYearOfIssue(lowestYearOfIssue);
            searchDTO.setLowestYearOfIssue(highestYearOfIssue);
        }
        log.info("Search DTO from year of issue check case - "+searchDTO);
    }

    public void checkEngineVolumeSearchCriteria(AnnouncementSearchDTO searchDTO) {
        int lowestEngineVolume = (int) searchDTO.getLowestEngineVolume();
        int highestEngineVolume = (int) searchDTO.getHighestEngineVolume();
        if (highestEngineVolume == 0) {
            highestEngineVolume = 1800;
            searchDTO.setHighestEngineVolume(highestEngineVolume);
        }
        if (lowestEngineVolume > highestEngineVolume) {
            searchDTO.setHighestEngineVolume(lowestEngineVolume);
            searchDTO.setLowestEngineVolume(highestEngineVolume);
        }
        log.info("Search DTO from engine volume check case - "+searchDTO);
    }

    public void checkPriceSearchCriteria(AnnouncementSearchDTO searchDTO) {
        int lowestPrice = (int) searchDTO.getLowestPrice();
        int highestPrice = (int) searchDTO.getHighestPrice();
        if (highestPrice == 0) {
            highestPrice = 100000;
            searchDTO.setHighestPrice(highestPrice);
        }
        if (lowestPrice > highestPrice) {
            searchDTO.setHighestPrice(lowestPrice);
            searchDTO.setLowestPrice(highestPrice);
        }
        log.info("Search DTO from price check case - "+searchDTO);
    }



}
