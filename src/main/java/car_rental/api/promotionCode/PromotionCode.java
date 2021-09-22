package car_rental.api.promotionCode;


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
    @Column(name = "promotion_code_id")
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

    public PromotionCode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    private PromotionCode(PromotionCodeBuilder p){
        this.id = p.id;
        this.promotionCode = p.promotionCode;
        this.usedDate = p.usedDate;
        this.generateDate = p.generateDate;
        this.expDate = p.expDate;
        this.discount = p.discount;
        this.availableUse = p.availableUse;
        this.isMultipleUse = p.isMultipleUse;
        this.isActive = p.isActive;
    }

    public static PromotionCodeBuilder builder() {
        return new PromotionCodeBuilder();
    }

    public static final class PromotionCodeBuilder {
        private Long id;
        private String promotionCode;
        private Date usedDate;
        private Date generateDate;
        private Date expDate;
        private BigDecimal discount;
        private boolean isMultipleUse;
        private int availableUse;
        private Boolean isActive;

        private PromotionCodeBuilder() {
        }

        public PromotionCodeBuilder id(Long id) {
            if (id == null){
                this.id = null;
                return this;
            }
            this.id = id;
            return this;
        }

        public PromotionCodeBuilder promotionCode(String promotionCode){
            if (promotionCode == null){
                this.promotionCode = null;
                return this;
            }
            this.promotionCode = promotionCode;
            return this;
        }

        public PromotionCodeBuilder usedDate(Date usedDate) {
            if (usedDate == null){
                this.usedDate = null;
                return this;
            }
            this.usedDate = usedDate;
            return this;
        }

        public PromotionCodeBuilder generateDate(Date generateDate) {
            if (generateDate == null){
                this.generateDate = null;
                return this;
            }
            this.generateDate = generateDate;
            return this;
        }

        public PromotionCodeBuilder expDate(Date expDate) {
            if (expDate == null){
                this.expDate = null;
                return this;
            }
            this.expDate = expDate;
            return this;
        }

        public PromotionCodeBuilder discount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public PromotionCodeBuilder isMultipleUse(boolean isMultipleUse) {
            this.isMultipleUse = isMultipleUse;
            return this;
        }

        public PromotionCodeBuilder availableUse(int availableUse) {
            this.availableUse = availableUse;
            return this;
        }

        public PromotionCodeBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public PromotionCode build() {
            return new PromotionCode(this);
        }
    }
}
