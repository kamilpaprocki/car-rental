package car_rental.api.car;

import car_rental.api.utils.DTOMapper;

import java.math.BigDecimal;

public class CarMapper implements DTOMapper<Car, CarDTO> {
    @Override
    public CarDTO mapToDTO(Car from) {
        if (from == null){
            return null;
        }
        return CarDTO.builder()
                .id(String.valueOf(from.getId()))
                .brand(from.getBrand())
                .model(from.getModel())
                .segment(from.getSegment().toString())
                .modelYear(String.valueOf(from.getModelYear()))
                .currentOdometer(String.valueOf(from.getCurrentOdometer()))
                .pricePerDay(String.valueOf(from.getPricePerDay()))
                .available(from.getAvailable())
                .build();
    }

    @Override
    public Car mapToDAO(CarDTO from) {
       return Car.builder()
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
