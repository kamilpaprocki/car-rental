package car_rental.api.rents;

import car_rental.api.car.Car;
import car_rental.api.car.CarDTO;
import car_rental.api.car.CarMapper;
import car_rental.api.car.CarService;
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
import java.util.stream.Collectors;

@Controller
@SessionAttributes({"rentDTO", "activeRentDTO"})
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
        List<Car> cars = carService.getAvailableCar();
        List<CarDTO> carDTOs = cars.stream().map(new CarMapper() :: map).collect(Collectors.toList());
       model.addAttribute("cars", carDTOs);
       return "rent-form";
    }

    @GetMapping("/add/rent")
    public String addRent(@ModelAttribute("rentDTO") RentDTO rentDTO, Model model, @RequestParam String carId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp)(authentication.getPrincipal());
        Car car = carService.getCarById(Long.parseLong(carId));
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
        return "redirect:/home?info=rented";
    }

    @PostMapping(value = "/usepromotioncode")
    public String use(@ModelAttribute("promotionCodeDTO") @Valid PromotionCodeDTO promotionCodeDTO, BindingResult bindingResult,
                      @ModelAttribute("rentDTO") RentDTO rentDTO, Model model){
        if (bindingResult.hasErrors()){
            return "rent-summary";
        }
        if (!(rentDTO.getPromotionCode() == null)){
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
        model.addAttribute("rents", rentDTO);
        return "get-rent";
    }

    @GetMapping("/extend/rent/update")
    public String extendRent(Model model, @RequestParam String rentId){
        Rent rent = rentService.getRentById(Long.parseLong(rentId));
        RentDTO rentDTO = new RentMapper().map(rent);
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
        rentService.createOrUpdateRent(new RentMapper().reverse(rentDTO));
        return "redirect:/home?info=extended";
    }
}
