package car_rental.api.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Car>> getAllCars(@RequestParam(required = false) String carStatus){
        List<Car> cars;
        if("available".equals(carStatus)) {
            cars = carService.getAvailableCar();
            if (cars == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }

        if ("unavailable".equals(carStatus)){
            cars = carService.getUnavailableCars();
            if (cars == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }

        if (carStatus == null){
            cars = carService.getAllCars();
            if (cars == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@RequestParam long id){
        Car car = carService.getCarById(id);
        if (car == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createOrUpdateCar(@RequestBody Car car){
        return new ResponseEntity<>(carService.createOrUpdateCar(car), HttpStatus.CREATED);
    }

    @DeleteMapping("/cars/{id}/delete")
    public ResponseEntity<Car> deleteCarById(@RequestParam long id){
        if (carService.deleteCarById(id) > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
