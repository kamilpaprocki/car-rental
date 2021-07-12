package car_rental.api.rents;

import car_rental.api.promotionCode.PromotionCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
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

}
