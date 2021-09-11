package car_rental.api.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CarDTO {

    @JsonIgnore
    private String id;

    @JsonProperty(value = "brand")
    private String brand;

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "segment")
    private String segment;

    @JsonProperty(value = "model_year")
    private String modelYear;

    @JsonProperty(value = "current_odemeter")
    private String currentOdometer;

    @JsonProperty(value = "price_per_day")
    private String pricePerDay;

    @JsonProperty(value = "is_available")
    private String isAvailable;

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
        private String isAvailable;

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

        public CarDTOBuilder available(String isAvailable){
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

    public String isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", segment='" + segment + '\'' +
                ", modelYear='" + modelYear + '\'' +
                ", currentOdometer='" + currentOdometer + '\'' +
                ", pricePerDay='" + pricePerDay + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
