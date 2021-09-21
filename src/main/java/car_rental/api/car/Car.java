package car_rental.api.car;



import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@DynamicInsert
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @Column(name = "brand", nullable = false, length = 60)
    private String brand;

    @Column(name = "model", nullable = false, length = 30)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "segment", nullable = false)
    private CarSegment segment;

    @Column(name = "model_year", nullable = false, length = 4)
    private int modelYear;

    @Column(name = "current_odometer", nullable = false)
    private long currentOdometer;

    @Column(name = "price_per_day", nullable = false)
    private BigDecimal pricePerDay;

    @Column(name = "is_available")
    @ColumnDefault("true")
    private Boolean isAvailable;

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarSegment getSegment() {
        return segment;
    }

    public void setSegment(CarSegment segment) {
        this.segment = segment;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public long getCurrentOdometer() {
        return currentOdometer;
    }

    public void setCurrentOdometer(long currentOdometer) {
        this.currentOdometer = currentOdometer;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return getModelYear() == car.getModelYear() && getCurrentOdometer() == car.getCurrentOdometer() && getId().equals(car.getId()) && getBrand().equals(car.getBrand()) && getModel().equals(car.getModel()) && getSegment() == car.getSegment() && getPricePerDay().equals(car.getPricePerDay()) && isAvailable.equals(car.isAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBrand(), getModel(), getSegment(), getModelYear(), getCurrentOdometer(), getPricePerDay(), isAvailable);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", segment=" + segment +
                ", modelYear=" + modelYear +
                ", currentOdometer=" + currentOdometer +
                ", pricePerDay=" + pricePerDay +
                ", isAvailable=" + isAvailable +
                '}';
    }

    private Car(CarBuilder c){
        this.id = c.id;
        this.brand = c.brand;
        this.model = c.model;
        this.segment = c.segment;
        this.modelYear = c.modelYear;
        this.currentOdometer = c.currentOdometer;
        this.pricePerDay = c.pricePerDay;
        this.isAvailable = c.isAvailable;
    }

    public static final class CarBuilder {
        private Long id;
        private String brand;
        private String model;
        private CarSegment segment;
        private int modelYear;
        private long currentOdometer;
        private BigDecimal pricePerDay;
        private Boolean isAvailable;

        public static CarBuilder builder() {
            return new CarBuilder();
        }

        public CarBuilder id(String id) {
            if (id == null){
                this.id = null;
                return this;
            }
            this.id = Long.parseLong(id);
            return this;
        }

        public CarBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder segment(CarSegment segment) {
            this.segment = segment;
            return this;
        }

        public CarBuilder modelYear(int modelYear) {
            this.modelYear = modelYear;
            return this;
        }

        public CarBuilder currentOdometer(long currentOdometer) {
            this.currentOdometer = currentOdometer;
            return this;
        }

        public CarBuilder pricePerDay(BigDecimal pricePerDay) {
            this.pricePerDay = pricePerDay;
            return this;
        }

        public CarBuilder isAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}
