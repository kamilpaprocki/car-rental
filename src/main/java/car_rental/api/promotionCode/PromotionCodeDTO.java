package car_rental.api.promotionCode;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PromotionCodeDTO {

   @JsonProperty(value = "promotion_code")
   private String promotionCode;

   @JsonProperty(value = "generation_date")
   private String generationDate;

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

    public static PromotionCodeDTOBuilder builder() {
        return new PromotionCodeDTOBuilder();
    }

    private PromotionCodeDTO(PromotionCodeDTOBuilder b){
        this.discount = b.discount;
        this.generationDate = b.generationDate;
        this.isMultipleUse = b.isMultipleUse;
        this.isActive = b.isActive;
        this.promotionCode = b.promotionCode;
        this.expDate = b.expDate;
        this.availableUse = b.availableUse;
    }

    public static final class PromotionCodeDTOBuilder {
        private String promotionCode;
        private String generationDate;
        private String expDate;
        private String discount;
        private String isMultipleUse;
        private String availableUse;
        private String isActive;

        public PromotionCodeDTOBuilder promotionCode(String promotionCode) {
            this.promotionCode = promotionCode;
            return this;
        }

        public PromotionCodeDTOBuilder generationDate(String generationDate) {
            this.generationDate = generationDate;
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

    public String getPromotionCode() {
        return promotionCode;
    }

    public String getGenerationDate() {
        return generationDate;
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

    @Override
    public String toString() {
        return "PromotionCodeDTO{" +
                "promotionCode='" + promotionCode + '\'' +
                ", generationDate='" + generationDate + '\'' +
                ", expDate='" + expDate + '\'' +
                ", discount='" + discount + '\'' +
                ", isMultipleUse='" + isMultipleUse + '\'' +
                ", availableUse='" + availableUse + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
