package car_rental.api.rents;

import car_rental.api.car.Car;
import car_rental.api.car.CarDTO;
import car_rental.api.car.CarMapper;
import car_rental.api.car.CarService;
import car_rental.api.promotionCode.PromotionCodeDTO;
import car_rental.api.user.UserApp;
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
@SessionAttributes("rentDTO")
public class RentFrontController {

    private final RentService rentService;
    private final CarService carService;

    public RentFrontController(RentService rentService, CarService carService) {
        this.rentService = rentService;
        this.carService = carService;
    }

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

        rentDTO = rentService.addRentDetails(rentDTO);
        model.addAttribute("rentDTO", rentDTO);
        model.addAttribute("promotionCodeDTO", new PromotionCodeDTO());
        
        return "rent-summary";
    }

    @PostMapping("/add/rent/summary")
    public String summary(@ModelAttribute("rentDTO") RentDTO rentDTO){

        System.out.println(rentDTO.toString());
        return "redirect:/home";
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
}
