package car_rental.api.rents;

import car_rental.api.car.CarDTO;
import car_rental.api.car.CarService;
import car_rental.api.exceptions.RentNotFoundException;
import car_rental.api.promotionCode.PromotionCodeDTO;
import car_rental.api.user.UserApp;
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

    public RentFrontController(RentService rentService, CarService carService) {
        this.rentService = rentService;
        this.carService = carService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/rent")
      public String rentForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (((UserApp)(authentication.getPrincipal())).isHasActiveRent()) {
            return "redirect:/home?info=multiplerents";
        }

        List<CarDTO> carDTOs = carService.getAvailableCar();
       model.addAttribute("cars", carDTOs);
       return "rent-form";
    }

    @GetMapping("/add/rent")
    public String addRent(@ModelAttribute("rentDTO") RentDTO rentDTO, Model model, @RequestParam String carid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        CarDTO car = carService.getCarById(Long.parseLong(carid));
        rentDTO = rentService.addUserAndCar(rentDTO, userApp, car);
        model.addAttribute("rentDTO", rentDTO);
        return "rent-details";
    }

    @PostMapping("/add/rent/details")
    public String addRentDetails(@ModelAttribute("rentDTO") @Valid RentDTO rentDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "rent-details";
        }

        rentDTO = rentService.addOrUpdateRentDetails(rentDTO);
        model.addAttribute("rentDTO", rentDTO);
        model.addAttribute("promotionCodeDTO", new PromotionCodeDTO());
        return "rent-summary";
    }

    @PostMapping("/add/rent/summary")
    public String summary(@ModelAttribute("rentDTO") RentDTO rentDTO){
        rentService.addRent(rentDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp currentUser = (UserApp) authentication.getPrincipal();
        currentUser.setHasActiveRent(true);
        return "redirect:/home?info=rented";
    }

    @PostMapping(value = "/usepromotioncode")
    public String use(@ModelAttribute("promotionCodeDTO") @Valid PromotionCodeDTO promotionCodeDTO, BindingResult bindingResult,
                      @ModelAttribute("rentDTO") RentDTO rentDTO, Model model){
        if (bindingResult.hasErrors()){
            return "rent-summary";
        }
        if ((rentDTO.getPromotionCode() != null)){
            bindingResult.addError(new ObjectError("multipleUse", "Promotion codes cannot be used multiple times"));
            return "rent-summary";
        }
        rentDTO = rentService.addPromotionCode(rentDTO, promotionCodeDTO);
        model.addAttribute("rentDTO", rentDTO);
        return "rent-summary";
    }

    @ModelAttribute("rentDTO")
    public RentDTO newRentDTO(){
        return new RentDTO();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/extend/rent")
    public String getExtendRent(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        List<RentDTO> rentDTO = rentService.getRentByUserApp(userApp);
        if (rentDTO.isEmpty()){
            throw new RentNotFoundException("User " + userApp.getUsername() + "hasnot active rents");
        }
        model.addAttribute("rents", rentDTO);
        return "get-rent";
    }

    @GetMapping("/extend/rent/update")
    public String extendRent(Model model, @RequestParam String rentid){
        RentDTO rentDTO = rentService.getRentById(rentid);
        model.addAttribute("activeRentDTO", rentDTO);
        return "extend-rent";
    }

    @PostMapping("/extend/rent")
    public String updateRent(@ModelAttribute("activeRentDTO") @Valid RentDTO rentDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "extend-rent";
        }
        model.addAttribute("activeRentDTO", rentService.updatePlannedReturnDate(rentDTO));
        return "extend-rent-summary";
    }

    @PostMapping("/update/extend/rent")
    public String saveUpdatedRent(@ModelAttribute("activeRentDTO") RentDTO rentDTO){
        rentService.createOrUpdateRent(rentDTO);
        return "redirect:/home?info=extended";
    }

    @GetMapping("/finish/rent")
    public String getActiveRents(Model model){
        model.addAttribute("rents", rentService.getActiveRents());
        return "get-rents-to-finish";
    }

    @GetMapping("/finish/rent/update")
    public String getFinishRentForm(@RequestParam String rentid, Model model){
        RentDTO finishRentDTO = rentService.getRentById(rentid);
        model.addAttribute("finishRentDTO", finishRentDTO);
        model.addAttribute("odometerWrapper", rentService.getCarLastOdometer(finishRentDTO));
    return "finish-rent";
    }

    @PostMapping("/finish/rent/update")
    public String finishRent(@ModelAttribute("odometerWrapper") @Valid  CarReturnOdometerWrapper carReturnOdometerWrapper, BindingResult bindingResult
            , @ModelAttribute("finishRentDTO") RentDTO rentDTO){
        if (bindingResult.hasErrors()){
            return "finish-rent";
        }
      rentService.finishRent(rentDTO, carReturnOdometerWrapper);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp currentUser = (UserApp) authentication.getPrincipal();
        currentUser.setHasActiveRent(false);

        return "redirect:/home?info=finished";

    }
}
