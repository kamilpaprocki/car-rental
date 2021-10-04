package car_rental.api.car;

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
        return new CarMapper().mapToDTO(carRepository.findById(carId).orElse(null));
}

public CarDTO createOrUpdateCar(CarDTO carDTO){
    carRepository.save(new CarMapper().mapToDAO(carDTO));
    return carDTO;
}

public Car createOrUpdateCar(Car car){
        return carRepository.save(car);
}

@Transactional
public int deleteCarById(Long carId){
        if (carId == null){
            throw new WrongArgumentException("Car id cannot be a null");
        }
        return carRepository.deleteCarById(carId);
}

public List<CarDTO> getAvailableCar() {
    List<Car> cars = carRepository.getAvailableCars().orElseThrow(null);
    return cars.stream().map(new CarMapper()::mapToDTO).collect(Collectors.toList());
}

public List<CarDTO> getUnavailableCars(){
    List<Car> cars = carRepository.getUnavailableCars().orElseThrow(null);
    return cars.stream().map(new CarMapper()::mapToDTO).collect(Collectors.toList());
}

}

