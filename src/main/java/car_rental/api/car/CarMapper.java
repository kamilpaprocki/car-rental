package car_rental.api.car;

import car_rental.api.utils.DTOMapper;

import java.math.BigDecimal;

public class CarMapper implements DTOMapper<Car, CarDTO> {
    @Override
    public CarDTO map(Car from) {
        return CarDTO.builder()
                .id(from.getId().toString())
                .brand(from.getBrand())
                .model(from.getModel())
                .segment(from.getSegment().toString())
                .modelYear(String.valueOf(from.getModelYear()))
                .currentOdometer(String.valueOf(from.getCurrentOdometer()))
                .pricePerDay(from.getPricePerDay().toString())
                .available(from.getAvailable().toString())
                .build();
    }

    @Override
    public Car reverse(CarDTO from) {
        Car car = new Car();
        if (from.getId()!=null){
            car.setId(Long.parseLong(from.getId()));
        }
        car.setBrand(from.getBrand());
        car.setModel(from.getModel());
        car.setSegment(CarSegment.valueOf(from.getSegment()));
        car.setModelYear(Integer.parseInt(from.getModelYear()));
        car.setCurrentOdometer(Long.parseLong(from.getCurrentOdometer()));
        car.setPricePerDay(new BigDecimal(from.getPricePerDay()));
        car.setAvailable(Boolean.valueOf(from.isAvailable()));
        return car;
    }
}
