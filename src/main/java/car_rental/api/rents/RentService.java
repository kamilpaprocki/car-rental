package car_rental.api.rents;

import car_rental.api.car.Car;
import car_rental.api.car.CarDTO;
import car_rental.api.car.CarMapper;
import car_rental.api.exceptions.WrongDataFormatException;
import car_rental.api.promotionCode.PromotionCode;
import car_rental.api.promotionCode.PromotionCodeDTO;
import car_rental.api.promotionCode.PromotionCodeMapper;
import car_rental.api.promotionCode.PromotionCodeService;
import car_rental.api.user.UserApp;
import car_rental.api.user.UserAppDTO;
import car_rental.api.user.UserAppMapper;
import car_rental.api.utils.DateParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;

@Service
public class RentService {

    private final RentRepository rentRepository;
    private final PromotionCodeService promotionCodeService;

    public RentService(RentRepository rentRepository, PromotionCodeService promotionCodeService) {
        this.rentRepository = rentRepository;
        this.promotionCodeService = promotionCodeService;
    }

    public Rent createRent(Rent rent, String promotionCode){
        rent.setPromotionCode(promotionCodeService.usePromotionCode(promotionCode));
       rent.setRentalCost(rent.getRentalCost()
                .subtract(
                        rent.getRentalCost().multiply(BigDecimal.valueOf(0.01)).multiply
                        (promotionCodeService.getPromotionCodeByCode(promotionCode).getDiscount())));
        return rentRepository.save(rent);
    }

    public Rent createRent(Rent rent){
        return rentRepository.save(rent);
    }

    public Rent getRentById(Long id){
        return rentRepository.findById(id).orElse(null);
    }

    public List<Rent> getAllRents(){
        return rentRepository.findAll();
    }

    public Rent extendPlannedRentDays(Long id, int days){
        Rent rent = getRentById(id);
        if (rent == null){
            throw new WrongRentException("There is no rent with id: " + id);
        }
        if(days <= 0){
            throw new WrongRentException("Days can not be less than zero. Input days: " + days);
        }
        rent.setPlannedReturnDate(Date.valueOf(rent.getPlannedReturnDate().toLocalDate().plusDays(days)));
        return rentRepository.save(rent);
    }

    public Rent updatePlannedReturnDate(Long id, String plannedReturnDate){
        Rent rent = getRentById(id);
        if (rent == null){
            throw new WrongRentException("There is no rent with id: " + id);
        }
        if (!plannedReturnDate.matches("\\d{4}[-]\\d{1,2}[-]\\d{1,2}")){
            throw new WrongRentException("Wrong Data format. Input planned return date: " + plannedReturnDate);
        }
        rent.setPlannedReturnDate(Date.valueOf(plannedReturnDate));
        return rentRepository.save(rent);
    }

    @Transactional
    public int deleteRentById(Long id){
        return rentRepository.deleteRentById(id);
    }

    public long getRentalDays(Date rentDay, Date returnDate){

        if ((rentDay.compareTo(returnDate)) > 0){
          throw new WrongDataFormatException("Rent day can not be after return date");
       }

       if (rentDay.compareTo(returnDate) == 0){
           return 1;
       }

       return (returnDate.getTime()-rentDay.getTime())/(1000*60*60*24);
    }

    public RentDTO addPromotionCode(RentDTO rentDTO, PromotionCodeDTO promotionCodeDTO){

        promotionCodeService.usePromotionCode(promotionCodeDTO.getPromotionCodeDTO());
        PromotionCode promotionCode = promotionCodeService.getPromotionCodeByCode(promotionCodeDTO.getPromotionCodeDTO());
        promotionCodeDTO = new PromotionCodeMapper().map(promotionCode);
        rentDTO.setPromotionCode(promotionCodeDTO);
        BigDecimal discount = new BigDecimal(promotionCodeDTO.getDiscount());
        BigDecimal rentalCost = new BigDecimal(rentDTO.getRentalCost());
        rentalCost = (rentalCost.multiply(BigDecimal.valueOf(100).subtract(discount))).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.CEILING);
        rentDTO.setRentalCost(rentalCost.toString());
        return rentDTO;
    }

    public RentDTO addUserAndCar(RentDTO rentDTO, UserApp userApp, Car car){
        UserAppDTO userAppDTO = new UserAppMapper().map(userApp);
        rentDTO.setUserApp(userAppDTO);
        CarDTO carDTO = new CarMapper().map(car);
        rentDTO.setCar(carDTO);
        return rentDTO;
    }

    public RentDTO addRentDetails(RentDTO rentDTO){
        DateParser dateParser = new DateParser();
        long rentalDays = getRentalDays(dateParser.parseDate(rentDTO.getRentDate()), dateParser.parseDate(rentDTO.getPlannedReturnDate()));
        rentDTO.setRentalDays(String.valueOf(rentalDays));

        String pricePerDay = rentDTO.getCar().getPricePerDay();
        BigDecimal rentalCost = new BigDecimal(pricePerDay).multiply(BigDecimal.valueOf(rentalDays));
        rentDTO.setRentalCost(rentalCost.toString());
        return rentDTO;
    }
}
