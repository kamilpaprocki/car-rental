package car_rental.api.rents;

import car_rental.api.PromotionCode.PromotionCode;
import car_rental.api.car.Car;
import car_rental.api.client.Client;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn(name = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn(name = "car_id")
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

    @Formula(value = "DATEDIFF(planned_return_date, rent_date)")
    private long rentalDays;

   @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "return_adress", nullable = false)
    private ReturnAddress returnAddress;

    @Column(name = "rental_cost")
    @ColumnDefault("0")
    private BigDecimal rentalCost;

    @Column(name = "odometer_distance")
    @ColumnDefault("0")
    private long odomoterDistance;

    @PrimaryKeyJoinColumn(name = "promotion_code_id")
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public long getOdomoterDistance() {
        return odomoterDistance;
    }

    public void setOdomoterDistance(long odomoterDistance) {
        this.odomoterDistance = odomoterDistance;
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
                ", client=" + client +
                ", car=" + car +
                ", rentDate=" + rentDate +
                ", rentAddress=" + rentAddress +
                ", plannedReturnDate=" + plannedReturnDate +
                ", returnDate=" + returnDate +
                ", rentalDays=" + rentalDays +
                ", returnAddress=" + returnAddress +
                ", rentalCost=" + rentalCost +
                ", odomoterDistance=" + odomoterDistance +
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
        return getRentalDays() == rent.getRentalDays() && getOdomoterDistance() == rent.getOdomoterDistance() && getId().equals(rent.getId()) && getClient().equals(rent.getClient()) && getCar().equals(rent.getCar()) && getRentDate().equals(rent.getRentDate()) && getRentAddress().equals(rent.getRentAddress()) && getPlannedReturnDate().equals(rent.getPlannedReturnDate()) && Objects.equals(getReturnDate(), rent.getReturnDate()) && getReturnAddress().equals(rent.getReturnAddress()) && Objects.equals(getRentalCost(), rent.getRentalCost()) && Objects.equals(getPromotionCode(), rent.getPromotionCode()) && getPaymentMethod() == rent.getPaymentMethod() && isFinished.equals(rent.isFinished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClient(), getCar(), getRentDate(), getRentAddress(), getPlannedReturnDate(), getReturnDate(), getRentalDays(), getReturnAddress(), getRentalCost(), getOdomoterDistance(), getPromotionCode(), getPaymentMethod(), isFinished);
    }
}
