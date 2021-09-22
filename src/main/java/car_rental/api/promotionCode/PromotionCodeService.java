package car_rental.api.promotionCode;


import car_rental.api.exceptions.WrongArgumentException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PromotionCodeService {

    private final PromotionCodeRepository promotionCodeRepository;

    private final int availableUse = 10;
    private final BigDecimal maxDiscount = new BigDecimal(90);

    public PromotionCodeService(PromotionCodeRepository promotionCodeRepository) {
        this.promotionCodeRepository = promotionCodeRepository;
    }

    public PromotionCode createPromotionCode(BigDecimal discount, int activeDays, boolean isMultipleUse){
        if (discount.compareTo(BigDecimal.ZERO) <=0){
            throw new WrongPromotionCodeException("Wrong discount. This can not be less than zero. Input discount: " + discount);
        }
        if (discount.compareTo(maxDiscount) > 0){
            throw new WrongPromotionCodeException("Wrong discount. This can not be more than max discount. Input discount: " + discount);
        }
        if (activeDays <= 0){
            throw new WrongPromotionCodeException("Wrong Discount. Active days can not be less than zero. Input active days: " + activeDays);
        }
        PromotionCode promotionCode = new PromotionCode();
        promotionCode.setPromotionCode(UUID.randomUUID().toString());
        promotionCode.setGenerateDate(Date.valueOf(LocalDate.now()));
        promotionCode.setExpDate(Date.valueOf(promotionCode.getGenerateDate().toLocalDate().plusDays(activeDays)));
        promotionCode.setDiscount(discount);
        promotionCode.setMultipleUse(isMultipleUse);
        if (!isMultipleUse){
            promotionCode.setAvailableUse(1);
            return promotionCodeRepository.save(promotionCode);
        }
        promotionCode.setAvailableUse(availableUse);
        return promotionCodeRepository.save(promotionCode);
    }

    public List<PromotionCodeDTO> getAllPromotionCodes(){
        List<PromotionCode> promotionCodes = promotionCodeRepository.findAll();
        return promotionCodes.stream().map(new PromotionCodeMapper() :: mapToDTO).collect(Collectors.toList());
    }

    public PromotionCodeDTO getPromotionCodeById(Long promotionCodeId){
        if (promotionCodeId == null){
            throw new WrongArgumentException("Promotion code id cannot be null");
        }
        PromotionCode promotionCode = promotionCodeRepository.findById(promotionCodeId).orElse(null);
        return new PromotionCodeMapper().mapToDTO(promotionCode);
    }

    public List<PromotionCodeDTO> getActivePromotionCodes(){
        List<PromotionCode> promotionCodes = promotionCodeRepository.getActivePromotionCodes().orElseThrow(null);
        return promotionCodes.stream().map(new PromotionCodeMapper() :: mapToDTO).collect(Collectors.toList());
    }

    public List<PromotionCodeDTO> getInactivePromotionCodes(){
        List<PromotionCode> promotionCodes = promotionCodeRepository.getInactivePromotionCodes().orElseThrow(null);
        return promotionCodes.stream().map(new PromotionCodeMapper() :: mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public int deletePromotionCodeById(Long promotionCodeId){
        if (promotionCodeId == null){
            throw new WrongArgumentException("Promotion code id cannot be a null");
        }
        return promotionCodeRepository.deletePromotionCodeById(promotionCodeId);
    }

    public PromotionCodeDTO getPromotionCodeDTOByCode(String promotionCode){
        PromotionCode pC = promotionCodeRepository.getPromotionCodeByCode(promotionCode).orElse(null);
        return new PromotionCodeMapper().mapToDTO(pC);
    }

    public PromotionCode getPromotionCodeByCode(String promotionCode){
        return promotionCodeRepository.getPromotionCodeByCode(promotionCode).orElse(null);
    }

    public PromotionCode usePromotionCode(String promotionCode){
        PromotionCode pC = getPromotionCodeByCode(promotionCode);
        if (pC == null){
            throw new WrongPromotionCodeException("Wrong promotion code. Input promotion code: " + promotionCode);
        }
        if (!pC.isActive()){
            throw new WrongPromotionCodeException("Used Promotion Code. Input promotion code: " + promotionCode);
        }
        pC.setAvailableUse(pC.getAvailableUse()-1);
        if (pC.getAvailableUse() == 0){
            pC.setUsedDate(Date.valueOf(LocalDate.now()));
            pC.setActive(false);
        }
        return promotionCodeRepository.save(pC);
    }

    public boolean isCorrectAndAvailablePromotionCode(String promotionCode){
        PromotionCode pC = getPromotionCodeByCode(promotionCode);
        if (pC == null){
            return false;
        }
        if(pC.getExpDate().before(Date.valueOf(LocalDate.now()))){
            pC.setUsedDate(pC.getExpDate());
            pC.setActive(false);
            promotionCodeRepository.save(pC);
        }
        return pC.isActive();
    }

    public PromotionCodeDTO getGeneratedPromotionCode(PromotionCode promotionCode){
        return new PromotionCodeMapper().mapToDTO(promotionCode);
    }


}
