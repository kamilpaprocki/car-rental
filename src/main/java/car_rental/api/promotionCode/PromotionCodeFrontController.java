package car_rental.api.promotionCode;

import car_rental.api.exceptions.WrongArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class PromotionCodeFrontController {

private final PromotionCodeService promotionCodeService;

private final static Logger logger = LoggerFactory.getLogger(PromotionCodeFrontController.class);

    public PromotionCodeFrontController(PromotionCodeService promotionCodeService) {
        this.promotionCodeService = promotionCodeService;
    }

    @GetMapping("/promotioncode")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String promotionCodePage(Model model,
                                    @RequestParam(required = false) Long id,
                                    @RequestParam(required = false) String promotioncode,
                                    @RequestParam(required = false) String promotioncodestatus){

        List<PromotionCodeDTO> promotionCodeDTOList;

        if ("allPromotionCodes".equals(promotioncodestatus)){
            promotionCodeDTOList = promotionCodeService.getAllPromotionCodes();
            if (promotionCodeDTOList.isEmpty()){
                logger.error("List of all promotion codes is empty");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no promotion codes");
            }
            logger.info("Return {} promotion codes.", promotionCodeDTOList.size());
            model.addAttribute("allPromotionCodes", promotionCodeDTOList);

        }
        if("activePromotionCodes".equals(promotioncodestatus)){
            promotionCodeDTOList = promotionCodeService.getActivePromotionCodes();
            if (promotionCodeDTOList.isEmpty()){
                logger.error("List of active promotion codes is empty");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no active promotion codes");
            }
            logger.info("Return {} active promotion codes.", promotionCodeDTOList.size());
            model.addAttribute("activePromotionCodes", promotionCodeDTOList);

        }
        if ("inactivePromotionCodes".equals(promotioncodestatus)){
            promotionCodeDTOList = promotionCodeService.getInactivePromotionCodes();
            if (promotionCodeDTOList.isEmpty()){
                logger.error("List of inactive promotion codes is empty");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no inactive promotion codes");
            }
            logger.info("Return {} inactive promotion codes.", promotionCodeDTOList.size());
            model.addAttribute("inactivePromotionCodes", promotionCodeService.getInactivePromotionCodes());
        }
       if ("promotionCodeId".equals(promotioncodestatus)){
           PromotionCodeDTO promotionCodeDTO = promotionCodeService.getPromotionCodeById(id);
           if (promotionCodeDTO == null){
               logger.error("Promotion code with id {} not found.", id);
               throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no promotion code with id: " + id);
           }
           logger.info("Return promotion code with id {}.", id);
           model.addAttribute("promotionCodeById", promotionCodeDTO);
       }

        if ("promotionCode".equals(promotioncodestatus)){
            PromotionCodeDTO promotionCodeDTO = promotionCodeService.getPromotionCodeDTOByCode(promotioncode);
            if (promotionCodeDTO == null){
                logger.error("Promotion code {} not found.", promotioncode);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong promotion code: " + promotioncode);
            }
            logger.info("Return promotion code: {}", promotioncode);
            model.addAttribute("promotionCode", promotionCodeDTO);

        }
        return "promotioncodes";
    }

    @GetMapping("/generate/promotioncode")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String getPromotionCodePage(@ModelAttribute("generatedPromotionCode") String generatedPromotionCode){
        return "generate-promotioncode";
    }

    @PostMapping("/generate/promotioncode")
    @PreAuthorize("hasAnyRole('WORKER', 'ADMIN')")
    public String generatePromotionCode(RedirectAttributes redirectAttributes, @RequestParam BigDecimal discount, @RequestParam int activeDays, @RequestParam boolean isMultipleUse){
        try{
            logger.info("Generate new promotion code with {} discount, with {} active days and is multiple use {}.", discount, activeDays, isMultipleUse);
            PromotionCodeDTO generatedPromotionCode = promotionCodeService.getGeneratedPromotionCode(promotionCodeService.createPromotionCode(discount, activeDays, isMultipleUse));
            redirectAttributes.addFlashAttribute("generatedPromotionCode", generatedPromotionCode.getPromotionCodeDTO());
        }catch (WrongArgumentException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return "redirect:/generate/promotioncode?info=generated";
    }

    @GetMapping("/promotioncodes")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String getPromotionCodesPage(){
        return "get-promotion-code";
    }

    @GetMapping("/delete/delete-promotion-code")
    @PreAuthorize("hasRole('ADMIN')")
    public String getDeletePromotionCode(){
        return "delete-promotion-code";
    }

    @GetMapping("/delete/promotioncode")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePromotionCodePage(@RequestParam(required = false) Long id){
        try{
            if (promotionCodeService.deletePromotionCodeById(id) > 0){
                logger.info("Delete promotion code with id {}.", id);
                return "redirect:/home?info=deleted";
            }
        }catch (WrongArgumentException e){
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        logger.info("Promotion code with id {} not exist.", id);
        return "redirect:/home";
    }



}
