package car_rental.api.promotionCode;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.PromotionCodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<PromotionCodeDTO> createPromotionCode(@RequestParam BigDecimal discount, @RequestParam int activeDays, @RequestParam boolean isMultipleUse){
        try{
            return new ResponseEntity<>(promotionCodeService.getGeneratedPromotionCode(promotionCodeService.createPromotionCode(discount, activeDays, isMultipleUse)), HttpStatus.CREATED);
        }catch(WrongPromotionCodeException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @GetMapping("/promotioncodes/")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<List<PromotionCodeDTO>> getAllPromotionCodes(@RequestParam(required = false) String promotionCodeStatus){
        List<PromotionCodeDTO> promotionCodes;
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

    @GetMapping("/promotioncodes/code")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<PromotionCodeDTO> getPromotionCodeById(@RequestParam Long id){
        PromotionCodeDTO promotionCode = promotionCodeService.getPromotionCodeById(id);
        if (promotionCode == null){
            throw new PromotionCodeNotFoundException("There is no promotion code with id: " + id);
        }
        return new ResponseEntity<>(promotionCode, HttpStatus.OK);
    }
    @GetMapping("/promotioncodes/code/{promotioncode}")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<PromotionCodeDTO> getPromotionCodeByCode(@PathVariable String promotioncode){
        PromotionCodeDTO pC = promotionCodeService.getPromotionCodeDTOByCode(promotioncode);
        if (pC == null){
            throw new PromotionCodeNotFoundException("There are no promotion code : " + promotioncode);
        }
        return new ResponseEntity<>(pC, HttpStatus.OK);
    }

    @GetMapping("/promotioncodes/{promotioncode}/use")
    @PreAuthorize("hasAnyRole('USER','WORKER', 'ADMIN')")
    public ResponseEntity<PromotionCode> usePromotionCode(@PathVariable String promotioncode){
        try{
            promotionCodeService.usePromotionCode(promotioncode);
        }
        catch (WrongPromotionCodeException e){
            throw new PromotionCodeNotFoundException(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/promotioncodes/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PromotionCode> deletePromotionCodeById(@RequestParam Long id){
        if (promotionCodeService.deletePromotionCodeById(id) > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }



}
