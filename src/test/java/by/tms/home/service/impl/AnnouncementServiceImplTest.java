package by.tms.home.service.impl;

import by.tms.home.entity.*;
import by.tms.home.entity.exception.EntityNotFoundException;
import by.tms.home.entity.motorcycle.*;
import by.tms.home.repository.AnnouncementRepository;
import by.tms.home.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnnouncementServiceImplTest {

    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private AnnouncementServiceImpl announcementServiceImpl;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MotorcycleModelService motorcycleModelService;
    @Autowired
    private MotorcycleTypeService typeService;
    @Autowired
    private MotorcycleConditionService conditionService;
    @Autowired
    private EngineTypeService engineTypeService;
    @Autowired
    private DriveTypeService driveTypeService;
    @Autowired
    private MotorcycleService motorcycleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CloudinaryService cloudinaryService;
    private Announcement announcement;
    private Motorcycle motorcycle;
    private MotorcycleCondition condition;
    private MotorcycleType motorcycleType;
    private EngineType engineType;
    private DriveType driveType;
    private Brand brand;
    private MotorcycleModel motorcycleModel;
    private Owner owner;
    private Price price;
    private Image photo;
    private User user;


    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1);
        brand.setNameOfBrand("BMW");
        motorcycleModel = new MotorcycleModel(1, "GS", brand);
        condition = new MotorcycleCondition(1, "NEW");
        engineType = new EngineType(1, "ROW");
        driveType = new DriveType(1, "CHAIN");
        motorcycleType = new MotorcycleType(1, "SPORT");
        motorcycle = new Motorcycle(1,brand , motorcycleModel, motorcycleType,
                2001, condition, 4, engineType, 600, driveType, 34000);
        user = new User(1, "username", "Burov6011", "Peter", "Burov6011@gmail.com", "+375295117730", "Minsk",
                Role.USER, new ArrayList<>());
        price = new Price(1, Currency.USD, 2300);
        announcement = new Announcement(1, motorcycle, owner, price, true, true, "comment",
                "", new ArrayList<>(), user);


    }

    @Test
    @Order(1)
    void addAdToDatabase() {
        userService.saveUserInDatabase(user);
        owner = userService.getOwnerFromUser(user.getUsername());
        owner.setId(1);
        announcement.setOwner(owner);
        brandService.addToStorage(brand);
        motorcycleModelService.addToStorage(motorcycleModel);
        driveTypeService.addToStorage(driveType);
        engineTypeService.addToStorage(engineType);
        conditionService.addToStorage(condition);
        typeService.addToStorage(motorcycleType);
        Motorcycle actualMotorcycle = (Motorcycle) motorcycleService.saveMotorcycle(motorcycle);
        Announcement actualAD = (Announcement) announcementService.addAdToDatabase(announcement);
        assertEquals(announcement, actualAD);
    }

    @Test
    @Order(2)
    void getAdById() {
        owner = userService.getOwnerFromUser(user.getUsername());
        owner.setId(1);
        announcement.setOwner(owner);
        Announcement actualADById = (Announcement) announcementService.getAdById(announcement.getId());
        announcement.setDateOfPublishing(actualADById.getDateOfPublishing());
        assertEquals(announcement, actualADById);
    }

    @Test
    @Order(24)
    void deleteAdById() {
        announcementService.deleteAdById(announcement.getId());
        Throwable thrown = assertThrows(EntityNotFoundException.class, ()->
                announcementService.getAdById(announcement.getId()));
        assertNotNull(thrown.getMessage());
    }

    @Test
    @Order(3)
    void setDateOfPublishingToAd() {
        announcementService.setDateOfPublishingToAd(announcement);
        String actualDateOfPublishing = (String) announcement.getDateOfPublishing();
        Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{1,2}");
        boolean actualMatches = (boolean) pattern.matcher(actualDateOfPublishing).matches();
        assertTrue(actualMatches);
    }

    @Test
    @Order(4)
    void getAllAnnouncements() {
        int expectedSize = 1;
        int actualSize = (int) announcementService.getAllAnnouncements().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Order(5)
    void findAdBySearchCriteria() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(0,0,0,0,0,0,0,0,0,0,1980,1980,0,0,0,0);
        int actualSize = (int) announcementService.findAdBySearchCriteria(searchDTOForTransform).size();
        int expectedSize = 1;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Order(6)
    void createReadyMadeSearchDTO() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(0,0,0,0,0,0,0,0,0,0,1980,1980,0,0,0,0);
        AnnouncementSearchDTO expectedDTO = new AnnouncementSearchDTO(1,1,1,1,1,1,1,1,1,1,1980,2021,0,1800,0,100000);
        AnnouncementSearchDTO actualDTO = (AnnouncementSearchDTO) announcementService.createReadyMadeSearchDTO(searchDTOForTransform);
        assertEquals(expectedDTO, actualDTO);
    }

    @Test
    @Order(7)
    void getRandomAdToHomePage() {
        int actualSize = (int) announcementService.getRandomAdToHomePage(1).size();
        int expectedSize = 1;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Order(8)
    void isAnnouncementHasNoPhotos() {
        boolean actualResult = (boolean) announcementService.isAnnouncementHasNoPhotos(announcement.getId());
        assertTrue(actualResult);
    }

    @Test
    @Order(9)
    void addDefaultPhotoToAd() {
        Image defaultImage = cloudinaryService.getDefaultImage();
        announcementService.addDefaultPhotoToAd(announcement.getId(), defaultImage);
        List<Image> photos = (List<Image>) announcementService.getAdById(announcement.getId()).getPhotos();
        Image image = (Image) photos.get(0);
        String expectedUrl = "https://res.cloudinary.com/dh6qfegkp/image/upload/v1619700112/noimage_file_lbwhsc.jpg";
        String actualUrl = (String) image.getUrl();
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    @Order(10)
    void photoCheckAndFinishOrSetDefaultPhoto() {
        announcementService.photoCheckAndFinishOrSetDefaultPhoto(announcement.getId());
        Announcement adById = (Announcement) announcementService.getAdById(announcement.getId());
        int actualSize = (int) adById.getPhotos().size();
        int expectedSize = 1;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    @Order(11)
    void updateAdComments() {
        String expectedComment = "new beautiful comment!";
        announcementService.updateAdComments(announcement.getId(), expectedComment);
        Announcement adById = (Announcement) announcementService.getAdById(announcement.getId());
        String actualComment = (String) adById.getComments();
        assertEquals(expectedComment, actualComment);
    }

    @Test
    @Order(12)
    void updateMileage() {
        int expectedMileage = 45000;
        announcementService.updateMileage(announcement.getId(), expectedMileage);
        Announcement adById = (Announcement) announcementService.getAdById(announcement.getId());
        int actualMileage = (int) adById.getMotorcycle().getMileage();
        assertEquals(expectedMileage, actualMileage);
    }

    @Test
    @Order(13)
    void updateCondition() {
        MotorcycleCondition expectedCondition = new MotorcycleCondition(2, "BREAK");
        conditionService.addToStorage(expectedCondition);
        announcementService.updateCondition(announcement.getId(), expectedCondition.getId());
        Announcement adById = (Announcement) announcementService.getAdById(announcement.getId());
        MotorcycleCondition actualCondition = (MotorcycleCondition) adById.getMotorcycle().getCondition();
        assertEquals(expectedCondition, actualCondition);
    }

    @Test
    @Order(14)
    void updateOwnerInfo() {
        String ownerName = "Sergey";
        String ownerPhone = "80298989548";
        String ownerCity = "Moscow";
        OwnerDTO ownerDTO = new OwnerDTO(ownerName, ownerPhone, ownerCity );
        Owner expectedOwner = new Owner(1, ownerName, ownerPhone, ownerCity);
        announcementService.updateOwnerInfo(announcement.getId(), ownerDTO);
        Announcement adById = (Announcement) announcementService.getAdById(announcement.getId());
        Owner actualOwner = (Owner) adById.getOwner();
        assertEquals(expectedOwner, actualOwner);
    }

    @Test
    @Order(15)
    void updatePriceOfAd() {
        PriceDTO priceDTO = new PriceDTO("EUR", "2500");
        Price expectedPrice = new Price(1, Currency.EUR, 2500);
        announcementService.updatePriceOfAd(announcement.getId(), priceDTO);
        Announcement adById = (Announcement) announcementService.getAdById(announcement.getId());
        Price actualPrice = (Price) adById.getPrice();
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    @Order(16)
    void checkBrandSearchCriteria() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(1,0,0,0,0,0,0,0,0,0,1980,1980,0,0,0,0);
        announcementServiceImpl.checkBrandSearchCriteria(searchDTOForTransform);
        int actualBrandStartId = (int) searchDTOForTransform.getBrandStartId();
        int actualBrandEndId = (int) searchDTOForTransform.getBrandEndId();
        assertEquals(actualBrandStartId, actualBrandEndId);
    }

    @Test
    @Order(17)
    void checkMotoTypeSearchCriteria() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(1,0,1,0,0,0,0,0,0,0,1980,1980,0,0,0,0);
        announcementServiceImpl.checkMotoTypeSearchCriteria(searchDTOForTransform);
        int typeStartId = (int) searchDTOForTransform.getTypeStartId();
        int typeEndId = (int) searchDTOForTransform.getTypeEndId();
        assertEquals(typeStartId, typeEndId);
    }

    @Test
    @Order(18)
    void checkConditionSearchCriteria() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(1,0,1,0,1,0,0,0,0,0,1980,1980,0,0,0,0);
        announcementServiceImpl.checkConditionSearchCriteria(searchDTOForTransform);
        int conditionStartId = (int) searchDTOForTransform.getConditionStartId();
        int conditionEndId = (int) searchDTOForTransform.getConditionEndId();
        assertEquals(conditionStartId, conditionEndId);
    }

    @Test
    @Order(19)
    void checkEngineTypeSearchCriteria() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(1,0,1,0,1,0,1,0,0,0,1980,1980,0,0,0,0);
        announcementServiceImpl.checkEngineTypeSearchCriteria(searchDTOForTransform);
        int engineTypeStartId = (int) searchDTOForTransform.getEngineTypeStartId();
        int engineTypeEndId = (int) searchDTOForTransform.getEngineTypeEndId();
        assertEquals(engineTypeStartId, engineTypeEndId);
    }

    @Test
    @Order(20)
    void checkDriveTypeSearchCriteria() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(1,0,1,0,1,0,1,0,1,0,1980,1980,0,0,0,0);
        announcementServiceImpl.checkDriveTypeSearchCriteria(searchDTOForTransform);
        int driveTypeStartId = (int) searchDTOForTransform.getDriveTypeStartId();
        int driveTypeEndId = (int) searchDTOForTransform.getDriveTypeEndId();
        assertEquals(driveTypeStartId, driveTypeEndId);
    }

    @Test
    @Order(21)
    void checkYearOfIssueSearchCriteria() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(1,0,1,0,1,0,1,0,1,0,1980,1980,0,0,0,0);
        announcementServiceImpl.checkYearOfIssueSearchCriteria(searchDTOForTransform);
        int actualHighestYearOfIssue = (int) searchDTOForTransform.getHighestYearOfIssue();
        int expectedHighYear = 2021;
        assertEquals(expectedHighYear, actualHighestYearOfIssue);
    }

    @Test
    @Order(22)
    void checkEngineVolumeSearchCriteria() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(1,0,1,0,1,0,1,0,1,0,1980,1980,0,0,0,0);
        announcementServiceImpl.checkEngineVolumeSearchCriteria(searchDTOForTransform);
        int actualHighestEngineVolume = (int) searchDTOForTransform.getHighestEngineVolume();
        int expectedHighEngineVolume = 1800;
        assertEquals(expectedHighEngineVolume, actualHighestEngineVolume);
    }

    @Test
    @Order(23)
    void checkPriceSearchCriteria() {
        AnnouncementSearchDTO searchDTOForTransform = new AnnouncementSearchDTO(1,0,1,0,1,0,1,0,1,0,1980,1980,0,0,0,0);
        announcementServiceImpl.checkPriceSearchCriteria(searchDTOForTransform);
        int actualHighestPrice = (int) searchDTOForTransform.getHighestPrice();
        int expectedHighPrice = 100000;
        assertEquals(expectedHighPrice, actualHighestPrice);
    }
}