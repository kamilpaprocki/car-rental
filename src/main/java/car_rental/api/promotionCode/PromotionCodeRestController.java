package car_rental.api.promotionCode;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.PromotionCodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PromotionCodeRestController {

    private final PromotionCodeService promotionCodeService;

    public PromotionCodeRestController(PromotionCodeService promotionCodeService) {
        this.promotionCodeService = promotionCodeService;
    }

    @PostMapping("/promotioncodes")
    public ResponseEntity<PromotionCode> createPromotionCode(@RequestParam BigDecimal discount, @RequestParam int activeDays, @RequestParam boolean isMultipleUse){
        try{
            return new ResponseEntity<>(promotionCodeService.createPromotionCode(discount, activeDays, isMultipleUse), HttpStatus.CREATED);
        }catch(WrongPromotionCodeException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/promotioncodes/")
    public ResponseEntity<List<PromotionCode>> getAllPromotionCodes(@RequestParam(required = false) String promotionCodeStatus){
        List<PromotionCode> promotionCodes;
        if (promotionCodeStatus == null){
            promotionCodes = promotionCodeService.getAllPromotionCodes();
            if (promotionCodes.isEmpty()){
                throw new PromotionCodeNotFoundException("There are no promotion codes");
            }
            return new ResponseEntity<>(promotionCodes, HttpStatus.OK);
        }
        if ("active".equals(promotionCodeStatus)){
            promotionCodes = promotionCodeService.getActivePromotionCodes();
            if (promotionCodes.isEmpty()){
                throw new PromotionCodeNotFoundException("There are no promotion codes active");
            }
            return new ResponseEntity<>(promotionCodes, HttpStatus.OK);
        }
        if ("inactive".equals(promotionCodeStatus)){
            promotionCodes = promotionCodeService.getInactivePromotionCodes();
            if (promotionCodes.isEmpty()){
                throw new PromotionCodeNotFoundException("There are no promotion codes inactive");
            }
            return new ResponseEntity<>(promotionCodes, HttpStatus.OK);
        }
        throw new BadRequestException("Wrong input parameter. Input promotion code status: " + promotionCodeStatus);
    }

    @GetMapping("/promotioncodes/{id}")
    public ResponseEntity<PromotionCode> getPromotionCodeById(@PathVariable Long id){
        PromotionCode promotionCode = promotionCodeService.getPromotionCodeById(id);
        if (promotionCode == null){
            throw new PromotionCodeNotFoundException("There is no promotion code with id: " + id);
        }
        return new ResponseEntity<>(promotionCode, HttpStatus.OK);
    }

    @GetMapping("/promotioncodes/code/{promotioncode}")
    public ResponseEntity<PromotionCode> getPromotionCodeByCode(@PathVariable String promotioncode){
        PromotionCode pC = promotionCodeService.getPromotionCodeByCode(promotioncode);
        if (pC == null){
            throw new PromotionCodeNotFoundException("There are no promotion code : " + promotioncode);
        }
        return new ResponseEntity<>(pC, HttpStatus.OK);
    }

    @GetMapping("/promotioncodes/{promotioncode}/use")
    public ResponseEntity<PromotionCode> usePromotionCode(@PathVariable String promotioncode){
        try{
            promotionCodeService.usePromotionCode(promotioncode);
        }
        catch (WrongPromotionCodeException e){
            throw new PromotionCodeNotFoundException(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/promotioncodes/{id}/delete")
    public ResponseEntity<PromotionCode> deletePromotionCodeById(@PathVariable Long id){
        if (promotionCodeService.deletePromotionCodeById(id) > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }



}
