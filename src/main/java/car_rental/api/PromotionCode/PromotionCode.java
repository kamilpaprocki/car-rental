package car_rental.api.PromotionCode;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;


@Entity
@Table(name = "promotion_code")
@DynamicInsert
public class PromotionCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "promotion_code", nullable = false)
    private String promotionCode;

    @Column(name = "used_date")
    private Date usedDate;

    @Column(name = "generate_date", nullable = false)
    private Date generateDate;

    @Column(name = "exp_date", nullable = false)
    private Date expDate;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name = "is_multiple_use", nullable = false)
    private boolean isMultipleUse;

    @Column(name = "available_use", nullable = false)
    private int availableUse;

    @Column(name = "is_active")
    @ColumnDefault("true")
    private Boolean isActive;


    public Long getId() {
        return id;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public boolean isMultipleUse() {
        return isMultipleUse;
    }

    public void setMultipleUse(boolean multipleUse) {
        isMultipleUse = multipleUse;
    }

    public int getAvailableUse() {
        return availableUse;
    }

    public void setAvailableUse(int availableUse) {
        this.availableUse = availableUse;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "PromotionCode{" +
                "id=" + id +
                ", promotionCode='" + promotionCode + '\'' +
                ", usedDate=" + usedDate +
                ", generateDate=" + generateDate +
                ", expDate=" + expDate +
                ", discount=" + discount +
                ", isMultipleUse=" + isMultipleUse +
                ", availableUse=" + availableUse +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PromotionCode)) return false;
        PromotionCode that = (PromotionCode) o;
        return isMultipleUse() == that.isMultipleUse() && getAvailableUse() == that.getAvailableUse() && getId().equals(that.getId()) && getPromotionCode().equals(that.getPromotionCode()) && Objects.equals(getUsedDate(), that.getUsedDate()) && getGenerateDate().equals(that.getGenerateDate()) && getExpDate().equals(that.getExpDate()) && getDiscount().equals(that.getDiscount()) && isActive.equals(that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPromotionCode(), getUsedDate(), getGenerateDate(), getExpDate(), getDiscount(), isMultipleUse(), getAvailableUse(), isActive);
    }
}
