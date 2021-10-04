package car_rental.api.rents;

import car_rental.api.car.CarDTO;
import car_rental.api.car.CarService;
import car_rental.api.exceptions.WrongArgumentException;
import car_rental.api.exceptions.WrongDataFormatException;
import car_rental.api.promotionCode.PromotionCodeDTO;
import car_rental.api.promotionCode.PromotionCodeMapper;
import car_rental.api.promotionCode.PromotionCodeService;
import car_rental.api.user.CustomUserDetailsService;
import car_rental.api.user.UserApp;
import car_rental.api.user.UserAppDTO;
import car_rental.api.user.UserAppMapper;
import car_rental.api.utils.DateParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentService {

    private final RentRepository rentRepository;
    private final PromotionCodeService promotionCodeService;
    private final CarService carService;
    private final CustomUserDetailsService customUserDetailsService;

    public RentService(RentRepository rentRepository, PromotionCodeService promotionCodeService, CarService carService, CustomUserDetailsService customUserDetailsService) {
        this.rentRepository = rentRepository;
        this.promotionCodeService = promotionCodeService;
        this.carService = carService;
        this.customUserDetailsService = customUserDetailsService;
    }

    public RentDTO createOrUpdateRent(RentDTO rentDTO, String promotionCode){
        Rent rent = new RentMapper().mapToDAO(rentDTO);
        rent.setPromotionCode(new PromotionCodeMapper().mapToDAO(promotionCodeService.usePromotionCode(promotionCode)));
       rent.setRentalCost(rent.getRentalCost()
                .subtract(
                        rent.getRentalCost().multiply(BigDecimal.valueOf(0.01)).multiply
                        (promotionCodeService.getPromotionCodeByCode(promotionCode).getDiscount())));

       return new RentMapper().mapToDTO(rentRepository.save(rent));
    }

    public RentDTO createOrUpdateRent(RentDTO rentDTO){
        rentRepository.save(new RentMapper().mapToDAO(rentDTO));
        return rentDTO;
    }

    public RentDTO getRentById(String rentId){
        if (rentId == null){
            throw new WrongArgumentException("Rent id cannot be null");
        }
        Rent rent = rentRepository.findById(Long.parseLong(rentId)).orElse(null);
        return new RentMapper().mapToDTO(rent);
    }

    public List<RentDTO> getAllRents(){
        List<Rent> rents = rentRepository.findAll();
        return rents.stream().map(new RentMapper() :: mapToDTO).collect(Collectors.toList());
    }

    public RentDTO extendPlannedRentDays(Long id, int days){
        Rent rent = getRentById(id);
        if (rent == null){
            throw new WrongRentException("There is no rent with id: " + id);
        }
        if(days <= 0){
            throw new WrongRentException("Days can not be less than zero. Input days: " + days);
        }
        rent.setPlannedReturnDate(Date.valueOf(rent.getPlannedReturnDate().toLocalDate().plusDays(days)));
        return new RentMapper().mapToDTO(rentRepository.save(rent));
    }

    public RentDTO updatePlannedReturnDate(Long id, String plannedReturnDate){
        Rent rent = getRentById(id);
        if (rent == null){
            throw new WrongRentException("There is no rent with id: " + id);
        }
        if (!plannedReturnDate.matches("\\d{4}[-]\\d{1,2}[-]\\d{1,2}")){
            throw new WrongRentException("Wrong Data format. Input planned return date: " + plannedReturnDate);
        }
        rent.setPlannedReturnDate(Date.valueOf(plannedReturnDate));
        return new RentMapper().mapToDTO(rentRepository.save(rent));
    }

    @Transactional
    public int deleteRentById(Long rentId){
        if (rentId == null){
            throw new WrongArgumentException("Rent id cannot be null");
        }
        return rentRepository.deleteRentById(rentId);
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
        PromotionCodeDTO promotionCode = promotionCodeService.getPromotionCodeDTOByCode(promotionCodeDTO.getPromotionCodeDTO());
        rentDTO.setPromotionCode(promotionCode);
        BigDecimal discount = new BigDecimal(promotionCode.getDiscount());
        BigDecimal rentalCost = new BigDecimal(rentDTO.getRentalCost());
        rentalCost = (rentalCost.multiply(BigDecimal.valueOf(100).subtract(discount))).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.CEILING);
        rentDTO.setRentalCost(rentalCost.toString());
        return rentDTO;
    }

    public RentDTO addUserAndCar(RentDTO rentDTO, UserApp userApp, CarDTO carDTO){
        UserAppDTO userAppDTO = new UserAppMapper().mapToDTO(userApp);
        rentDTO.setUserApp(userAppDTO);
        rentDTO.setCar(carDTO);
        return rentDTO;
    }

    public RentDTO addOrUpdateRentDetails(RentDTO rentDTO){
        DateParser dateParser = new DateParser();
        long rentalDays = getRentalDays(dateParser.parseStringToDateDAO(rentDTO.getRentDate()), dateParser.parseStringToDateDAO(rentDTO.getPlannedReturnDate()));
        rentDTO.setRentalDays(String.valueOf(rentalDays));

        String pricePerDay = rentDTO.getCar().getPricePerDay();
        BigDecimal rentalCost = new BigDecimal(pricePerDay).multiply(BigDecimal.valueOf(rentalDays));
        rentDTO.setRentalCost(String.valueOf(rentalCost));
        return rentDTO;
    }

    public Rent addRent(RentDTO rentDTO){
        Rent rent = new RentMapper().mapToDAO(rentDTO);
        rent.getCar().setAvailable(false);
        carService.createOrUpdateCar(rent.getCar());
        rent.getUserApp().setHasActiveRent(true);
        customUserDetailsService.updateUser(rent.getUserApp());
        return rentRepository.save(rent);
    }

    public List<RentDTO> getRentByUserApp(UserApp userApp){
        if (userApp == null){
            throw new WrongArgumentException("User cannot be a null");
        }
        List<Rent> rent = rentRepository.getRentByUserApp(userApp).orElseThrow(null);
        return rent.stream().map(new RentMapper() ::mapToDTO).collect(Collectors.toList());
    }

    public RentDTO updatePlannedReturnDate(RentDTO rentDTO){
        rentDTO = addOrUpdateRentDetails(rentDTO);
        if (rentDTO.getPromotionCode() != null){
            BigDecimal discount = new BigDecimal(rentDTO.getPromotionCode().getDiscount());
            BigDecimal rentalCost = new BigDecimal(rentDTO.getRentalCost());
            rentalCost = (rentalCost.multiply(BigDecimal.valueOf(100).subtract(discount))).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.CEILING);
            rentDTO.setRentalCost(String.valueOf(rentalCost));
        }
        return rentDTO;
    }

    public List<RentDTO> getActiveRents(){
        List<Rent> rents = rentRepository.getActiveRents().orElseThrow(null);
       return rents.stream().map(new RentMapper() ::mapToDTO).collect(Collectors.toList());
    }

    public List<RentDTO> getFinishedRents(){
        List<Rent> rents = rentRepository.getFinishedRents().orElseThrow(null);
        return rents.stream().map(new RentMapper() :: mapToDTO).collect(Collectors.toList());
    }


    public CarReturnOdometerWrapper getCarLastOdometer(RentDTO rentDTO){
        CarReturnOdometerWrapper carReturnOdometerWrapper = new CarReturnOdometerWrapper();
        carReturnOdometerWrapper.setLastOdometer(rentDTO.getCar().getCurrentOdometer());
        return carReturnOdometerWrapper;
    }

    public Rent finishRent(RentDTO rentDTO, CarReturnOdometerWrapper carReturnOdometerWrapper){

        if (carReturnOdometerWrapper.getCurrentOdometer() == null){
            throw new WrongArgumentException("Current odometer distance cannot be a null.");
        }

        if (carReturnOdometerWrapper.getLastOdometer() == null){
            carReturnOdometerWrapper.setCurrentOdometer(rentDTO.getCar().getCurrentOdometer());
        }

        if (!rentDTO.getPlannedReturnDate().equals(String.valueOf(Date.valueOf(LocalDate.now())))){
            rentDTO.setPlannedReturnDate(Date.valueOf(LocalDate.now()).toString());
            rentDTO = updatePlannedReturnDate(rentDTO);
        }

        Rent rent = new RentMapper().mapToDAO(rentDTO);

        if (rent.getRentDate().after(Date.valueOf(LocalDate.now()))){
            throw new WrongDataFormatException("Return date cannot be before rent date.");
        }

        long lastOdometer = Long.parseLong(carReturnOdometerWrapper.getLastOdometer());
        long currentOdometer = Long.parseLong(carReturnOdometerWrapper.getCurrentOdometer());
        long distance = currentOdometer - lastOdometer;
        rent.setOdometerDistance(distance);
        rent.setFinished(true);
        rent.setReturnDate(Date.valueOf(LocalDate.now()));
        rent.getCar().setAvailable(true);
        rent.getCar().setCurrentOdometer(currentOdometer);
        rent.getUserApp().setHasActiveRent(false);
        return rentRepository.save(rent);

    }

    private Rent getRentById(Long rentId){
        if (rentId == null){
            throw new WrongArgumentException("Rent id cannot be null");
        }
        return rentRepository.findById(rentId).orElse(null);
    }

    public RentDTO getRentDTOById(Long rentId){
        Rent rent = getRentById(rentId);
        return new RentMapper().mapToDTO(rent);
    }
}
