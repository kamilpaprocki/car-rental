package car_rental.api.rents;

import car_rental.api.PromotionCode.PromotionCodeMapper;
import car_rental.api.car.CarMapper;
import car_rental.api.client.ClientMapper;
import car_rental.utils.DTOMapper;

public class RentDTOMapper implements DTOMapper<Rent, RentDTO> {

    @Override
    public RentDTO from(Rent from) {
        return RentDTO.builder().
                client(new ClientMapper().from(from.getClient()).toString())
                .car(new CarMapper().from(from.getCar()).toString())
                .rentDate(from.getRentDate().toString())
                .rentAddress(new AddressMapper().from(from.getRentAddress()).toString())
                .plannedReturnDate(from.getPlannedReturnDate().toString())
                .returnDate(from.getReturnDate().toString())
                .rentalDays(String.valueOf(from.getRentalDays()))
                .returnAddress(new AddressMapper().from(from.getReturnAddress()).toString())
                .rentalCost(from.getRentalCost().toString())
                .odomoterDistance(String.valueOf(from.getOdomoterDistance()))
                .promotionCode(new PromotionCodeMapper().from(from.getPromotionCode()).toString())
                .paymentMethod(from.getPaymentMethod().toString())
                .isFinished(String.valueOf(from.isFinished()))
                .build();
    }
}
