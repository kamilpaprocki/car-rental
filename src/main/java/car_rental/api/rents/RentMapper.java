package car_rental.api.rents;

import car_rental.api.car.CarMapper;
import car_rental.api.currency.Currency;
import car_rental.api.promotionCode.PromotionCodeMapper;
import car_rental.api.user.UserAppMapper;
import car_rental.api.utils.DTOMapper;
import car_rental.api.utils.DateParser;

import java.math.BigDecimal;

public class RentMapper implements DTOMapper<Rent, RentDTO> {

    @Override
    public RentDTO map(Rent from) {

        if (from.getPromotionCode() != null) {
            return RentDTO.builder()
                    .id(from.getId().toString())
                    .userApp(new UserAppMapper().map(from.getUserApp()))
                    .car(new CarMapper().map(from.getCar()))
                    .rentAddress(new AddressMapper().map(from.getRentAddress()))
                    .plannedReturnDate(new DateParser().parseDateToString(from.getPlannedReturnDate()))
                    .rentalDays(String.valueOf(from.getRentalDays()))
                    .returnAddress(new AddressMapper().map(from.getReturnAddress()))
                    .rentDate(new DateParser().parseDateToString(from.getRentDate()))
                    .returnDate(new DateParser().parseDateToString(from.getReturnDate()))
                    .rentalCost(from.getRentalCost().toString())
                    .promotionCode(new PromotionCodeMapper().map(from.getPromotionCode()))
                    .odometerDistance(String.valueOf(from.getOdometerDistance()))
                    .paymentMethod(from.getPaymentMethod().toString())
                    .currency(from.getCurrency().toString())
                    .isFinished(from.isFinished().toString())
                    .build();
        }

        return RentDTO.builder()
                .id(from.getId().toString())
                .userApp(new UserAppMapper().map(from.getUserApp()))
                .car(new CarMapper().map(from.getCar()))
                .rentAddress(new AddressMapper().map(from.getRentAddress()))
                .plannedReturnDate(new DateParser().parseDateToString(from.getPlannedReturnDate()))
                .rentalDays(String.valueOf(from.getRentalDays()))
                .returnAddress(new AddressMapper().map(from.getReturnAddress()))
                .rentDate(new DateParser().parseDateToString(from.getRentDate()))
                .returnDate(new DateParser().parseDateToString(from.getReturnDate()))
                .rentalCost(from.getRentalCost().toString())
                .odometerDistance(String.valueOf(from.getOdometerDistance()))
                .paymentMethod(from.getPaymentMethod().toString())
                .currency(from.getCurrency().toString())
                .isFinished(from.isFinished().toString())
                .build();
    }

    @Override
    public Rent reverse(RentDTO from) {
        Rent rent = new Rent();
        DateParser dateParser = new DateParser();

        if(from.getId() != null) {
            rent.setId(Long.parseLong(from.getId()));
        }
        rent.setUserApp(new UserAppMapper().reverse(from.getUserApp()));
        rent.setCar(new CarMapper().reverse(from.getCar()));
        rent.setRentDate(dateParser.parseStringToDate(from.getRentDate()));
        rent.setRentAddress(new AddressMapper().reverse(from.getRentAddress()));
        rent.setPlannedReturnDate(dateParser.parseStringToDate(from.getPlannedReturnDate()));
        rent.setRentalDays(Long.parseLong(from.getRentalDays()));
        rent.setReturnAddress(new AddressMapper().reverseToReturnAddress(from.getReturnAddress()));
        if (from.getReturnDate() != null){
            rent.setReturnDate(dateParser.parseStringToDate(from.getReturnDate()));
        }
        rent.setRentalCost(new BigDecimal(from.getRentalCost()));
        if (from.getOdometerDistance() != null) {
            rent.setOdometerDistance(Long.parseLong(from.getOdometerDistance()));
        }
        if (from.getPromotionCode() != null){
            rent.setPromotionCode(new PromotionCodeMapper().reverse(from.getPromotionCode()));
        }
        rent.setPaymentMethod(PaymentMethod.valueOf(from.getPaymentMethod()));
        if(from.getIsFinished() != null) {
            rent.setFinished(Boolean.parseBoolean(from.getIsFinished()));
        }
        rent.setCurrency(Currency.valueOf(from.getCurrency()));
        return rent;
    }
}
