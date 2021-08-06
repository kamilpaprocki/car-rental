package car_rental.api.car;

import car_rental.api.utils.DTOMapper;

public class CarMapper implements DTOMapper<Car, CarDTO> {
    @Override
    public CarDTO from(Car from) {
        return CarDTO.builder()
                .brand(from.getBrand())
                .model(from.getModel())
                .segment(from.getSegment().toString())
                .modelYear(String.valueOf(from.getModelYear()))
                .currentOdemeter(String.valueOf(from.getCurrentOdometer()))
                .pricePerDay(from.getPricePerDay().toString())
                .build();
    }
}
