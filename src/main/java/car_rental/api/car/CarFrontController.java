package car_rental.api.car;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CarFrontController {

private final CarService carService;

    public CarFrontController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car")
    public String carPage(Model model){
        model.addAttribute("car", new Car());
        return "form-car";
    }

    @PostMapping("/add/car")
    public String addCar(@ModelAttribute Car car, Model model){
        model.addAttribute("car", car);
        System.out.println(car.toString());
        carService.createOrUpdateCar(car);
        return "redirect:/car";
    }
}
