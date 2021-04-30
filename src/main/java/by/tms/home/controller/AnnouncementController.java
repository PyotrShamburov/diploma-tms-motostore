package by.tms.home.controller;

import by.tms.home.entity.*;
import by.tms.home.entity.motorcycle.*;
import by.tms.home.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/ad")
@Slf4j
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MotorcycleConditionService conditionService;
    @Autowired
    private MotorcycleTypeService typeService;
    @Autowired
    private DriveTypeService driveTypeService;
    @Autowired
    private EngineTypeService  engineTypeService;

    @GetMapping(path = "/create")
    public ModelAndView getPageForCreateAd(ModelAndView modelAndView) {
        modelAndView.setViewName("selectBargainExchangeForAd");
        modelAndView.addObject("newAnnouncement", new Announcement());
        return modelAndView;
    }

    @PostMapping(path = "/create")
    public ModelAndView createNewAd(@ModelAttribute("newAnnouncement")Announcement announcement, HttpSession httpSession,
                                    Principal principal, ModelAndView modelAndView) {
        modelAndView.setViewName("selectBargainExchangeForAd");
        String username = (String) principal.getName();
        Motorcycle newSavedMoto = (Motorcycle) httpSession.getAttribute("newSavedMoto");
        Owner ownerFromUser = (Owner) userService.getOwnerFromUser(username);
        announcement.setOwner(ownerFromUser);
        announcement.setMotorcycle(newSavedMoto);
        log.info("Ad without price :"+announcement);
        httpSession.setAttribute("newAnnouncement", announcement);
        modelAndView.setViewName("redirect:/ad/price");
        return modelAndView;

    }

    @GetMapping(path = "/price")
    public ModelAndView getPageForAdditionPrice(ModelAndView modelAndView) {
        modelAndView.setViewName("addPriceToAnnouncement");
        modelAndView.addObject("currencyList", Currency.values());
        modelAndView.addObject("newPrice", new Price());
        return modelAndView;
    }

    @PostMapping(path = "/price")
    public ModelAndView createAndAddPriceToAd(@Valid @ModelAttribute("newPrice")Price price, BindingResult bindingResult,
                                              Principal principal, HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("addPriceToAnnouncement");
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("addPriceToAnnouncement");
        } else {
            Announcement announcementFromSession = (Announcement) httpSession.getAttribute("newAnnouncement");
            announcementFromSession.setPrice(price);
            announcementFromSession.setPhotos(new ArrayList<>());
            String username = (String) principal.getName();
            User byUsername = (User) userService.getByUsername(username);
            announcementFromSession.setUser(byUsername);
            Announcement announcementFromDataBase = (Announcement) announcementService.addAdToDatabase(announcementFromSession);
            httpSession.setAttribute("newAdId", announcementFromDataBase.getId());
            modelAndView.setViewName("redirect:/ad/photos");
            log.info("Saved in database AD without photo :" + announcementFromSession);
        }
        return modelAndView;
    }

    @GetMapping(path = "/photos")
    public ModelAndView getPhotoAdditionPage(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("additionPhotoToAd");
        long newAdId = (long) httpSession.getAttribute("newAdId");
        Announcement adById = (Announcement) announcementService.getAdById(newAdId);
        modelAndView.addObject("photos", adById.getPhotos());
        log.info("Add photo step AD: "+adById);
        return modelAndView;
    }

    @PostMapping(path = "/photos")
    public ModelAndView addPhotoToAnnouncement(HttpSession httpSession, @RequestParam("file")MultipartFile multipartFile,
                                               ModelAndView modelAndView) {
        modelAndView.setViewName("additionPhotoToAd");
        long newAdId = (long) httpSession.getAttribute("newAdId");
        Announcement adById = (Announcement) announcementService.getAdById(newAdId);
        log.info("POST method for uploading photo AD is :"+adById);
        Image savedImage = (Image) imageService.createImageFromFileAndSetAd(adById, multipartFile);
        modelAndView.setViewName("redirect:/ad/photos");
        log.info("New uploaded image :"+savedImage);
        return modelAndView;
    }

    @PostMapping(path = "/photos/delete")
    public ModelAndView deletePhotoFromAd(@RequestParam("photoId")long photoId, ModelAndView modelAndView) {
        modelAndView.setViewName("additionPhotoToAd");
        log.info("Photo ID to delete :"+photoId);
        imageService.deleteImageById(photoId);
        modelAndView.setViewName("redirect:/ad/photos");
        return modelAndView;
    }

    @PostMapping(path = "/photos/finish")
    public ModelAndView endOfPhotoAdditionAndCreateAd(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("additionPhotoToAd");
        long newAdId = (long) httpSession.getAttribute("newAdId");
        announcementService.photoCheckAndFinishOrSetDefaultPhoto(newAdId);
        log.info("Creating of AD is over!");
        modelAndView.setViewName("redirect:/ad/success");
        return modelAndView;
    }

    @GetMapping(path = "/success")
    public ModelAndView getAfterAdditionAdSuccessPage(ModelAndView modelAndView) {
        modelAndView.setViewName("successAdditionPage");
        return modelAndView;
    }

    @GetMapping(path = "/show/{adId}")
    public ModelAndView getAnnouncementPage(@PathVariable("adId")long adId, ModelAndView modelAndView) {
        modelAndView.setViewName("announcementPage");
        Announcement adById = (Announcement) announcementService.getAdById(adId);
        modelAndView.addObject("adId", adById.getId());
        modelAndView.addObject("motorcycle", adById.getMotorcycle());
        modelAndView.addObject("owner", adById.getOwner());
        modelAndView.addObject("price", adById.getPrice());
        modelAndView.addObject("info", adById.getComments());
        modelAndView.addObject("bargain", adById.isBargain());
        modelAndView.addObject("exchange", adById.isExchange());
        modelAndView.addObject("dateOfPublishing", adById.getDateOfPublishing());
        modelAndView.addObject("photos", adById.getPhotos());
        return modelAndView;
    }


    @GetMapping(path = "/show/all")
    public ModelAndView getPageToWatchAllAd(ModelAndView modelAndView) {
        modelAndView.setViewName("allAnnouncementsViewPage");
        modelAndView.addObject("allBrands", brandService.getAllBrands());
        modelAndView.addObject("allTypes", typeService.getAllMotoTypes());
        modelAndView.addObject("allEngineTypes", engineTypeService.getAllEnginesType());
        modelAndView.addObject("allDriveTypes", driveTypeService.getAllDriveTypes());
        modelAndView.addObject("allConditions", conditionService.getAllConditions());
        modelAndView.addObject("newSearchAdDTO", new AnnouncementSearchDTO());
        List<Announcement> allAnnouncements = (List<Announcement>) announcementService.getAllAnnouncements();
        modelAndView.addObject("announcements", allAnnouncements);
        return modelAndView;
    }

    @PostMapping(path = "/search")
    public ModelAndView getSortedListOfAd(@ModelAttribute("newSearchAdDTO")AnnouncementSearchDTO announcementSearchDTO, HttpSession httpSession,
                                          ModelAndView modelAndView) {
        modelAndView.setViewName("homePage");
        List<Announcement> adBySearchCriteria = (List<Announcement>) announcementService.findAdBySearchCriteria(announcementSearchDTO);
        modelAndView.setViewName("redirect:/ad/search/result");
        httpSession.setAttribute("announcements", adBySearchCriteria);
        log.info("List with result of search in POST method /search :"+adBySearchCriteria);
        return modelAndView;
    }

    @GetMapping(path = "/search/result")
    public ModelAndView getPageToWatchFilteredAd(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("allAnnouncementsViewPage");
        modelAndView.addObject("allBrands", brandService.getAllBrands());
        modelAndView.addObject("allTypes", typeService.getAllMotoTypes());
        modelAndView.addObject("allEngineTypes", engineTypeService.getAllEnginesType());
        modelAndView.addObject("allDriveTypes", driveTypeService.getAllDriveTypes());
        modelAndView.addObject("allConditions", conditionService.getAllConditions());
        modelAndView.addObject("newSearchAdDTO", new AnnouncementSearchDTO());
        List<Announcement> resultOfSearch = (List<Announcement>) httpSession.getAttribute("announcements");
        log.info("List with result of search GET method /search/result :"+resultOfSearch);
        modelAndView.addObject("announcements",resultOfSearch);
        return modelAndView;
    }
}
