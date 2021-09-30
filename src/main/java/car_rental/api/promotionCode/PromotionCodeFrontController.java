package car_rental.api.promotionCode;

import car_rental.api.exceptions.PromotionCodeNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class PromotionCodeFrontController {

private final PromotionCodeService promotionCodeService;

    public PromotionCodeFrontController(PromotionCodeService promotionCodeService) {
        this.promotionCodeService = promotionCodeService;
    }

    @GetMapping("/promotioncode")
    @PreAuthorize("hasAnyRole('USER', 'WORKER', 'ADMIN')")
    public String promotionCodePage(Model model,
                                    @RequestParam(required = false) Long id,
                                    @RequestParam(required = false) String promotioncode,
                                    @RequestParam(required = false) String promotioncodestatus){

        if ("allPromotionCodes".equals(promotioncodestatus)){
            List<PromotionCodeDTO> promotionCodeDTOList = promotionCodeService.getAllPromotionCodes();
            if (promotionCodeDTOList.isEmpty()){
                throw new PromotionCodeNotFoundException("There is no promotion codes");
            }
            model.addAttribute("allPromotionCodes", promotionCodeDTOList);

        }
        if("activePromotionCodes".equals(promotioncodestatus)){
            List<PromotionCodeDTO> promotionCodeDTOList = promotionCodeService.getActivePromotionCodes();
            if (promotionCodeDTOList.isEmpty()){
                throw new PromotionCodeNotFoundException("There is no active promotion codes");
            }
            model.addAttribute("activePromotionCodes", promotionCodeDTOList);

        }
        if ("inactivePromotionCodes".equals(promotioncodestatus)){
            List<PromotionCodeDTO> promotionCodeDTOList = promotionCodeService.getInactivePromotionCodes();
            if (promotionCodeDTOList.isEmpty()){
                throw new PromotionCodeNotFoundException("There is no inactive promotion codes");
            }
            model.addAttribute("inactivePromotionCodes", promotionCodeService.getInactivePromotionCodes());
        }
       if ("promotionCodeId".equals(promotioncodestatus)){
           PromotionCodeDTO promotionCodeDTO = promotionCodeService.getPromotionCodeById(id);
           if (promotionCodeDTO == null){
               throw new PromotionCodeNotFoundException("There is no promotion code with id: " + id);
           }
           model.addAttribute("promotionCodeById", promotionCodeDTO);
       }

        if ("promotionCode".equals(promotioncodestatus)){
            PromotionCodeDTO promotionCodeDTO = promotionCodeService.getPromotionCodeDTOByCode(promotioncode);
            if (promotionCodeDTO == null){
                throw new PromotionCodeNotFoundException("Wrong promotion code: " + promotioncode);
            }
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
        PromotionCodeDTO generatedPromotionCode = promotionCodeService.getGeneratedPromotionCode(promotionCodeService.createPromotionCode(discount, activeDays, isMultipleUse));
        redirectAttributes.addFlashAttribute("generatedPromotionCode", generatedPromotionCode.getPromotionCodeDTO());
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
        promotionCodeService.deletePromotionCodeById(id);
        return "redirect:/home?info=deleted";
    }



}
