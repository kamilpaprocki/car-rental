package car_rental.api.promotionCode;

import car_rental.api.utils.DTOMapper;
import car_rental.api.utils.DateParser;

import java.math.BigDecimal;

public class PromotionCodeMapper implements DTOMapper<PromotionCode, PromotionCodeDTO> {


    @Override
    public PromotionCodeDTO mapToDTO(PromotionCode from) {
        if (from == null){
            return null;
        }
        DateParser dateParser = new DateParser();
        return PromotionCodeDTO.builder()
                .id(String.valueOf(from.getId()))
                .promotionCode(from.getPromotionCode())
                .generateDate(dateParser.parseDateToStringDTO(from.getGenerateDate()))
                .expDate(dateParser.parseDateToStringDTO(from.getExpDate()))
                .discount(String.valueOf(from.getDiscount()))
                .isMultipleUse(String.valueOf(from.isMultipleUse()))
                .availableUse(String.valueOf(from.getAvailableUse()))
                .isActive(String.valueOf(from.isActive()))
                .build();
    }

    @Override
    public PromotionCode mapToDAO(PromotionCodeDTO from) {
        if(from == null){
            return null;
        }
        DateParser dateParser = new DateParser();
       return PromotionCode.builder()
               .id(from.getId())
               .promotionCode(from.getPromotionCodeDTO())
               .usedDate(dateParser.parseStringToDateDAO(from.getUsedDate()))
               .generateDate(dateParser.parseStringToDateDAO(from.getGenerateDate()))
               .expDate(dateParser.parseStringToDateDAO(from.getExpDate()))
               .discount(new BigDecimal(from.getDiscount()))
               .isMultipleUse(Boolean.parseBoolean(from.getIsMultipleUse()))
               .availableUse(Integer.parseInt(from.getAvailableUse()))
               .isActive(Boolean.parseBoolean(from.getIsActive()))
               .build();
    }
}
