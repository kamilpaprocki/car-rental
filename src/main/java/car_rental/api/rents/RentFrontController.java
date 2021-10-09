package car_rental.api.rents;

import car_rental.api.car.CarDTO;
import car_rental.api.car.CarService;
import car_rental.api.promotionCode.PromotionCodeDTO;
import car_rental.api.user.UserApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes({"rentDTO", "activeRentDTO", "finishRentDTO", "odometerWrapper"})
public class RentFrontController {

    private final RentService rentService;
    private final CarService carService;

    private final static Logger logger = LoggerFactory.getLogger(RentFrontController.class);

    public RentFrontController(RentService rentService, CarService carService) {
        this.rentService = rentService;
        this.carService = carService;
    }

    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    @GetMapping("/rent")
      public String rentForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = ((UserApp)(authentication.getPrincipal()));
        if (userApp.isHasActiveRent()) {
            logger.error("User {} has active rents.", userApp.getUsername());
            return "redirect:/home?info=multiplerents";
        }

        List<CarDTO> carDTOs = carService.getAvailableCar();
        if (carDTOs.isEmpty()){
            logger.error("List of available cars to rent is empty.");
        }
        logger.info("Return {} available cars to rent.", carDTOs.size());
       model.addAttribute("cars", carDTOs);
       return "rents/rent-form";
    }

    @GetMapping("/add/rent")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String addRent(@ModelAttribute("rentDTO") RentDTO rentDTO, Model model, @RequestParam String carid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        CarDTO car = carService.getCarById(Long.parseLong(carid));
        rentDTO = rentService.addUserAndCar(rentDTO, userApp, car);
        logger.info("Add to new rent user and car.");
        model.addAttribute("rentDTO", rentDTO);
        return "rents/rent-details";
    }

    @PostMapping("/add/rent/details")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String addRentDetails(@ModelAttribute("rentDTO") @Valid RentDTO rentDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            for (ObjectError error: bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return "rents/rent-details";
        }

        rentDTO = rentService.addOrUpdateRentDetails(rentDTO);
        logger.info("Add details to new rent.");
        model.addAttribute("rentDTO", rentDTO);
        model.addAttribute("promotionCodeDTO", new PromotionCodeDTO());
        return "rents/rent-summary";
    }

    @PostMapping("/add/rent/summary")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String summary(@ModelAttribute("rentDTO") RentDTO rentDTO){
        rentService.addRent(rentDTO);
        logger.info("Save new rent.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp currentUser = (UserApp) authentication.getPrincipal();
        currentUser.setHasActiveRent(true);
        return "redirect:/home?info=rented";
    }

    @PostMapping(value = "/usepromotioncode")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String use(@ModelAttribute("promotionCodeDTO") @Valid PromotionCodeDTO promotionCodeDTO, BindingResult bindingResult,
                      @ModelAttribute("rentDTO") RentDTO rentDTO, Model model){
        if (bindingResult.hasErrors()){
            for (ObjectError error: bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return "rents/rent-summary";
        }
        if ((rentDTO.getPromotionCode() != null)){
            bindingResult.addError(new ObjectError("multipleUse", "Promotion codes cannot be used multiple times"));
            logger.error("Multiple user promotion code.");
            return "rents/rent-summary";
        }
        rentDTO = rentService.addPromotionCode(rentDTO, promotionCodeDTO);
        logger.info("Activated promotion code {} to new rent.", promotionCodeDTO.getPromotionCodeDTO());
        model.addAttribute("rentDTO", rentDTO);
        return "rents/rent-summary";
    }

    @ModelAttribute("rentDTO")
    public RentDTO newRentDTO(){
        return new RentDTO();
    }

    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    @GetMapping(value = "/extend/rent")
    public String getExtendRent(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        List<RentDTO> rentDTO = rentService.getRentByUserApp(userApp);
        if (rentDTO.isEmpty()){
            logger.error("User {} has not active rents.", userApp.getUsername());
        }
        model.addAttribute("rents", rentDTO);
        return "rents/get-rent";
    }

    @GetMapping("/extend/rent/update")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String extendRent(Model model, @RequestParam String rentid){
        RentDTO rentDTO = rentService.getRentById(rentid);
        logger.info("Return rent with id {}, to extend.", rentid);
        model.addAttribute("activeRentDTO", rentDTO);
        return "rents/extend-rent";
    }

    @PostMapping("/extend/rent")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String updateRent(@ModelAttribute("activeRentDTO") @Valid RentDTO rentDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return "rents/extend-rent";
        }
        model.addAttribute("activeRentDTO", rentService.updatePlannedReturnDate(rentDTO));
        logger.info("Update planned return date in rent with id {}.", rentDTO.getId());
        return "rents/extend-rent-summary";
    }

    @PostMapping("/update/extend/rent")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String saveUpdatedRent(@ModelAttribute("activeRentDTO") RentDTO rentDTO){
        rentService.createOrUpdateRent(rentDTO);
        logger.info("Update rent with id {}.", rentDTO.getId());
        return "redirect:/home?info=extended";
    }

    @GetMapping("/finish/rent")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String getActiveRents(Model model){
        List<RentDTO> rentDTOS = rentService.getActiveRents();
        if (rentDTOS.isEmpty()){
            logger.error("List of rents to finish is empty.");
        }
        logger.info("Return {} active rents to finish.", rentDTOS.size());
        model.addAttribute("rents", rentDTOS);

        return "rents/get-rents-to-finish";
    }

    @GetMapping("/finish/rent/update")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String getFinishRentForm(@RequestParam String rentid, Model model){
        RentDTO finishRentDTO = rentService.getRentById(rentid);
        model.addAttribute("finishRentDTO", finishRentDTO);
        model.addAttribute("odometerWrapper", rentService.getCarLastOdometer(finishRentDTO));
        logger.info("Return rent with id {} to finish.", rentid);
    return "rents/finish-rent";
    }

    @PostMapping("/finish/rent/update")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String finishRent(@ModelAttribute("odometerWrapper") @Valid  CarReturnOdometerWrapper carReturnOdometerWrapper, BindingResult bindingResult,
                             @ModelAttribute("finishRentDTO") RentDTO rentDTO){
        if (bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()){
                logger.error(error.getDefaultMessage());
            }
            return "rents/finish-rent";
        }
      rentService.finishRent(rentDTO, carReturnOdometerWrapper);
        logger.info("Finish rent with id {}.", rentDTO.getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp currentUser = (UserApp) authentication.getPrincipal();
        currentUser.setHasActiveRent(false);

        return "redirect:/home?info=finished";

    }
}
