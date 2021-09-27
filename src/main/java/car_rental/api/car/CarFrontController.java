package car_rental.api.car;

import car_rental.api.exceptions.CarNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CarFrontController {

private final CarService carService;

    public CarFrontController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car")
    public String carPage(Model model,
                          @RequestParam(required = false) String cars,
                          @RequestParam(required = false) Long carId
                          ){

        if ("carById".equals(cars)){
            CarDTO carDTO = carService.getCarById(carId);
            if (carDTO == null){
                throw new CarNotFoundException("There is no car with id: " + carId);
            }
            model.addAttribute("carById", carDTO);
        }
        if ("allCars".equals(cars)){
            List<CarDTO> availableCarDTOList = carService.getAllCars();
            if (availableCarDTOList.isEmpty()){
                throw new CarNotFoundException("There is no car");
            }
            model.addAttribute("allCars", availableCarDTOList);
        }
        if ("availableCars".equals(cars)){
            List<CarDTO> availableCarDTOList = carService.getAvailableCar();
            if (availableCarDTOList.isEmpty()){
                throw new CarNotFoundException("There is no available cars");
            }
            model.addAttribute("availableCars", availableCarDTOList);
        }
        if ("unavailableCars".equals(cars)){
            List<CarDTO> availableCarDTOList = carService.getUnavailableCars();
            if (availableCarDTOList.isEmpty()){
                throw new CarNotFoundException("There is no unavailable cars");
            }
            model.addAttribute("unavailableCars", availableCarDTOList);
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
        CarDTO carDTO = carService.getCarById(carId);
        if (carDTO == null){
            throw new CarNotFoundException("There is no car with id: " + carId);
        }
        model.addAttribute("updateCar", carDTO);
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
