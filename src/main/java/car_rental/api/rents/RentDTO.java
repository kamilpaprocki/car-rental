package car_rental.api.rents;

import car_rental.api.car.CarDTO;
import car_rental.api.promotionCode.PromotionCodeDTO;
import car_rental.api.user.UserAppDTO;
import car_rental.api.utils.DateFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;

@DateFormat
public class RentDTO {

    @JsonIgnore
    private String id;

    @JsonProperty(value = "user")
    private UserAppDTO userApp;

    @JsonProperty(value = "car")
    private CarDTO car;

    @JsonProperty(value = "rent_date")
    @Valid
    private String rentDate;

    @JsonProperty(value = "rent_address")
    @Valid
    private AddressDTO rentAddress;

    @JsonProperty(value = "planned_return_date")
    private String plannedReturnDate;

    @JsonProperty(value = "return_date")
    @Valid
    private String returnDate;

    @JsonProperty(value = "rental_days")
    private String rentalDays;

    @JsonProperty(value = "return_address")
    private AddressDTO returnAddress;

    @JsonProperty(value = "rental_cost")
    private String rentalCost;

    @JsonProperty(value = "odometer_distance")
    private String odometerDistance;

    @JsonProperty(value = "promotion_code_id")
    private PromotionCodeDTO promotionCode;

    @JsonProperty(value = "payment_method")
    private String paymentMethod;

    @JsonProperty(value = "is_finished")
    private String isFinished;

    public RentDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserApp(UserAppDTO userApp) {
        this.userApp = userApp;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public void setRentAddress(AddressDTO rentAddress) {
        this.rentAddress = rentAddress;
    }

    public void setPlannedReturnDate(String plannedReturnDate) {
        this.plannedReturnDate = plannedReturnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setRentalDays(String rentalDays) {
        this.rentalDays = rentalDays;
    }

    public void setReturnAddress(AddressDTO returnAddress) {
        this.returnAddress = returnAddress;
    }

    public void setRentalCost(String rentalCost) {
        this.rentalCost = rentalCost;
    }

    public void setOdometerDistance(String odometerDistance) {
        this.odometerDistance = odometerDistance;
    }

    public void setPromotionCode(PromotionCodeDTO promotionCode) {
        this.promotionCode = promotionCode;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    public static RentDTOBuilder builder() {
        return new RentDTOBuilder();
    }

    private RentDTO(RentDTOBuilder b){
        this.id = b.id;
        this.userApp = b.userApp;
        this.promotionCode = b.promotionCode;
        this.rentalDays = b.rentalDays;
        this.rentalCost = b.rentalCost;
        this.rentAddress = b.rentAddress;
        this.plannedReturnDate = b.plannedReturnDate;
        this.odometerDistance = b.odometerDistance;
        this.paymentMethod = b.paymentMethod;
        this.isFinished = b.isFinished;
        this.rentDate = b.rentDate;
        this.returnAddress = b.returnAddress;
        this.returnDate = b.returnDate;
        this.car = b.car;
    }

    public static final class RentDTOBuilder {
        String id;
        private UserAppDTO userApp;
        private CarDTO car;
        private String rentDate;
        private AddressDTO rentAddress;
        private String plannedReturnDate;
        private String rentalDays;
        private AddressDTO returnAddress;
        private String returnDate;
        private String rentalCost;
        private String odometerDistance;
        private PromotionCodeDTO promotionCode;
        private String paymentMethod;
        private String isFinished;

        public RentDTOBuilder id(String id){
            this.id = id;
            return this;
        }

        public RentDTOBuilder userApp(UserAppDTO userApp) {
            this.userApp = userApp;
            return this;
        }

        public RentDTOBuilder car(CarDTO car) {
            this.car = car;
            return this;
        }

        public RentDTOBuilder rentDate(String rentDate) {
            this.rentDate = rentDate;
            return this;
        }

        public RentDTOBuilder rentAddress(AddressDTO rentAddress) {
            this.rentAddress = rentAddress;
            return this;
        }

        public RentDTOBuilder plannedReturnDate(String plannedReturnDate) {
            this.plannedReturnDate = plannedReturnDate;
            return this;
        }

        public RentDTOBuilder rentalDays(String rentalDays) {
            this.rentalDays = rentalDays;
            return this;
        }

        public RentDTOBuilder returnAddress(AddressDTO returnAddress) {
            this.returnAddress = returnAddress;
            return this;
        }

        public RentDTOBuilder returnDate(String returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public RentDTOBuilder rentalCost(String rentalCost) {
            this.rentalCost = rentalCost;
            return this;
        }

        public RentDTOBuilder odometerDistance(String odometerDistance) {
            this.odometerDistance = odometerDistance;
            return this;
        }

        public RentDTOBuilder promotionCode(PromotionCodeDTO promotionCode) {
            this.promotionCode = promotionCode;
            return this;
        }

        public RentDTOBuilder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public RentDTOBuilder isFinished(String isFinished) {
            this.isFinished = isFinished;
            return this;
        }

        public RentDTO build() {
            return new RentDTO(this);
        }
    }



    public UserAppDTO getUserApp() {
        return userApp;
    }

    public CarDTO getCar() {
        return car;
    }

    public String getRentDate() {
        return rentDate;
    }

    public AddressDTO getRentAddress() {
        return rentAddress;
    }

    public String getPlannedReturnDate() {
        return plannedReturnDate;
    }

    public String getRentalDays() {
        return rentalDays;
    }

    public AddressDTO getReturnAddress() {
        return returnAddress;
    }

    public String getRentalCost() {
        return rentalCost;
    }

    public String getOdometerDistance() {
        return odometerDistance;
    }

    public PromotionCodeDTO getPromotionCode() {
        return promotionCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public String getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "RentDTO{" +
                "id='" + id + '\'' +
                ", userApp=" + userApp +
                ", car=" + car +
                ", rentDate='" + rentDate + '\'' +
                ", rentAddress=" + rentAddress +
                ", plannedReturnDate='" + plannedReturnDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", rentalDays='" + rentalDays + '\'' +
                ", returnAddress=" + returnAddress +
                ", rentalCost='" + rentalCost + '\'' +
                ", odometerDistance='" + odometerDistance + '\'' +
                ", promotionCode=" + promotionCode +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", isFinished='" + isFinished + '\'' +
                '}';
    }
}
