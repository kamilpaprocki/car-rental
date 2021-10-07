package car_rental.api.car;

import car_rental.api.exceptions.WrongArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CarFrontController {

private final CarService carService;

private static final Logger logger = LoggerFactory.getLogger(CarFrontController.class);

    public CarFrontController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car")
    @PreAuthorize("hasAnyRole('USER','WORKER', 'ADMIN')")
    public String carPage(Model model,
                          @RequestParam(required = false) String cars,
                          @RequestParam(required = false) Long carId
                          ){

        List<CarDTO> carDTOS;

        if ("carById".equals(cars)){
            try{
                CarDTO carDTO = carService.getCarById(carId);
                if (carDTO == null){
                    logger.error("Car with id {} not found.", carId);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no car with id: " + carId);
                }
                logger.info("Return car with id {}.", carId);
                model.addAttribute("carById", carDTO);
            }catch (WrongArgumentException e){
                logger.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }
        if ("allCars".equals(cars)){
            carDTOS = carService.getAllCars();
            if (carDTOS.isEmpty()){
                logger.error("List of cars is empty.");
            }
            logger.info("Return {} cars.", carDTOS.size());
            model.addAttribute("allCars", carDTOS);
        }
        if ("availableCars".equals(cars)){
            carDTOS = carService.getAvailableCar();
            if (carDTOS.isEmpty()){
                logger.error("List of available cars is empty.");
            }
            logger.info("Return {} available cars.", carDTOS.size());
            model.addAttribute("availableCars", carDTOS);
        }
        if ("unavailableCars".equals(cars)){
            carDTOS = carService.getUnavailableCars();
            if (carDTOS.isEmpty()){
                logger.error("List of unavailable cars is empty.");
            }
            logger.info("Return {} unavailable cars.", carDTOS.size());
            model.addAttribute("unavailableCars", carDTOS);
        }
        return "car/cars";
    }

    @GetMapping("/add/car")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String getAddCarPage(Model model){
       model.addAttribute("car", new CarDTO());
        return "car/add-form-car";
    }

    @PostMapping("/add/car")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String addCar(@ModelAttribute("car") @Valid CarDTO carDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            for (ObjectError error :bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return "car/add-form-car";
        }
        logger.info("Create new car.");
        carService.createOrUpdateCar(carDTO);
        return "redirect:/home?info=added";
    }

    @GetMapping("/update/edit-car")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String getUpdateCar(){
        return "car/update-car";
    }

    @GetMapping("/update/car")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String getUpdateCarPage(@RequestParam(required = false) Long carId, Model model){
        CarDTO carDTO;
        try{
            carDTO = carService.getCarById(carId);
        } catch (WrongArgumentException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        if (carDTO == null){
            logger.error("Car with id {} not found.", carId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no car with id: " + carId);
        }
        model.addAttribute("updateCar", carDTO);
        return "car/update-form-car";
    }

    @PostMapping("/update/car")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String updateCar(@ModelAttribute("updateCar") @Valid CarDTO carDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            for (ObjectError error :bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return "car/update-form-car";
        }
        logger.info("Update car with id {}.", carDTO.getId());
        carService.createOrUpdateCar(carDTO);
        return "redirect:/home?info=updated";
    }

    @GetMapping("/cars")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String getCarsPage(){
        return "car/get-car";
    }

    @GetMapping("/delete/delete-car")
    @PreAuthorize("hasRole('ADMIN')")
    public String getDeleteCar(){
        return "car/delete-car";
    }

    @GetMapping("/delete/car")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCarPage(@RequestParam(required = false) Long id){
        try{
            if (carService.deleteCarById(id) > 0){
                logger.info("Delete car with id {}.", id);
                return "redirect:/home?info=deleted";
            }

        }catch (WrongArgumentException e){
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        logger.info("Car with id {} not exists.", id);
       return "redirect:/home";
    }


}
