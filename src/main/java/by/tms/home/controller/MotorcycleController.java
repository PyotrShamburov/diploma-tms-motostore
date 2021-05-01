package by.tms.home.controller;

import by.tms.home.entity.motorcycle.Brand;
import by.tms.home.entity.motorcycle.Motorcycle;
import by.tms.home.entity.motorcycle.MotorcycleDTO;
import by.tms.home.entity.motorcycle.MotorcycleModel;
import by.tms.home.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/moto")
@Slf4j
public class MotorcycleController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private MotorcycleTypeService motorcycleTypeService;
    @Autowired
    private MotorcycleModelService motorcycleModelService;
    @Autowired
    private MotorcycleConditionService motorcycleConditionService;
    @Autowired
    private EngineTypeService engineTypeService;
    @Autowired
    private DriveTypeService driveTypeService;
    @Autowired
    private MotorcycleService motorcycleService;

    @GetMapping(path = "/add/brand")
    public ModelAndView getBrandSelectionPage(ModelAndView modelAndView) {
        modelAndView.setViewName("selectBrandForMoto");
        List<Brand> allBrands = (List<Brand>) brandService.getAllBrands();
        log.info("All brands: " + allBrands);
        modelAndView.addObject("allBrands", allBrands);
        modelAndView.addObject("newMotorcycleDTO", new MotorcycleDTO());
        modelAndView.addObject("newMotorcycle", new Motorcycle());
        return modelAndView;
    }

    @PostMapping(path = "/add/brand")
    public ModelAndView addBrandToMoto(@ModelAttribute("newMotorcycleDTO") MotorcycleDTO motorcycleDTO,
                                       @ModelAttribute("newMotorcycle") Motorcycle motorcycle, HttpSession httpSession,
                                       ModelAndView modelAndView) {
        modelAndView.setViewName("selectBrandForMoto");
        Brand brandByNameFromDTO = (Brand) brandService.getByNameOfBrand(motorcycleDTO.getNameOfBrand());
        motorcycle.setBrand(brandByNameFromDTO);
        httpSession.setAttribute("brandOfNewMoto", brandByNameFromDTO);
        httpSession.setAttribute("newMotorcycle", motorcycle);
        modelAndView.setViewName("redirect:/moto/add/model");
        log.info("Moto with added Brand :" + motorcycle);
        return modelAndView;
    }

    @GetMapping(path = "/add/model")
    public ModelAndView getModelSelectionPage(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("selectModelForMoto");
        Brand brandOfNewMoto = (Brand) httpSession.getAttribute("brandOfNewMoto");
        log.info("Selected brand :" + brandOfNewMoto);
        List<MotorcycleModel> modelsOfBrand = motorcycleModelService.getModelsByBrandId(brandOfNewMoto.getId());
        modelAndView.addObject("modelsOfBrand", modelsOfBrand);
        modelAndView.addObject("newMotorcycleDTO", new MotorcycleDTO());
        return modelAndView;
    }

    @PostMapping(path = "/add/model")
    public ModelAndView addModelForMoto(@ModelAttribute("newMotorcycleDTO") MotorcycleDTO motorcycleDTO, HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("selectModelForMoto");
        Motorcycle motorcycleFromSession = (Motorcycle) httpSession.getAttribute("newMotorcycle");
        MotorcycleModel motorcycleModelByNameFromDTO = motorcycleModelService.getMotorcycleModelByName(motorcycleDTO.getNameOfModel());
        motorcycleFromSession.setModel(motorcycleModelByNameFromDTO);
        httpSession.setAttribute("newMotorcycle", motorcycleFromSession);
        modelAndView.setViewName("redirect:/moto/add/info");
        log.info("post method add/model" + motorcycleFromSession + " hash" + motorcycleDTO.hashCode());
        return modelAndView;
    }

    @GetMapping(path = "/add/info")
    public ModelAndView getInfoSelectionPage(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("selectCharacteristicsForMoto");
        modelAndView.addObject("motoTypes", motorcycleTypeService.getAllMotoTypes());
        modelAndView.addObject("engineTypes", engineTypeService.getAllEnginesType());
        modelAndView.addObject("conditions", motorcycleConditionService.getAllConditions());
        modelAndView.addObject("driveTypes", driveTypeService.getAllDriveTypes());
        modelAndView.addObject("newMotorcycleDTO", new MotorcycleDTO());
        log.info("GET method addition info to motorcycle!");
        return modelAndView;
    }

    @PostMapping(path = "/add/info")
    public ModelAndView addInfoForMoto(@Valid @ModelAttribute("newMotorcycleDTO") MotorcycleDTO motorcycleDTO, BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes, HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("selectCharacteristicsForMoto");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("isError", true);
            log.warn("Errors during addition info to motorcycle!");
            modelAndView.setViewName("redirect:/moto/add/info");
        } else {
            Motorcycle motorcycleFromSession = (Motorcycle) httpSession.getAttribute("newMotorcycle");
            Motorcycle motorcycle = (Motorcycle) motorcycleService.setInfoFieldsFromDTO(motorcycleFromSession, motorcycleDTO);
            log.info("post method add/model" + motorcycle + " hash" + motorcycle.hashCode());
            Motorcycle savedMotorcycle = (Motorcycle) motorcycleService.saveMotorcycle(motorcycle);
            httpSession.setAttribute("newSavedMoto", savedMotorcycle);
            modelAndView.setViewName("redirect:/ad/create");
        }
        return modelAndView;
    }
}
