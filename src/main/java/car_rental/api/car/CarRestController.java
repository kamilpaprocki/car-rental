package car_rental.api.car;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.CarNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CarRestController {

    private final CarService carService;

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
                throw new CarNotFoundException("There are no cars available");
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }

        if ("unavailable".equals(carStatus)){
            cars = carService.getUnavailableCars();
            if (cars.isEmpty()){
                throw new CarNotFoundException("There are no cars unavailable");
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }

        if (carStatus == null){
            cars = carService.getAllCars();
            if (cars.isEmpty()){
                throw new CarNotFoundException("There are no cars");
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        throw new BadRequestException("Wrong input parameter. Input car status: " + carStatus);
    }

    @GetMapping("/cars/car")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<CarDTO> getCarById(@RequestParam Long id){
        CarDTO car = carService.getCarById(id);
        if (car == null){
            throw new CarNotFoundException("There is no car with id: " + id);
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping("/cars/update")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<Car> createOrUpdateCar(@RequestBody CarDTO carDTO){
        if (carDTO.getId() != null){
            return new ResponseEntity<>(carService.createOrUpdateCar(carDTO), HttpStatus.OK);
        }
        return new ResponseEntity<>(carService.createOrUpdateCar(carDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/cars/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> deleteCarById(@RequestParam Long id){
        if (carService.deleteCarById(id) > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
