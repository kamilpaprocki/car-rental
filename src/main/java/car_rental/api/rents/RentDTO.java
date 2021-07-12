package car_rental.api.rents;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RentDTO {

    @JsonProperty(value = "client")
    private String client;

    @JsonProperty(value = "car")
    private String car;

    @JsonProperty(value = "rent_date")
    private String rentDate;

    @JsonProperty(value = "rent_address")
    private String rentAddress;

    @JsonProperty(value = "planned_return_date")
    private String plannedReturnDate;

    @JsonProperty(value = "return_date")
    private String returnDate;

    private String rentalDays;

    @JsonProperty(value = "return_address")
    private String returnAddress;

    @JsonProperty(value = "rental_cost")
    private String rentalCost;

    @JsonProperty(value = "odometer_distance")
    private String odomoterDistance;

    @JsonProperty(value = "promotion_code_id")
    private String promotionCode;

    @JsonProperty(value = "payment_method")
    private String paymentMethod;

    @JsonProperty(value = "is_finished")
    private String isFinished;

    public static RentDTOBuilder builder() {
        return new RentDTOBuilder();
    }

    private RentDTO(RentDTOBuilder b){
        this.client = b.client;
        this.promotionCode = b.promotionCode;
        this.rentalDays = b.rentalDays;
        this.rentalCost = b.rentalCost;
        this.rentAddress = b.rentAddress;
        this.plannedReturnDate = b.plannedReturnDate;
        this.odomoterDistance = b.odomoterDistance;
        this.paymentMethod = b.paymentMethod;
        this.isFinished = b.isFinished;
        this.rentDate = b.rentDate;
        this.returnAddress = b.returnAddress;
        this.car = b.car;
    }

    public static final class RentDTOBuilder {
        private String client;
        private String car;
        private String rentDate;
        private String rentAddress;
        private String plannedReturnDate;
        private String rentalDays;
        private String returnAddress;
        private String returnDate;
        private String rentalCost;
        private String odomoterDistance;
        private String promotionCode;
        private String paymentMethod;
        private String isFinished;

        public RentDTOBuilder client(String client) {
            this.client = client;
            return this;
        }

        public RentDTOBuilder car(String car) {
            this.car = car;
            return this;
        }

        public RentDTOBuilder rentDate(String rentDate) {
            this.rentDate = rentDate;
            return this;
        }

        public RentDTOBuilder rentAddress(String rentAddress) {
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

        public RentDTOBuilder returnAddress(String returnAddress) {
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

        public RentDTOBuilder odomoterDistance(String odomoterDistance) {
            this.odomoterDistance = odomoterDistance;
            return this;
        }

        public RentDTOBuilder promotionCode(String promotionCode) {
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

    public String getClient() {
        return client;
    }

    public String getCar() {
        return car;
    }

    public String getRentDate() {
        return rentDate;
    }

    public String getRentAddress() {
        return rentAddress;
    }

    public String getPlannedReturnDate() {
        return plannedReturnDate;
    }

    public String getRentalDays() {
        return rentalDays;
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public String getRentalCost() {
        return rentalCost;
    }

    public String getOdomoterDistance() {
        return odomoterDistance;
    }

    public String getPromotionCode() {
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
                "client='" + client + '\'' +
                ", car='" + car + '\'' +
                ", rentDate='" + rentDate + '\'' +
                ", rentAddress='" + rentAddress + '\'' +
                ", plannedReturnDate='" + plannedReturnDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", rentalDays='" + rentalDays + '\'' +
                ", returnAddress='" + returnAddress + '\'' +
                ", rentalCost='" + rentalCost + '\'' +
                ", odomoterDistance='" + odomoterDistance + '\'' +
                ", promotionCode='" + promotionCode + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", isFinished='" + isFinished + '\'' +
                '}';
    }
}
