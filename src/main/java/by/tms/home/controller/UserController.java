package by.tms.home.controller;

import by.tms.home.entity.*;
import by.tms.home.entity.motorcycle.MotorcycleCondition;
import by.tms.home.entity.motorcycle.MotorcycleDTO;
import by.tms.home.entity.PriceDTO;
import by.tms.home.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping(path = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private MotorcycleConditionService motorcycleConditionService;
    @Autowired
    private ImageService imageService;

    @GetMapping(path = "/auth")
    public ModelAndView getAuthorizationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("authorizationPage");
        return modelAndView;
    }

    @GetMapping(path = "/reg")
    public ModelAndView getRegistrationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("registrationPage");
        modelAndView.addObject("newUser", new User());
        return modelAndView;
    }

    @PostMapping(path = "/reg")
    public ModelAndView registration(@Valid @ModelAttribute("newUser") User user, BindingResult bindingResult, @RequestParam("confirmPassword") String confirmedPassword,
                                     ModelAndView modelAndView) {
        modelAndView.setViewName("registrationPage");
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registrationPage");
        } else if (confirmedPassword.equals(user.getPassword())) {
            userService.saveUserInDatabase(user);
            modelAndView.setViewName("redirect:/user/auth");
        } else {
            modelAndView.setViewName("registrationPage");
            modelAndView.addObject("message", "Password confirmation failed!");
        }
        return modelAndView;
    }

    @GetMapping(path = "/allAnnouncements")
    public ModelAndView getAllAnnouncementsOfUser(Principal principal, ModelAndView modelAndView) {
        modelAndView.setViewName("allAdOfUser");
        String username = (String) principal.getName();
        User byUsername = (User) userService.getByUsername(username);
        List<Announcement> announcements = (List<Announcement>) byUsername.getAnnouncements();
        modelAndView.addObject("announcements", announcements);
        return modelAndView;
    }

    @GetMapping(path = "/settings")
    public ModelAndView getUserSettingsPage(Principal principal, ModelAndView modelAndView) {
        modelAndView.setViewName("userSettingsPage");
        String username = (String) principal.getName();
        User byUsername = (User) userService.getByUsername(username);
        modelAndView.addObject("activeUser", byUsername);
        return modelAndView;
    }

    @GetMapping(path = "/changeName")
    public ModelAndView getNameChangePage(ModelAndView modelAndView) {
        modelAndView.setViewName("changeUserName");
        return modelAndView;
    }

    @PostMapping(path = "/changeName")
    public ModelAndView changeUserName(@RequestParam("newName") String newFirstName, Principal principal, ModelAndView modelAndView) {
        Pattern pattern = Pattern.compile("^[A-Z]?[a-z]{3,15}$");
        if (pattern.matcher(newFirstName).matches()) {
            String username = principal.getName();
            userService.updateUserFirstName(newFirstName, username);
            modelAndView.addObject("isChanged", true);
        } else {
            modelAndView.addObject("isChanged", false);
            modelAndView.addObject("message", "Wrong format! Only characters(3 - 15)!");
        }
        modelAndView.setViewName("changeUserName");
        return modelAndView;
    }

    @GetMapping(path = "/changePhone")
    public ModelAndView getPhoneChangePage(ModelAndView modelAndView) {
        modelAndView.setViewName("changeUserPhone");
        return modelAndView;
    }

    @PostMapping(path = "/changePhone")
    public ModelAndView changeUserPhone(@RequestParam("phoneNumber") String newPhoneNumber, Principal principal,
                                        ModelAndView modelAndView) {
        Pattern pattern = Pattern.compile("^(\\+\\d{12})|(\\d{11})$");
        if (pattern.matcher(newPhoneNumber).matches()) {
            String username = (String) principal.getName();
            userService.updateUserPhoneNumber(username, newPhoneNumber);
            modelAndView.addObject("isChanged", true);
        } else {
            modelAndView.addObject("isChanged", false);
            modelAndView.addObject("message", "Invalid phone number format!");
        }
        modelAndView.setViewName("changeUserPhone");
        return modelAndView;
    }

    @GetMapping(path = "/changeEmail")
    public ModelAndView getChangeEmailPage(ModelAndView modelAndView) {
        modelAndView.setViewName("changeUserEmail");
        return modelAndView;
    }

    @PostMapping(path = "/changeEmail")
    public ModelAndView changeUserEmail(@RequestParam("newEmail") String newEmail, Principal principal,
                                        ModelAndView modelAndView) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]{2,10}@[A-Za-z0-9.-]{3,6}\\.[A-Za-z]{2,4}");
        if (pattern.matcher(newEmail).matches()) {
            String username = (String) principal.getName();
            userService.updateUserEmail(username, newEmail);
            modelAndView.addObject("isChanged", true);
        } else {
            modelAndView.addObject("isChanged", false);
            modelAndView.addObject("message", "Invalid email format!");
        }
        modelAndView.setViewName("changeUserEmail");
        return modelAndView;
    }

    @GetMapping(path = "/changePassword")
    public ModelAndView getPasswordChangePage(ModelAndView modelAndView) {
        modelAndView.setViewName("changeUserPassword");
        return modelAndView;
    }

    @PostMapping(path = "/changePassword")
    public ModelAndView changeUserPassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,
                                           @RequestParam("confirmPassword") String confirmPassword, Principal principal,
                                           ModelAndView modelAndView) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
        boolean isChanged = false;
        String username = (String) principal.getName();
        if (pattern.matcher(newPassword).matches()) {
            if (newPassword.equals(confirmPassword)) {
                log.info(newPassword + "= new pass equals confirmed pass =" + confirmPassword);
                boolean isOldPassTrue = (boolean) userService.updateUserPassword(username, oldPassword, newPassword);
                if (isOldPassTrue) {
                    log.info("Update password method is done!");
                    isChanged = true;
                } else {
                    modelAndView.addObject("message", "Check old password!");
                }
            } else {
                modelAndView.addObject("message", "Password confirmation failed!");
            }
        } else {
            modelAndView.addObject("message", "New password is not valid!");
        }
        modelAndView.addObject("isChanged", isChanged);
        modelAndView.setViewName("changeUserPassword");
        return modelAndView;
    }

    @PostMapping(path = "/ad/delete/{adId}")
    public ModelAndView deleteAdFromDatabase(@PathVariable("adId") long adId, ModelAndView modelAndView) {
        announcementService.deleteAdById(adId);
        modelAndView.setViewName("redirect:/user/ad/deleted");
        return modelAndView;
    }

    @GetMapping(path = "/ad/deleted")
    public ModelAndView getDeleteSuccessPage(ModelAndView modelAndView) {
        modelAndView.setViewName("successDeletePage");
        return modelAndView;
    }

    @GetMapping(path = "/ad/edit/{adId}")
    public ModelAndView getAdEditPage(@PathVariable("adId")long adId, HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("editAdPage");
        Announcement adById = (Announcement) announcementService.getAdById(adId);
        modelAndView.addObject("ad", adById);
        httpSession.setAttribute("editAdId", adId);
        return modelAndView;
    }

    @PostMapping(path = "/ad/edit")
    public ModelAndView changeComments(@RequestParam("comment")String comment, HttpSession httpSession, RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
        modelAndView.setViewName("editAdPage");
        long editAdId = (long) httpSession.getAttribute("editAdId");
        announcementService.updateAdComments(editAdId, comment);
        modelAndView.addObject("isUpdated", true);
        redirectAttributes.addFlashAttribute("isUpdated", true);
        modelAndView.setViewName("redirect:/user/ad/edit/"+editAdId);
        return modelAndView;
    }

    @GetMapping(path = "/ad/edit/mileage")
    public ModelAndView getMileageEditPage(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("changeMileage");
        long adId = (long) httpSession.getAttribute("editAdId");
        int currentMileage = (int) announcementService.getAdById(adId).getMotorcycle().getMileage();
        modelAndView.addObject("currentMileage", currentMileage);
        modelAndView.addObject("adId", adId);
        return modelAndView;
    }

    @PostMapping(path = "/ad/edit/mileage")
    public ModelAndView changeMileage(@RequestParam("mileage")String mileage,HttpSession httpSession, RedirectAttributes redirectAttributes,
                                      ModelAndView modelAndView) {
        modelAndView.setViewName("changeMileage");
        boolean isUpdated = true;
        if (!"".equals(mileage)) {
            int newMileage = Integer.parseInt(mileage);
            if (newMileage > 0) {
                long editAdId = (long) httpSession.getAttribute("editAdId");
                announcementService.updateMileage(editAdId, newMileage);
            }
        } else {
            isUpdated = false;
            redirectAttributes.addFlashAttribute("message", "Mileage can be only positive number!");
        }
        redirectAttributes.addFlashAttribute("isUpdated", isUpdated);
        modelAndView.setViewName("redirect:/user/ad/edit/mileage");
        return modelAndView;
    }

    @GetMapping(path = "/ad/edit/condition")
    public ModelAndView getConditionChangePage(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("changeCondition");
        long adId = (long) httpSession.getAttribute("editAdId");
        MotorcycleCondition condition = (MotorcycleCondition) announcementService.getAdById(adId).getMotorcycle().getCondition();
        modelAndView.addObject("currentCondition", condition);
        modelAndView.addObject("allConditions", motorcycleConditionService.getAllConditions());
        modelAndView.addObject("newMotoDTO", new MotorcycleDTO());
        modelAndView.addObject("adId", adId);
        return modelAndView;
    }

    @PostMapping(path = "/ad/edit/condition")
    public ModelAndView changeCondition(@ModelAttribute("newMotoDTO")MotorcycleDTO motorcycleDTO, HttpSession httpSession, RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
        modelAndView.setViewName("changeCondition");
        long editAdId = (long) httpSession.getAttribute("editAdId");
        long conditionId = (long) motorcycleDTO.getConditionId();
        announcementService.updateCondition(editAdId, conditionId);
        redirectAttributes.addFlashAttribute("isUpdated", true);
        modelAndView.setViewName("redirect:/user/ad/edit/condition");
        return modelAndView;
    }

    @GetMapping(path = "/ad/edit/owner")
    public ModelAndView getOwnerChangePage(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("changeOwnerInfo");
        long adId = (long) httpSession.getAttribute("editAdId");
        Owner currentOwner = (Owner) announcementService.getAdById(adId).getOwner();
        modelAndView.addObject("currentOwner", currentOwner);
        modelAndView.addObject("newOwnerDTO", new OwnerDTO());
        modelAndView.addObject("adId", adId);
        return modelAndView;
    }

    @PostMapping(path = "/ad/edit/owner")
    public ModelAndView changeOwnerInfo(@Valid @ModelAttribute("newOwnerDTO")OwnerDTO ownerDTO, BindingResult bindingResult,
                                        HttpSession httpSession, RedirectAttributes redirectAttributes,
                                        ModelAndView modelAndView) {
        modelAndView.setViewName("changeOwnerInfo");
        boolean isUpdated = false;
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("changeOwnerInfo");
        } else {
            long adId = (long) httpSession.getAttribute("editAdId");
            announcementService.updateOwnerInfo(adId, ownerDTO);
            isUpdated = true;
        }
        redirectAttributes.addFlashAttribute("isUpdated", isUpdated);
        modelAndView.setViewName("redirect:/user/ad/edit/owner");
        return modelAndView;
    }

    @GetMapping(path = "/ad/edit/price")
    public ModelAndView getPriceChangePage(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("changePrice");
        long adId = (long) httpSession.getAttribute("editAdId");
        Price currentPrice = (Price) announcementService.getAdById(adId).getPrice();
        modelAndView.addObject("currentPrice", currentPrice);
        modelAndView.addObject("allCurrency", Currency.values());
        modelAndView.addObject("newPriceDTO", new PriceDTO());
        modelAndView.addObject("adId", adId);
        return modelAndView;
    }

    @PostMapping(path = "/ad/edit/price")
    public ModelAndView changePriceOfAd(@ModelAttribute("newPriceDTO")PriceDTO priceDTO, HttpSession httpSession,
                                        RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
        modelAndView.setViewName("changePrice");
        boolean isUpdated = true;
        if (!"".equals(priceDTO.getValue())) {
            long adId = (long) httpSession.getAttribute("editAdId");
            announcementService.updatePriceOfAd(adId, priceDTO);
        } else {
            isUpdated = false;
            redirectAttributes.addFlashAttribute("message", "Price can be positive number only!");
        }
        redirectAttributes.addFlashAttribute("isUpdated", isUpdated);
        modelAndView.setViewName("redirect:/user/ad/edit/price");
        return modelAndView;
    }

    @GetMapping(path = "/ad/edit/photo")
    public ModelAndView getPhotoEditPage(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("changePhotos");
        long adId = (long) httpSession.getAttribute("editAdId");
        log.info("ID of editing announcement: "+adId);
        List<Image> photos = (List<Image>) announcementService.getAdById(adId).getPhotos();
        log.info("List of AD images: "+photos);
        modelAndView.addObject("photos", photos);
        return modelAndView;
    }

    @PostMapping(path = "/ad/edit/photo/add")
    public ModelAndView addNewPhotoToAd(@RequestParam("file") MultipartFile multipartFile, RedirectAttributes redirectAttributes, HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("changePhotos");
        long adId = (long) httpSession.getAttribute("editAdId");
        log.info("ID of editing announcement: "+adId);
        Announcement adById = (Announcement) announcementService.getAdById(adId);
        Image savedImage =  imageService.createImageFromFileAndSetAd(adById, multipartFile);
        log.info("New image: "+savedImage);
        redirectAttributes.addFlashAttribute("isUpdated", true);
        modelAndView.setViewName("redirect:/user/ad/edit/photo");
        return modelAndView;
    }

    @PostMapping(path = "/ad/edit/photo/delete")
    public ModelAndView deletePhotoFromAd(@RequestParam("photoId")long photoId, RedirectAttributes redirectAttributes, ModelAndView modelAndView) {
        modelAndView.setViewName("changePhotos");
        log.info("Image ID for delete: "+photoId);
        imageService.deleteImageById(photoId);
        redirectAttributes.addFlashAttribute("isUpdated", true);
        modelAndView.setViewName("redirect:/user/ad/edit/photo");
        return modelAndView;
    }

    @PostMapping(path = "/ad/edit/photo/finish")
    public ModelAndView finishPhotoEditing(HttpSession httpSession, ModelAndView modelAndView) {
        modelAndView.setViewName("changePhotos");
        long editAdId = (long) httpSession.getAttribute("editAdId");
        announcementService.photoCheckAndFinishOrSetDefaultPhoto(editAdId);
        modelAndView.setViewName("redirect:/user/ad/edit/"+editAdId);
        return modelAndView;
    }
}