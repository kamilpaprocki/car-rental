package car_rental.api.promotionCode;

import car_rental.api.validators.PromotionCodeChecker;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PromotionCodeChecker
public class PromotionCodeDTO {

    @JsonProperty(value = "id")
    private String id;

   @JsonProperty(value = "promotion_code")
   @NotEmpty(message = "Field: \"promotion code\" cannot be empty.")
   @NotNull(message = "This can not be null.")
   private String promotionCodeDTO;

   @JsonProperty(value = "used_date")
   private String usedDate;

   @JsonProperty(value = "generate_date")
   private String generateDate;

   @JsonProperty(value = "exp_date")
    private String expDate;

   @JsonProperty(value = "discount")
    private String discount;

   @JsonProperty(value = "is_multiple_use")
    private String isMultipleUse;

   @JsonProperty(value = "available_use")
    private String availableUse;

   @JsonProperty(value = "is_active")
    private String isActive;

    public PromotionCodeDTO() {
    }

    public static PromotionCodeDTOBuilder builder() {
        return new PromotionCodeDTOBuilder();
    }

    private PromotionCodeDTO(PromotionCodeDTOBuilder b){
        this.id = b.id;
        this.usedDate = b.usedDate;
        this.discount = b.discount;
        this.generateDate = b.generateDate;
        this.isMultipleUse = b.isMultipleUse;
        this.isActive = b.isActive;
        this.promotionCodeDTO = b.promotionCode;
        this.expDate = b.expDate;
        this.availableUse = b.availableUse;
    }

    public static final class PromotionCodeDTOBuilder {

        private String id;
        private String promotionCode;
        private String usedDate;
        private String generateDate;
        private String expDate;
        private String discount;
        private String isMultipleUse;
        private String availableUse;
        private String isActive;

        public PromotionCodeDTOBuilder id(String id){
            this.id = id;
            return this;
        }

        public PromotionCodeDTOBuilder promotionCode(String promotionCode) {
            this.promotionCode = promotionCode;
            return this;
        }

        public PromotionCodeDTOBuilder usedDate(String usedDate){
            if (usedDate == null){
                this.usedDate = null;
                return this;
            }
            this.usedDate = usedDate;
            return this;
        }

        public PromotionCodeDTOBuilder generateDate(String generateDate) {
            this.generateDate = generateDate;
            return this;
        }

        public PromotionCodeDTOBuilder expDate(String expDate) {
            this.expDate = expDate;
            return this;
        }

        public PromotionCodeDTOBuilder discount(String discount) {
            this.discount = discount;
            return this;
        }

        public PromotionCodeDTOBuilder isMultipleUse(String isMultipleUse) {
            this.isMultipleUse = isMultipleUse;
            return this;
        }

        public PromotionCodeDTOBuilder availableUse(String availableUse) {
            this.availableUse = availableUse;
            return this;
        }

        public PromotionCodeDTOBuilder isActive(String isActive) {
            this.isActive = isActive;
            return this;
        }

        public PromotionCodeDTO build() {
           return new PromotionCodeDTO(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getPromotionCodeDTO() {
        return promotionCodeDTO;
    }

    public String getUsedDate() {
        return usedDate;
    }

    public String getGenerateDate() {
        return generateDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getDiscount() {
        return discount;
    }

    public String getIsMultipleUse() {
        return isMultipleUse;
    }

    public String getAvailableUse() {
        return availableUse;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPromotionCodeDTO(String promotionCodeDTO) {
        this.promotionCodeDTO = promotionCodeDTO;
    }

    public void setUsedDate(String usedDate) {
        this.usedDate = usedDate;
    }

    public void setGenerateDate(String generateDate) {
        this.generateDate = generateDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setIsMultipleUse(String isMultipleUse) {
        this.isMultipleUse = isMultipleUse;
    }

    public void setAvailableUse(String availableUse) {
        this.availableUse = availableUse;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "PromotionCodeDTO{" +
                "promotionCode='" + promotionCodeDTO + '\'' +
                ", generationDate='" + generateDate + '\'' +
                ", expDate='" + expDate + '\'' +
                ", discount='" + discount + '\'' +
                ", isMultipleUse='" + isMultipleUse + '\'' +
                ", availableUse='" + availableUse + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
