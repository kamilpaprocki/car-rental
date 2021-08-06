package car_rental.api.promotionCode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class PromotionCodeFrontController {

private final PromotionCodeService promotionCodeService;

    public PromotionCodeFrontController(PromotionCodeService promotionCodeService) {
        this.promotionCodeService = promotionCodeService;
    }

    @GetMapping("/promotioncode")
    public String promotionCodePage(Model model,
                                    @RequestParam(required = false) Long id,
                                    @RequestParam(required = false) String promotioncode,
                                    @RequestParam(required = false) String promotioncodestatus){
        if ("allPromotionCodes".equals(promotioncodestatus)){
            model.addAttribute("allPromotionCodes", promotionCodeService.getAllPromotionCodes());

        }
        if("activePromotionCodes".equals(promotioncodestatus)){
            model.addAttribute("activePromotionCodes", promotionCodeService.getActivePromotionCodes());

        }
        if ("inactivePromotionCodes".equals(promotioncodestatus)){
            model.addAttribute("inactivePromotionCodes", promotionCodeService.getInactivePromotionCodes());

        }
        if (id != null){
            model.addAttribute("promotionCodeById", promotionCodeService.getPromotionCodeById(id));

        }
        if (promotioncode != null){
            model.addAttribute("promotionCode", promotionCodeService.getPromotionCodeByCode(promotioncode));

        }
        return "promotioncodes";
    }

    @GetMapping("/generate/promotioncode")
    public String getPromotionCodePage(Model model, @ModelAttribute("generatedPromotionCode") String generatedPromotionCode){
        model.addAttribute("promotioncode", new PromotionCode());
        return "generate-promotioncode";
    }

    @PostMapping("/generate/promotioncode")
    public String generatePromotionCode(RedirectAttributes redirectAttributes, @RequestParam BigDecimal discount, @RequestParam int activeDays, @RequestParam boolean isMultipleUse){
        PromotionCode promotionCode = promotionCodeService.createPromotionCode(discount, activeDays, isMultipleUse);
        redirectAttributes.addFlashAttribute("generatedPromotionCode", promotionCode.getPromotionCode());
        return "redirect:/generate/promotioncode?info=generated";
    }

    @GetMapping("/promotioncodes")
    public String getPromotionCodesPage(){
        return "get-promotion-code";
    }

    @GetMapping("/delete/delete-promotion-code")
    public String getDeletePromotionCode(){
        return "delete-promotion-code";
    }

    @GetMapping("/delete/promotioncode")
    public String deletePromotionCodePage(@RequestParam(required = false) Long id){
        promotionCodeService.deletePromotionCodeById(id);
        return "redirect:/home?info=deleted";
    }



}
