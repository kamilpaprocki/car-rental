package car_rental.api.car;

import car_rental.api.exceptions.CarNotFoundException;
import car_rental.api.exceptions.WrongArgumentException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

public List<CarDTO> getAllCars(){
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(new CarMapper() :: mapToDTO).collect(Collectors.toList());
}

public CarDTO getCarById(Long carId){
        if (carId == null){
            throw new WrongArgumentException("Car id cannot be a null");
        }
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null){
            throw new CarNotFoundException("There is no car with id: " + carId);
        }
        return new CarMapper().mapToDTO(car);
}

public Car createOrUpdateCar(CarDTO carDTO){
        return carRepository.save(new CarMapper().mapToDAO(carDTO));
}

@Transactional
public int deleteCarById(Long carId){
        if (carId == null){
            throw new WrongArgumentException("Car id cannot be a null");
        }
        return carRepository.deleteCarById(carId);
}

public List<CarDTO> getAvailableCar() {
    List<Car> cars = carRepository.getAvailableCars().orElse(null);
    if (cars == null) {
        throw new CarNotFoundException("There is no car available");
    }
    return cars.stream().map(new CarMapper()::mapToDTO).collect(Collectors.toList());
}


public List<CarDTO> getUnavailableCars(){
    List<Car> cars = carRepository.getUnavailableCars().orElse(null);
    if (cars == null) {
        throw new CarNotFoundException("There is no car unavailable");
    }
    return cars.stream().map(new CarMapper()::mapToDTO).collect(Collectors.toList());
}

}

