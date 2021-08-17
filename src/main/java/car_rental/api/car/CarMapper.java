package car_rental.api.car;

import car_rental.api.utils.DTOMapper;

import java.math.BigDecimal;

public class CarMapper implements DTOMapper<Car, CarDTO> {
    @Override
    public CarDTO map(Car from) {
        return CarDTO.builder()
                .brand(from.getBrand())
                .model(from.getModel())
                .segment(from.getSegment().toString())
                .modelYear(String.valueOf(from.getModelYear()))
                .currentOdemeter(String.valueOf(from.getCurrentOdometer()))
                .pricePerDay(from.getPricePerDay().toString())
                .build();
    }

    @Override
    public Car reverse(CarDTO from) {
        Car car = new Car();
        car.setBrand(from.getBrand());
        car.setModel(from.getModel());
        car.setSegment(CarSegment.valueOf(from.getSegment()));
        car.setModelYear(Integer.parseInt(from.getModelYear()));
        car.setCurrentOdometer(Long.parseLong(from.getCurrentOdemeter()));
        car.setPricePerDay(new BigDecimal(from.getPricePerDay()));
        car.setAvailable(true);
        return car;
    }
}
