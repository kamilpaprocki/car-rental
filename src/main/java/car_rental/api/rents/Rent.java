package car_rental.api.rents;

import car_rental.api.car.Car;
import car_rental.api.currency.Currency;
import car_rental.api.promotionCode.PromotionCode;
import car_rental.api.user.UserApp;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;


@Entity
@Table(name = "rents")
@DynamicInsert
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private UserApp userApp;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "rent_date", nullable = false)
    private Date rentDate;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.ALL})
    @JoinColumn(name = "rent_address")
    private RentAddress rentAddress;

    @Column(name = "planned_return_date", nullable = false)
    private Date plannedReturnDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "rental_days")
    private long rentalDays;

   @OneToOne(cascade = {CascadeType.MERGE, CascadeType.ALL})
    @JoinColumn(name = "return_adress")
    private ReturnAddress returnAddress;

    @Column(name = "rental_cost")
    @ColumnDefault("0")
    private BigDecimal rentalCost;

    @Column(name = "odometer_distance")
    @ColumnDefault("0")
    private Long odometerDistance;

    @JoinColumn(name = "promotion_code_id")
    @OneToOne(cascade = {CascadeType.MERGE}, orphanRemoval = true)
    private PromotionCode promotionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "is_finished")
    @ColumnDefault("false")
    private Boolean isFinished;

    public Rent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public RentAddress getRentAddress() {
        return rentAddress;
    }

    public void setRentAddress(RentAddress rentAddress) {
        this.rentAddress = rentAddress;
    }

    public Date getPlannedReturnDate() {
        return plannedReturnDate;
    }

    public void setPlannedReturnDate(Date plannedReturnDate) {
        this.plannedReturnDate = plannedReturnDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public long getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(long rentalDays) {
        this.rentalDays = rentalDays;
    }

    public ReturnAddress getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(ReturnAddress returnAddress) {
        this.returnAddress = returnAddress;
    }

    public BigDecimal getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(BigDecimal rentalCost) {
        this.rentalCost = rentalCost;
    }

    public Long getOdometerDistance() {
        return odometerDistance;
    }

    public void setOdometerDistance(Long odometerDistance) {
        this.odometerDistance = odometerDistance;
    }

    public PromotionCode getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(PromotionCode promotionCode) {
        this.promotionCode = promotionCode;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Boolean isFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", userApp=" + userApp +
                ", car=" + car +
                ", rentDate=" + rentDate +
                ", rentAddress=" + rentAddress +
                ", plannedReturnDate=" + plannedReturnDate +
                ", returnDate=" + returnDate +
                ", rentalDays=" + rentalDays +
                ", returnAddress=" + returnAddress +
                ", rentalCost=" + rentalCost +
                ", odometerDistance=" + odometerDistance +
                ", promotionCode=" + promotionCode +
                ", currency=" + currency +
                ", paymentMethod=" + paymentMethod +
                ", isFinished=" + isFinished +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rent)) return false;
        Rent rent = (Rent) o;
        return getRentalDays() == rent.getRentalDays() && Objects.equals(getId(), rent.getId()) && Objects.equals(getUserApp(), rent.getUserApp()) && Objects.equals(getCar(), rent.getCar()) && Objects.equals(getRentDate(), rent.getRentDate()) && Objects.equals(getRentAddress(), rent.getRentAddress()) && Objects.equals(getPlannedReturnDate(), rent.getPlannedReturnDate()) && Objects.equals(getReturnDate(), rent.getReturnDate()) && Objects.equals(getReturnAddress(), rent.getReturnAddress()) && Objects.equals(getRentalCost(), rent.getRentalCost()) && Objects.equals(getOdometerDistance(), rent.getOdometerDistance()) && Objects.equals(getPromotionCode(), rent.getPromotionCode()) && getCurrency() == rent.getCurrency() && getPaymentMethod() == rent.getPaymentMethod() && Objects.equals(isFinished, rent.isFinished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserApp(), getCar(), getRentDate(), getRentAddress(), getPlannedReturnDate(), getReturnDate(), getRentalDays(), getReturnAddress(), getRentalCost(), getOdometerDistance(), getPromotionCode(), getCurrency(), getPaymentMethod(), isFinished);
    }

    public static RentBuilder builder() {
        return new RentBuilder();
    }

    private Rent(RentBuilder r){
        this.id = r.id;
        this.userApp = r.userApp;
        this.car = r.car;
        this.rentDate = r.rentDate;
        this.rentAddress = r.rentAddress;
        this.plannedReturnDate = r.plannedReturnDate;
        this.returnDate = r.returnDate;
        this.rentalDays = r.rentalDays;
        this.returnAddress = r.returnAddress;
        this.rentalCost = r.rentalCost;
        this.odometerDistance = r.odometerDistance;
        this.promotionCode = r.promotionCode;
        this.currency = r.currency;
        this.paymentMethod = r.paymentMethod;
        this.isFinished = r.isFinished;
    }


    public static final class RentBuilder {
        private Long id;
        private UserApp userApp;
        private Car car;
        private Date rentDate;
        private RentAddress rentAddress;
        private Date plannedReturnDate;
        private Date returnDate;
        private long rentalDays;
        private ReturnAddress returnAddress;
        private BigDecimal rentalCost;
        private Long odometerDistance;
        private PromotionCode promotionCode;
        private Currency currency;
        private PaymentMethod paymentMethod;
        private Boolean isFinished;

        private RentBuilder() {
        }

        public RentBuilder id(String id) {
            if (id == null){
                this.id = null;
                return this;
            }
            this.id = Long.parseLong(id);
            return this;
        }

        public RentBuilder userApp(UserApp userApp) {
            this.userApp = userApp;
            return this;
        }

        public RentBuilder car(Car car) {
            this.car = car;
            return this;
        }

        public RentBuilder rentDate(Date rentDate) {
            this.rentDate = rentDate;
            return this;
        }

        public RentBuilder rentAddress(RentAddress rentAddress) {
            this.rentAddress = rentAddress;
            return this;
        }

        public RentBuilder plannedReturnDate(Date plannedReturnDate) {
            this.plannedReturnDate = plannedReturnDate;
            return this;
        }

        public RentBuilder returnDate(Date returnDate) {
            if (returnDate == null){
                this.returnDate = null;
                return this;
            }
            this.returnDate = returnDate;
            return this;
        }

        public RentBuilder rentalDays(long rentalDays) {
            this.rentalDays = rentalDays;
            return this;
        }

        public RentBuilder returnAddress(ReturnAddress returnAddress) {
            this.returnAddress = returnAddress;
            return this;
        }

        public RentBuilder rentalCost(BigDecimal rentalCost) {
            this.rentalCost = rentalCost;
            return this;
        }

        public RentBuilder odometerDistance(String odometerDistance) {
            if (odometerDistance == null){
                this.odometerDistance = null;
                return this;
            }
            this.odometerDistance = Long.parseLong(odometerDistance);
            return this;
        }

        public RentBuilder promotionCode(PromotionCode promotionCode) {
            if (promotionCode == null){
                this.promotionCode = null;
                return this;
            }
            this.promotionCode = promotionCode;
            return this;
        }

        public RentBuilder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public RentBuilder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public RentBuilder isFinished(Boolean isFinished) {
            this.isFinished = isFinished;
            return this;
        }

        public Rent build() {
            return new Rent(this);
        }
    }
}
