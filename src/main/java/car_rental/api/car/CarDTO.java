package car_rental.api.car;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarDTO {

    @JsonProperty(value = "brand")
    private String brand;

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "segment")
    private String segment;

    @JsonProperty(value = "model_year")
    private String modelYear;

    @JsonProperty(value = "current_odemeter")
    private String currentOdemeter;

    @JsonProperty(value = "price_per_day")
    private String pricePerDay;

    private CarDTO(CarDTOBuilder b) {
        this.brand = b.brand;
        this.currentOdemeter = b.currentOdemeter;
        this.modelYear = b.modelYear;
        this.segment = b.segment;
        this.model = b.model;
        this.pricePerDay = b.pricePerDay;
    }

    public static CarDTOBuilder builder() {
        return new CarDTOBuilder();
    }

    public static class CarDTOBuilder {
        private String brand;
        private String model;
        private String segment;
        private String modelYear;
        private String currentOdemeter;
        private String pricePerDay;

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

        public CarDTOBuilder currentOdemeter(String currentOdemeter) {
            this.currentOdemeter = currentOdemeter;
            return this;
        }

        public CarDTOBuilder pricePerDay(String pricePerDay) {
            this.pricePerDay = pricePerDay;
            return this;
        }

        public CarDTO build() {
            return new CarDTO(this);
        }
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

    public String getCurrentOdemeter() {
        return currentOdemeter;
    }

    public String getPricePerDay() {
        return pricePerDay;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", segment='" + segment + '\'' +
                ", modelYear='" + modelYear + '\'' +
                ", currentOdemeter='" + currentOdemeter + '\'' +
                ", pricePerDay='" + pricePerDay + '\'' +
                '}';
    }
}
