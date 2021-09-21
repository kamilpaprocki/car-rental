package car_rental.api.car;

import car_rental.api.utils.DTOMapper;

import java.math.BigDecimal;

public class CarMapper implements DTOMapper<Car, CarDTO> {
    @Override
    public CarDTO mapToDTO(Car from) {
        return CarDTO.builder()
                .id(from.getId().toString())
                .brand(from.getBrand())
                .model(from.getModel())
                .segment(from.getSegment().toString())
                .modelYear(String.valueOf(from.getModelYear()))
                .currentOdometer(String.valueOf(from.getCurrentOdometer()))
                .pricePerDay(from.getPricePerDay().toString())
                .available(from.getAvailable())
                .build();
    }

    @Override
    public Car mapToDAO(CarDTO from) {
       return Car.CarBuilder.builder()
               .id(from.getId())
               .brand(from.getBrand())
               .model(from.getModel())
               .segment(CarSegment.valueOf(from.getSegment()))
               .modelYear(Integer.parseInt(from.getModelYear()))
               .currentOdometer(Long.parseLong(from.getCurrentOdometer()))
               .pricePerDay(new BigDecimal(from.getPricePerDay()))
               .isAvailable(from.isAvailable())
               .build();
    }
}
