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
    public String getPromotionCodePage(Model model, @ModelAttribute("generatedPromotionCode") String generatedPromotionCode, @RequestParam(required = false) String info){
        model.addAttribute("promotioncode", new PromotionCode());
        model.addAttribute("info", info);
        return "generate-promotioncode";
    }

    @PostMapping("/generate/promotioncode")
    public String generatePromotionCode(RedirectAttributes redirectAttributes, @RequestParam BigDecimal discount, @RequestParam int activeDays, @RequestParam boolean isMultipleUse){
        PromotionCode promotionCode = promotionCodeService.createPromotionCode(discount, activeDays, isMultipleUse);
        redirectAttributes.addFlashAttribute("generatedPromotionCode", promotionCode.getPromotionCode());
        return "redirect:/promotioncode?info=generated";
    }



}
