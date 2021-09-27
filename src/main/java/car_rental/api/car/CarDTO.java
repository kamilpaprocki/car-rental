package car_rental.api.car;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CarDTO {

    @JsonProperty(value= "id")
    private String id;

    @JsonProperty(value = "brand")
    @NotEmpty(message = "This can not be empty")
    private String brand;

    @JsonProperty(value = "model")
    @NotEmpty(message = "This can not be empty")
    private String model;

    @JsonProperty(value = "segment")
    @NotNull(message = "This can not be null")
    private String segment;

    @JsonProperty(value = "model_year")
    @NotEmpty(message = "This can not be empty")
    private String modelYear;

    @JsonProperty(value = "current_odometer")
    @NotEmpty(message = "This can not be empty")
    private String currentOdometer;

    @JsonProperty(value = "price_per_day")
    @NotEmpty(message = "This can not be empty")
    private String pricePerDay;

    @JsonProperty(value = "is_available")
    @NotNull(message = "This can not be null")
    private Boolean isAvailable;

    public CarDTO() {
    }

    private CarDTO(CarDTOBuilder b) {
        this.id = b.id;
        this.brand = b.brand;
        this.currentOdometer = b.currentOdometer;
        this.modelYear = b.modelYear;
        this.segment = b.segment;
        this.model = b.model;
        this.pricePerDay = b.pricePerDay;
        this.isAvailable = b.isAvailable;
    }

    public static CarDTOBuilder builder() {
        return new CarDTOBuilder();
    }

    public static class CarDTOBuilder {
        private String id;
        private String brand;
        private String model;
        private String segment;
        private String modelYear;
        private String currentOdometer;
        private String pricePerDay;
        private Boolean isAvailable;

        public CarDTOBuilder id(String id){
            this.id = id;
            return this;
        }

        public CarDTOBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public CarDTOBuilder model(String model) {
            this.model = model;
            return this;
        }

        public CarDTOBuilder segment(String segment) {
            this.segment = segment;
            return this;
        }

        public CarDTOBuilder modelYear(String modelYear) {
            this.modelYear = modelYear;
            return this;
        }

        public CarDTOBuilder currentOdometer(String currentOdometer) {
            this.currentOdometer = currentOdometer;
            return this;
        }

        public CarDTOBuilder pricePerDay(String pricePerDay) {
            this.pricePerDay = pricePerDay;
            return this;
        }

        public CarDTOBuilder available(Boolean isAvailable){
            this.isAvailable = isAvailable;
            return this;
        }

        public CarDTO build() {
            return new CarDTO(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getSegment() {
        return segment;
    }

    public String getModelYear() {
        return modelYear;
    }

    public String getCurrentOdometer() {
        return currentOdometer;
    }

    public String getPricePerDay() {
        return pricePerDay;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public void setCurrentOdometer(String currentOdometer) {
        this.currentOdometer = currentOdometer;
    }

    public void setPricePerDay(String pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", segment='" + segment + '\'' +
                ", modelYear='" + modelYear + '\'' +
                ", currentOdometer='" + currentOdometer + '\'' +
                ", pricePerDay='" + pricePerDay + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                '}';
    }
}
