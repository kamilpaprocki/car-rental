package car_rental.api.car;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

public List<Car> getAllCars(){
        return carRepository.findAll();
}

public Car getCarById(long id){
        return carRepository.findById(id).orElse(null);
}

public Car createOrUpdateCar(Car car){
        return carRepository.save(car);
}

@Transactional
public int deleteCarById(long id){
        return carRepository.deleteCarById(id);
}

public List<Car> getAvailableCar(){
        return carRepository.getAvailableCars().orElse(null);
}

public List<Car> getUnavailableCars(){
        return carRepository.getUnavailableCars().orElse(null);
}

}

