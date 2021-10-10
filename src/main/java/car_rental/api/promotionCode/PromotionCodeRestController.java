package car_rental.api.promotionCode;

import car_rental.api.exceptions.BadRequestException;
import car_rental.api.exceptions.PromotionCodeNotFoundException;
import car_rental.api.exceptions.WrongArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(PromotionCodeRestController.class);

    public PromotionCodeRestController(PromotionCodeService promotionCodeService) {
        this.promotionCodeService = promotionCodeService;
    }

    @PostMapping("/promotioncodes")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<PromotionCodeDTO> createPromotionCode(@RequestParam BigDecimal discount, @RequestParam int activeDays, @RequestParam boolean isMultipleUse){
            logger.info("Generate new promotion code with {} discount, with {} active days and is multiple use {}.", discount, activeDays, isMultipleUse);
            return new ResponseEntity<>(promotionCodeService.getGeneratedPromotionCode(promotionCodeService.createPromotionCode(discount, activeDays, isMultipleUse)), HttpStatus.CREATED);
    }

    @GetMapping("/promotioncodes/")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public ResponseEntity<List<PromotionCodeDTO>> getAllPromotionCodes(@RequestParam(required = false) String promotionCodeStatus){
        List<PromotionCodeDTO> promotionCodes;
        if (promotionCodeStatus == null){
            promotionCodes = promotionCodeService.getAllPromotionCodes();
            if (promotionCodes.isEmpty()){
                logger.error("List of all promotion codes is empty");
                throw new PromotionCodeNotFoundException("There are no promotion codes");
            }
            logger.info("Return {} promotion codes.", promotionCodes.size());
            return new ResponseEntity<>(promotionCodes, HttpStatus.OK);
        }
        if ("active".equals(promotionCodeStatus)){
            promotionCodes = promotionCodeService.getActivePromotionCodes();
            if (promotionCodes.isEmpty()){
                logger.error("List of active promotion codes is empty");
                throw new PromotionCodeNotFoundException("There are no promotion codes active");
            }
            logger.info("Return {} active promotion codes.", promotionCodes.size());
            return new ResponseEntity<>(promotionCodes, HttpStatus.OK);
        }
        if ("inactive".equals(promotionCodeStatus)){
            promotionCodes = promotionCodeService.getInactivePromotionCodes();
            if (promotionCodes.isEmpty()){
                logger.error("List of inactive promotion codes is empty");
                throw new PromotionCodeNotFoundException("There are no promotion codes inactive");
            }
            logger.info("Return {} inactive promotion codes.", promotionCodes.size());
            return new ResponseEntity<>(promotionCodes, HttpStatus.OK);
        }
        logger.error("Wrong promotion code status: {}.", promotionCodeStatus);
        throw new BadRequestException("Wrong input parameter. Input promotion code status: " + promotionCodeStatus);
    }

    @GetMapping("/promotioncodes/code")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<PromotionCodeDTO> getPromotionCodeById(@RequestParam Long id){
        PromotionCodeDTO promotionCode = promotionCodeService.getPromotionCodeById(id);
        if (promotionCode == null){
            logger.error("Promotion code with id {} not found.", id);
            throw new PromotionCodeNotFoundException("There is no promotion code with id: " + id);
        }
        logger.info("Return promotion code with id {}.", id);
        return new ResponseEntity<>(promotionCode, HttpStatus.OK);
    }
    @GetMapping("/promotioncodes/code/{promotioncode}")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public ResponseEntity<PromotionCodeDTO> getPromotionCodeByCode(@PathVariable String promotioncode){
        PromotionCodeDTO pC = promotionCodeService.getPromotionCodeDTOByCode(promotioncode);
        if (pC == null){
            logger.error("Promotion code {} not found.", promotioncode);
            throw new PromotionCodeNotFoundException("There are no promotion code : " + promotioncode);
        }
        logger.info("Return promotion code {}.", promotioncode);
        return new ResponseEntity<>(pC, HttpStatus.OK);
    }

    @GetMapping("/promotioncodes/{promotioncode}/use")
    @PreAuthorize("hasAnyRole('USER','WORKER', 'ADMIN')")
    public ResponseEntity<PromotionCodeDTO> usePromotionCode(@PathVariable String promotioncode){
        try{
            promotionCodeService.usePromotionCode(promotioncode);
        }
        catch (PromotionCodeNotFoundException e){
            logger.error(e.getMessage());
            throw new PromotionCodeNotFoundException(e.getMessage());
        }
        logger.info("Promotion code {} is activated.", promotioncode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/promotioncodes/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PromotionCode> deletePromotionCodeById(@RequestParam Long id){
            if (promotionCodeService.deletePromotionCodeById(id) > 0){
                logger.info("Delete promotion code with id {}.", id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        logger.info("Promotion code with id {} not exists.", id);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }



}
