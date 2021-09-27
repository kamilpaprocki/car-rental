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
    public RentDTO mapToDTO(Rent from) {
        if (from == null){
            return null;
        }
        DateParser dateParser = new DateParser();
        return  RentDTO.builder()
                .id(from.getId())
                .userApp(new UserAppMapper().mapToDTO(from.getUserApp()))
                .car(new CarMapper().mapToDTO(from.getCar()))
                .rentDate(dateParser.parseDateToStringDTO(from.getRentDate()))
                .rentAddress(new AddressMapper().mapToDTO(from.getRentAddress()))
                .plannedReturnDate(dateParser.parseDateToStringDTO(from.getPlannedReturnDate()))
                .returnDate(dateParser.parseDateToStringDTO(from.getReturnDate()))
                .rentalDays(String.valueOf(from.getRentalDays()))
                .returnAddress(new AddressMapper().mapToDTO(from.getReturnAddress()))
                .rentalCost(String.valueOf(from.getRentalCost()))
                .odometerDistance(from.getOdometerDistance())
                .promotionCode(new PromotionCodeMapper().mapToDTO(from.getPromotionCode()))
                .currency(from.getCurrency().toString())
                .paymentMethod(from.getPaymentMethod().toString())
                .isFinished(from.isFinished())
                .build();
    }

    @Override
    public Rent mapToDAO(RentDTO from) {
        if(from == null){
            return null;
        }
        DateParser dateParser = new DateParser();
       return Rent.builder()
               .id(from.getId())
               .userApp(new UserAppMapper().mapToDAO(from.getUserApp()))
               .car(new CarMapper().mapToDAO(from.getCar()))
               .rentDate(dateParser.parseStringToDateDAO(from.getRentDate()))
               .rentAddress(new AddressMapper().mapAddressToRentAddressDAO(from.getRentAddress()))
               .plannedReturnDate(dateParser.parseStringToDateDAO(from.getPlannedReturnDate()))
               .returnDate(dateParser.parseStringToDateDAO(from.getReturnDate()))
               .rentalDays(Long.parseLong(from.getRentalDays()))
               .returnAddress(new AddressMapper().mapAddressToReturnAddressDAO(from.getReturnAddress()))
               .rentalCost(new BigDecimal(from.getRentalCost()))
               .odometerDistance(from.getOdometerDistance())
               .promotionCode(new PromotionCodeMapper().mapToDAO(from.getPromotionCode()))
               .currency(Currency.valueOf(from.getCurrency()))
               .paymentMethod(PaymentMethod.valueOf(from.getPaymentMethod()))
               .isFinished(Boolean.parseBoolean(from.getIsFinished()))
               .build();
    }
}
