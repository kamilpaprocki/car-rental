package car_rental.api.car;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.CarNotFoundException;
import car_rental.api.exceptions.WrongArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CarRestController {

    private final CarService carService;

    private final static Logger logger = LoggerFactory.getLogger(CarRestController.class);

    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<List<CarDTO>> getAllCars(@RequestParam(required = false) String carStatus){
        List<CarDTO> cars;
        if("available".equals(carStatus)) {
            cars = carService.getAvailableCar();
            if (cars.isEmpty()){
                logger.error("List of available cars is empty.");
                throw new CarNotFoundException("There are no cars available");
            }
            logger.info("Return {} available cars.", cars.size());
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }

        if ("unavailable".equals(carStatus)){
            cars = carService.getUnavailableCars();
            if (cars.isEmpty()){
                logger.error("List of unavailable cars is empty.");
                throw new CarNotFoundException("There are no cars unavailable");
            }
            logger.info("Return {} unavailable cars.", cars.size());
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }

        if (carStatus == null){
            cars = carService.getAllCars();
            if (cars.isEmpty()){
                logger.error("List of cars is empty.");
                throw new CarNotFoundException("There are no cars");
            }
            logger.info("Return {} cars.", cars.size());
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        logger.error("Wrong car status: {}.", carStatus);
        throw new BadRequestException("Wrong input parameter. Input car status: " + carStatus);
    }

    @GetMapping("/cars/car")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<CarDTO> getCarById(@RequestParam Long id){
        CarDTO car = carService.getCarById(id);
        if (car == null){
            logger.error("Car with id {} not found.", id);
            throw new CarNotFoundException("There is no car with id: " + id);
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping("/cars/update")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<CarDTO> createOrUpdateCar(@RequestBody CarDTO carDTO){
        if (carDTO.getId() != null){
            logger.info("Update car with id {}.", carDTO.getId());
            return new ResponseEntity<>(carService.createOrUpdateCar(carDTO), HttpStatus.OK);
        }
        logger.info("Create new car");
        return new ResponseEntity<>(carService.createOrUpdateCar(carDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/cars/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> deleteCarById(@RequestParam Long id){
        try{
            if (carService.deleteCarById(id) > 0){
                logger.info("Delete car with id {}.", id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (WrongArgumentException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Car with id {} not exists.", id);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
