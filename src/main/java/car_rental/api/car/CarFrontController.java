package car_rental.api.car;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class CarFrontController {

private final CarService carService;

    public CarFrontController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car")
    public String carPage(Model model,
                          @RequestParam(required = false) Long carId,
                          @RequestParam(required = false) String cars){

        if ("carId".equals(cars)){
/*            if (carId == null){
                throw new WrongArgumentException("Car id cannot be empty");
            }*/
            model.addAttribute("carById", carService.getCarById(carId));
        }
        if ("allCars".equals(cars)){
            model.addAttribute("allCars", carService.getAllCars());
        }
        if ("availableCars".equals(cars)){
            model.addAttribute("availableCars", carService.getAvailableCar());
        }
        if ("unavailableCars".equals(cars)){
            model.addAttribute("unavailableCars", carService.getUnavailableCars());
        }
        return "cars";
    }

    @GetMapping("/add/car")
    public String getAddCarPage(Model model){
       model.addAttribute("car", new CarDTO());
        return "add-form-car";
    }

    @PostMapping("/add/car")
    public String addCar(@ModelAttribute("car") @Valid CarDTO carDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "add-form-car";
        }
        carService.createOrUpdateCar(carDTO);
        return "redirect:/home?info=added";
    }

    @GetMapping("/update/edit-car")
    public String getUpdateCar(){
        return "update-car";
    }

    @GetMapping("/update/car")
    public String getUpdateCarPage(@RequestParam(required = false) Long carId, Model model){
        model.addAttribute("updateCar", carService.getCarById(carId));
        return "update-form-car";
    }

    @PostMapping("/update/car")
    public String updateCar(@ModelAttribute("updateCar") @Valid CarDTO carDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "update-form-car";
        }
        carService.createOrUpdateCar(carDTO);
        return "redirect:/home?info=updated";
    }

    @GetMapping("/cars")
    public String getCarsPage(){
        return "get-car";
    }

    @GetMapping("/delete/delete-car")
    public String getDeleteCar(){
        return "delete-car";
    }

    @GetMapping("/delete/car")
    public String deleteCarPage(@RequestParam(required = false) Long id){
           carService.deleteCarById(id);
       return "redirect:/home?info=deleted";
    }


}
