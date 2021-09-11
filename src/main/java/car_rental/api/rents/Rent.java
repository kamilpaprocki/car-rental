package car_rental.api.rents;

import car_rental.api.car.Car;
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
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @PrimaryKeyJoinColumn(name = "id")
    private UserApp userApp;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @PrimaryKeyJoinColumn(name = "id")
    private Car car;

    @Column(name = "rent_date", nullable = false)
    private Date rentDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rent_address")
    private RentAddress rentAddress;

    @Column(name = "planned_return_date", nullable = false)
    private Date plannedReturnDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "rental_days")
    private long rentalDays;

   @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "return_adress", nullable = false)
    private ReturnAddress returnAddress;

    @Column(name = "rental_cost")
    @ColumnDefault("0")
    private BigDecimal rentalCost;

    @Column(name = "odometer_distance")
    @ColumnDefault("0")
    private long odometerDistance;

    @PrimaryKeyJoinColumn(name = "id")
    @OneToOne(cascade = {CascadeType.MERGE}, orphanRemoval = true)
    private PromotionCode promotionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "is_finished")
    @ColumnDefault("false")
    private Boolean isFinished;

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

    public long getOdometerDistance() {
        return odometerDistance;
    }

    public void setOdometerDistance(long odomoterDistance) {
        this.odometerDistance = odomoterDistance;
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
                ", paymentMethod=" + paymentMethod +
                ", isFinished=" + isFinished +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rent)) return false;
        Rent rent = (Rent) o;
        return getRentalDays() == rent.getRentalDays() && getOdometerDistance() == rent.getOdometerDistance() && Objects.equals(getId(), rent.getId()) && getUserApp().equals(rent.getUserApp()) && getCar().equals(rent.getCar()) && getRentDate().equals(rent.getRentDate()) && getRentAddress().equals(rent.getRentAddress()) && getPlannedReturnDate().equals(rent.getPlannedReturnDate()) && getReturnDate().equals(rent.getReturnDate()) && getReturnAddress().equals(rent.getReturnAddress()) && Objects.equals(getRentalCost(), rent.getRentalCost()) && Objects.equals(getPromotionCode(), rent.getPromotionCode()) && getPaymentMethod() == rent.getPaymentMethod() && Objects.equals(isFinished, rent.isFinished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserApp(), getCar(), getRentDate(), getRentAddress(), getPlannedReturnDate(), getReturnDate(), getRentalDays(), getReturnAddress(), getRentalCost(), getOdometerDistance(), getPromotionCode(), getPaymentMethod(), isFinished);
    }
}
