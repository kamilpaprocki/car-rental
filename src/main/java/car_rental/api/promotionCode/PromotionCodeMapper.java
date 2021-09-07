package car_rental.api.promotionCode;

import car_rental.api.utils.DTOMapper;
import car_rental.api.utils.DateParser;

import java.math.BigDecimal;

public class PromotionCodeMapper implements DTOMapper<PromotionCode, PromotionCodeDTO> {


    @Override
    public PromotionCodeDTO map(PromotionCode from) {
        return PromotionCodeDTO.builder()
                .id(from.getId().toString())
                .promotionCode(from.getPromotionCode())
                .generateDate(from.getGenerateDate().toString())
                .expDate(from.getExpDate().toString())
                .discount(from.getDiscount().toString())
                .isMultipleUse((String.valueOf(from.isMultipleUse())))
                .availableUse(String.valueOf(from.getAvailableUse()))
                .isActive(String.valueOf(from.isActive()))
                .build();
    }

    @Override
    public PromotionCode reverse(PromotionCodeDTO from) {
        PromotionCode promotionCode = new PromotionCode();
        DateParser dateParser = new DateParser();
        promotionCode.setId(Long.parseLong(from.getId()));
        promotionCode.setPromotionCode(from.getPromotionCodeDTO());
        promotionCode.setUsedDate(dateParser.parseDate(from.getUsedDate()));
        promotionCode.setGenerateDate(dateParser.parseDate(from.getUsedDate()));
        promotionCode.setExpDate(dateParser.parseDate(from.getUsedDate()));
        promotionCode.setDiscount(new BigDecimal(from.getDiscount()));
        promotionCode.setMultipleUse(Boolean.getBoolean(from.getIsMultipleUse()));
        promotionCode.setAvailableUse(Integer.parseInt(from.getAvailableUse()));
        return promotionCode;
    }
}
