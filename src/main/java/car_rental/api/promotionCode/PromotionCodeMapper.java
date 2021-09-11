package car_rental.api.promotionCode;

import car_rental.api.utils.DTOMapper;
import car_rental.api.utils.DateParser;

import java.math.BigDecimal;

public class PromotionCodeMapper implements DTOMapper<PromotionCode, PromotionCodeDTO> {


    @Override
    public PromotionCodeDTO map(PromotionCode from) {

        if (from.getUsedDate() != null){
            return PromotionCodeDTO.builder()
                    .id(from.getId().toString())
                    .promotionCode(from.getPromotionCode())
                    .generateDate(from.getGenerateDate().toString())
                    .expDate(from.getExpDate().toString())
                    .discount(from.getDiscount().toString())
                    .isMultipleUse(String.valueOf(from.isMultipleUse()))
                    .usedDate(from.getUsedDate().toString())
                    .availableUse(String.valueOf(from.getAvailableUse()))
                    .isActive(from.isActive().toString())
                    .build();
        }

        return PromotionCodeDTO.builder()
                .id(from.getId().toString())
                .promotionCode(from.getPromotionCode())
                .generateDate(from.getGenerateDate().toString())
                .expDate(from.getExpDate().toString())
                .discount(from.getDiscount().toString())
                .isMultipleUse(String.valueOf(from.isMultipleUse()))
                .availableUse(String.valueOf(from.getAvailableUse()))
                .isActive(from.isActive().toString())
                .build();
    }

    @Override
    public PromotionCode reverse(PromotionCodeDTO from) {
        PromotionCode promotionCode = new PromotionCode();
        DateParser dateParser = new DateParser();
        if(from.getId() != null){
            promotionCode.setId(Long.parseLong(from.getId()));
        }
        promotionCode.setPromotionCode(from.getPromotionCodeDTO());
        if (from.getUsedDate() != null) {
            promotionCode.setUsedDate(dateParser.parseDate(from.getUsedDate()));
        }
        promotionCode.setGenerateDate(dateParser.parseDate(from.getGenerateDate()));
        promotionCode.setExpDate(dateParser.parseDate(from.getExpDate()));
        promotionCode.setDiscount(new BigDecimal(from.getDiscount()));
        promotionCode.setMultipleUse(Boolean.parseBoolean(from.getIsMultipleUse()));
        promotionCode.setAvailableUse(Integer.parseInt(from.getAvailableUse()));
        return promotionCode;
    }
}
