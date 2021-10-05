package car_rental.api.car;

import car_rental.api.exceptions.WrongArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final static Logger logger = LoggerFactory.getLogger(CarService.class);

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

public List<CarDTO> getAllCars(){
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(new CarMapper() :: mapToDTO).collect(Collectors.toList());
}

public CarDTO getCarById(Long carId){
        if (carId == null){
            logger.error("Car id is null.");
            throw new WrongArgumentException("Car id cannot be a null");
        }
        return new CarMapper().mapToDTO(carRepository.findById(carId).orElse(null));
}

public CarDTO createOrUpdateCar(CarDTO carDTO){
        if (carDTO.getId() == null){
            logger.info("Create new car");
        }
        if (carDTO.getId() != null){
            logger.info("Update car with id {}", carDTO.getId());
        }
    carRepository.save(new CarMapper().mapToDAO(carDTO));
    return carDTO;
}

@Transactional
public int deleteCarById(Long carId){
        if (carId == null){
            logger.error("Car id is null.");
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

