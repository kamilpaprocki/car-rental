package car_rental.api.car;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarFrontController {

private final CarService carService;

    public CarFrontController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car")
    public String carPage(Model model,
                          @RequestParam(required = false) String id,
                          @RequestParam(required = false) String cars){

        if ("carId".equals(cars)){
            model.addAttribute("carById", carService.getCarById(Long.parseLong(id)));
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
       model.addAttribute("car", new Car());
        return "add-form-car";
    }

    @PostMapping("/add/car")
    public String addCar(@ModelAttribute Car car){
        carService.createOrUpdateCar(car);
        return "redirect:/home?info=added";
    }

    @GetMapping("/update/edit-car")
    public String getUpdateCar(){
        return "update-car";
    }

    @GetMapping("/update/car")
    public String getUpdateCarPage(@RequestParam Long id, Model model){
        model.addAttribute("carById", carService.getCarById(id));
        return "update-form-car";
    }

    @PostMapping("/update/car")
    public String updateCar(@ModelAttribute("carById") Car carById){
        carService.createOrUpdateCar(carById);
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
