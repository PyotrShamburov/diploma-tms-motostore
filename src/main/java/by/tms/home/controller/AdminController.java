package by.tms.home.controller;

import by.tms.home.entity.motorcycle.*;
import by.tms.home.repository.motorcycle.BrandRepository;
import by.tms.home.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/motorcycle")
@Slf4j
public class AdminController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private DriveTypeService driveTypeService;
    @Autowired
    private EngineTypeService engineTypeService;
    @Autowired
    private MotorcycleConditionService motorcycleConditionService;
    @Autowired
    private MotorcycleModelService motorcycleModelService;
    @Autowired
    private MotorcycleTypeService motorcycleTypeService;

    @GetMapping(path = "/brand")
    public ModelAndView getPageForAdditionBrand(ModelAndView modelAndView) {
        modelAndView.setViewName("additionBrand");
        modelAndView.addObject("newBrand", new Brand());
        modelAndView.addObject("addedBrands", brandService.getAllBrands());
        log.info("Brands already saved in system: "+brandService.getAllBrands());
        return modelAndView;
    }

    @PostMapping(path = "/brand")
    public ModelAndView addBrandToDatabase(@Valid @ModelAttribute("newBrand") Brand brand, BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
        modelAndView.setViewName("additionBrand");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("isError", true);
            modelAndView.setViewName("redirect:/admin/motorcycle/brand");
            log.warn("Binding errors POST method Add Brand!");
        } else {
            String formattedName = (String) brand.getNameOfBrand().toUpperCase().trim();
            log.info("POST method add brand without errors! Name of Brand: "+formattedName);
            brand.setNameOfBrand(formattedName);
            brandService.addToStorage(brand);
            modelAndView.setViewName("redirect:/admin/motorcycle/brand");
        }
        return modelAndView;
    }

    @PostMapping(path = "/brand/delete")
    public ModelAndView deleteBrandFromDatabase(@RequestParam("brandId") long id, ModelAndView modelAndView) {
        brandService.deleteBrandById(id);
        log.info("POST method deleting brand with ID :"+id);
        modelAndView.setViewName("redirect:/admin/motorcycle/brand");
        return modelAndView;
    }

    @GetMapping(path = "/drive")
    public ModelAndView getPageForAdditionDriveType(ModelAndView modelAndView) {
        modelAndView.setViewName("additionDriveType");
        modelAndView.addObject("newDriveType", new DriveType());
        modelAndView.addObject("driveTypes", driveTypeService.getAllDriveTypes());
        log.info("Drive types already saved in system: "+driveTypeService.getAllDriveTypes());
        return modelAndView;
    }

    @PostMapping(path = "/drive")
    public ModelAndView addDriveTypeToStorage(@Valid @ModelAttribute("newDriveType") DriveType driveType, BindingResult bindingResult,
                                              ModelAndView modelAndView) {
        modelAndView.setViewName("additionDriveType");
        if (bindingResult.hasErrors()) {
            log.warn("Binding errors POST method Add drive type!");
            modelAndView.setViewName("additionDriveType");
        } else {
            String formattedName = (String) driveType.getNameOfDriveType().toUpperCase().trim();
            driveType.setNameOfDriveType(formattedName);
            driveTypeService.addToStorage(driveType);
            log.info("POST method add drive type without errors! Name of drive type: "+formattedName);
            modelAndView.setViewName("redirect:/admin/motorcycle/drive");
        }
        return modelAndView;
    }

    @PostMapping(path = "/drive/delete")
    public ModelAndView deleteDriveTypeFromDatabase(@RequestParam("driveTypeId") long id, ModelAndView modelAndView) {
        driveTypeService.deleteDriveTypeById(id);
        log.info("POST method deleting drive type with ID :"+id);
        modelAndView.setViewName("redirect:/admin/motorcycle/drive");
        return modelAndView;
    }

    @GetMapping(path = "/engine")
    public ModelAndView getPageForAdditionEngineType(ModelAndView modelAndView) {
        modelAndView.setViewName("additionEngineType");
        modelAndView.addObject("newEngineType", new EngineType());
        modelAndView.addObject("engineTypes", engineTypeService.getAllEnginesType());
        log.info("Engine types already saved in system: "+engineTypeService.getAllEnginesType());
        return modelAndView;
    }

    @PostMapping(path = "/engine")
    public ModelAndView addEngineTypeToStorage(@Valid @ModelAttribute("newEngineType") EngineType engineType, BindingResult bindingResult,
                                               ModelAndView modelAndView) {
        modelAndView.setViewName("additionEngineType");
        if (bindingResult.hasErrors()) {
            log.warn("Binding errors POST method Add engine type!");
            modelAndView.setViewName("additionEngineType");
        } else {
            String formattedName = (String) engineType.getNameOfEngineType().toUpperCase().trim();
            engineType.setNameOfEngineType(formattedName);
            engineTypeService.addToStorage(engineType);
            log.info("POST method add engine type without errors! Name of engine type: "+formattedName);
            modelAndView.setViewName("redirect:/admin/motorcycle/engine");
        }
        return modelAndView;
    }

    @PostMapping(path = "/engine/delete")
    public ModelAndView deleteEngineTypeFromDatabase(@RequestParam("engineTypeId") long id, ModelAndView modelAndView) {
        engineTypeService.deleteEngineTypeById(id);
        log.info("POST method deleting engine type with ID :"+id);
        modelAndView.setViewName("redirect:/admin/motorcycle/engine");
        return modelAndView;
    }

    @GetMapping(path = "/condition")
    public ModelAndView getPadeForAdditionCondition(ModelAndView modelAndView) {
        modelAndView.setViewName("additionCondition");
        modelAndView.addObject("newCondition", new MotorcycleCondition());
        modelAndView.addObject("conditions", motorcycleConditionService.getAllConditions());
        log.info("Conditions already saved in system: "+motorcycleConditionService.getAllConditions());
        return modelAndView;
    }

    @PostMapping(path = "/condition")
    public ModelAndView addConditionToStorage(@Valid @ModelAttribute("newCondition") MotorcycleCondition motorcycleCondition,
                                              BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("additionCondition");
        if (bindingResult.hasErrors()) {
            log.warn("Binding errors POST method Add condition!");
            modelAndView.setViewName("additionCondition");
        } else {
            String formattedName = (String) motorcycleCondition.getNameOfCondition().toUpperCase().trim();
            motorcycleCondition.setNameOfCondition(formattedName);
            motorcycleConditionService.addToStorage(motorcycleCondition);
            log.info("POST method add condition without errors! Name of condition: "+formattedName);
            modelAndView.setViewName("redirect:/admin/motorcycle/condition");
        }
        return modelAndView;
    }

    @PostMapping(path = "/condition/delete")
    public ModelAndView deleteConditionFromDatabase(@RequestParam("conditionId")long id, ModelAndView modelAndView) {
        motorcycleConditionService.deleteMotorcycleConditionById(id);
        log.info("POST method deleting condition with ID :"+id);
        modelAndView.setViewName("redirect:/admin/motorcycle/condition");
        return modelAndView;
    }

    @GetMapping(path = "/model")
    public ModelAndView getPageForAdditionModel(ModelAndView modelAndView) {
        modelAndView.setViewName("additionModel");
        List<Brand> allBrands = (List<Brand>) brandService.getAllBrands();
        modelAndView.addObject("allBrands", allBrands);
        modelAndView.addObject("newModelDTO", new ModelDTO());
        log.info("GET method adding moto model!");
        return modelAndView;
    }

    @PostMapping(path = "/model")
    public ModelAndView addModelToStorage(@Valid @ModelAttribute("newModelDTO") ModelDTO modelDTO, BindingResult bindingResult,
                                          MotorcycleModel motorcycleModel, ModelAndView modelAndView) {
        modelAndView.setViewName("additionModel");
        if (bindingResult.hasErrors()) {
            log.warn("Binding errors POST method Add model!");
            modelAndView.setViewName("additionModel");
        } else {
            String formattedName = (String) modelDTO.getNameOfModel().toUpperCase().trim();
            modelDTO.setNameOfModel(formattedName);
            MotorcycleModel modelFromModelDTO = (MotorcycleModel) motorcycleModelService.createModelFromModelDTO(motorcycleModel, modelDTO);
            motorcycleModelService.addToStorage(modelFromModelDTO);
            log.info("POST method add model without errors! Name of model: "+formattedName);
            modelAndView.setViewName("redirect:/admin/motorcycle/model");
        }
        return modelAndView;
    }

    @PostMapping(path = "/model/delete")
    public ModelAndView deleteModelFromDatabase(@RequestParam("modelId")long modelId, ModelAndView modelAndView) {
        motorcycleModelService.deleteMotorcycleModelById(modelId);
        log.info("POST method deleting model with ID :"+modelId);
        modelAndView.setViewName("redirect:/admin/motorcycle/model");
        return modelAndView;
    }

    @GetMapping(path = "/type")
    public ModelAndView getPageForAdditionType(ModelAndView modelAndView) {
        modelAndView.setViewName("additionType");
        modelAndView.addObject("newMotorcycleType", new MotorcycleType());
        modelAndView.addObject("motoTypes", motorcycleTypeService.getAllMotoTypes());
        log.info("Types already saved in system: "+motorcycleTypeService.getAllMotoTypes());
        return modelAndView;
    }

    @PostMapping(path = "/type")
    public ModelAndView addTypeToStorage(@Valid @ModelAttribute("newMotorcycleType") MotorcycleType motorcycleType,
                                         BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("additionType");
        if (bindingResult.hasErrors()) {
            log.warn("Binding errors POST method Add moto type!");
            modelAndView.setViewName("additionType");
        } else {
            String formattedName = (String) motorcycleType.getNameOfMotorcycleType().toUpperCase().trim();
            motorcycleType.setNameOfMotorcycleType(formattedName);
            motorcycleTypeService.addToStorage(motorcycleType);
            modelAndView.setViewName("redirect:/admin/motorcycle/type");
            log.info("POST method add type without errors! Name of type: "+formattedName);
        }
        return modelAndView;
    }

    @PostMapping(path = "/type/delete")
    public ModelAndView deleteTypeFromDatabase(@RequestParam("typeId")long id, ModelAndView modelAndView) {
        motorcycleTypeService.deleteMotorcycleTypeById(id);
        log.info("POST method deleting type with ID :"+id);
        modelAndView.setViewName("redirect:/admin/motorcycle/type");
        return modelAndView;
    }


}
