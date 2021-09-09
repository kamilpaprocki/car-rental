package car_rental.api.rents;

import car_rental.api.car.CarMapper;
import car_rental.api.promotionCode.PromotionCodeMapper;
import car_rental.api.user.UserAppMapper;
import car_rental.api.utils.DTOMapper;
import car_rental.api.utils.DateParser;

import java.math.BigDecimal;

public class RentDTOMapper implements DTOMapper<Rent, RentDTO> {

    @Override
    public RentDTO map(Rent from) {

        if (from.getRentDate() != null) {
            return RentDTO.builder()
                    .id(from.getId().toString())
                    .userApp(new UserAppMapper().map(from.getUserApp()))
                    .car(new CarMapper().map(from.getCar()))
                    .rentDate(from.getRentDate().toString())
                    .rentAddress(new AddressMapper().map(from.getRentAddress()))
                    .plannedReturnDate(from.getPlannedReturnDate().toString())
                    .rentalDays(String.valueOf(from.getRentalDays()))
                    .returnAddress(new AddressMapper().map(from.getReturnAddress()))
                    .returnDate(from.getReturnDate().toString())
                    .rentalCost(from.getRentalCost().toString())
                    .odometerDistance(String.valueOf(from.getOdometerDistance()))
                    .promotionCode(new PromotionCodeMapper().map(from.getPromotionCode()))
                    .paymentMethod(from.getPaymentMethod().toString())
                    .isFinished(from.isFinished().toString())
                    .build();
        }
        return RentDTO.builder()
                .id(from.getId().toString())
                .userApp(new UserAppMapper().map(from.getUserApp()))
                .car(new CarMapper().map(from.getCar()))
                .rentAddress(new AddressMapper().map(from.getRentAddress()))
                .plannedReturnDate(from.getPlannedReturnDate().toString())
                .rentalDays(String.valueOf(from.getRentalDays()))
                .returnAddress(new AddressMapper().map(from.getReturnAddress()))
                .returnDate(from.getReturnDate().toString())
                .rentalCost(from.getRentalCost().toString())
                .odometerDistance(String.valueOf(from.getOdometerDistance()))
                .promotionCode(new PromotionCodeMapper().map(from.getPromotionCode()))
                .paymentMethod(from.getPaymentMethod().toString())
                .isFinished(from.isFinished().toString())
                .build();
    }

    @Override
    public Rent reverse(RentDTO from) {
        Rent rent = new Rent();
        DateParser dateParser = new DateParser();

        rent.setId(Long.parseLong(from.getId()));
        rent.setUserApp(new UserAppMapper().reverse(from.getUserApp()));
        rent.setCar(new CarMapper().reverse(from.getCar()));
        rent.setRentDate(dateParser.parseDate(from.getRentDate()));
        rent.setRentAddress(new AddressMapper().reverse(from.getRentAddress()));
        rent.setPlannedReturnDate(dateParser.parseDate(from.getPlannedReturnDate()));
        rent.setRentalDays(Long.parseLong(from.getRentalDays()));
        rent.setReturnAddress(new AddressMapper().reverseToReturnAddress(from.getReturnAddress()));
        if (from.getReturnDate() != null){
            rent.setReturnDate(dateParser.parseDate(from.getReturnDate()));
        }
        rent.setRentalCost(new BigDecimal(from.getRentalCost()));
        rent.setOdometerDistance(Long.parseLong(from.getOdometerDistance()));
        rent.setPromotionCode(new PromotionCodeMapper().reverse(from.getPromotionCode()));
        rent.setPaymentMethod(PaymentMethod.valueOf(from.getPaymentMethod()));
        rent.setFinished(Boolean.getBoolean(from.getIsFinished()));

        return rent;
    }
}
