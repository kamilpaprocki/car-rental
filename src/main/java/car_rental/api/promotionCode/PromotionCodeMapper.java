package car_rental.api.promotionCode;

import car_rental.utils.DTOMapper;

public class PromotionCodeMapper implements DTOMapper<PromotionCode, PromotionCodeDTO> {


    @Override
    public PromotionCodeDTO from(PromotionCode from) {
        return PromotionCodeDTO.builder()
                .promotionCode(from.getPromotionCode())
                .generationDate(from.getGenerateDate().toString())
                .expDate(from.getExpDate().toString())
                .discount(from.getDiscount().toString())
                .isMultipleUse((String.valueOf(from.isMultipleUse())))
                .availableUse(String.valueOf(from.getAvailableUse()))
                .isActive(String.valueOf(from.isActive()))
                .build();
    }
}
