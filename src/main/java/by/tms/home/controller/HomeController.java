package by.tms.home.controller;

import by.tms.home.entity.AnnouncementSearchDTO;
import by.tms.home.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping(path = "/")
@Slf4j
public class HomeController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private MotorcycleTypeService typeService;
    @Autowired
    private EngineTypeService engineTypeService;
    @Autowired
    private DriveTypeService driveTypeService;
    @Autowired
    private MotorcycleConditionService conditionService;
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public ModelAndView getHomePage(ModelAndView modelAndView) {
        modelAndView.setViewName("homePage");
        modelAndView.addObject("allBrands", brandService.getAllBrands());
        modelAndView.addObject("allTypes", typeService.getAllMotoTypes());
        modelAndView.addObject("allEngineTypes", engineTypeService.getAllEnginesType());
        modelAndView.addObject("allDriveTypes", driveTypeService.getAllDriveTypes());
        modelAndView.addObject("allConditions", conditionService.getAllConditions());
        modelAndView.addObject("newSearchAdDTO", new AnnouncementSearchDTO());
        modelAndView.addObject("listOfTopAd", announcementService.getRandomAdToHomePage(3));
        log.info("GET method of home page!");
        return modelAndView;
    }
}
