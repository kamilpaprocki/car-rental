package car_rental.api.currency;

import car_rental.api.utils.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import static car_rental.api.currency.Currency.EUR;
import static car_rental.api.currency.Currency.USD;

@Controller
@RequestMapping("currency")
public class CurrencyReplaceController {

    private final CurrencyService currencyService;

    public CurrencyReplaceController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping("")
    public String loadPage(){
        return "rent-summary";
    }

    @RequestMapping("currency-pln")
    public String getCurrencyPLN(@RequestParam("cost") String rentalCostJSON){

        BigDecimal rentalCost = new JSONParser().parseJSONtoBigDecimal(rentalCostJSON);

        return "currency :: currency-pln(rentalCost="+rentalCost+")";
    }

    @RequestMapping("currency-usd")
    public String getCurrencyUSD(@RequestParam("cost") String rentalCostJSON){

        BigDecimal rentalCost = new JSONParser().parseJSONtoBigDecimal(rentalCostJSON);
        rentalCost = currencyService.convertAmountFromPLN(USD, rentalCost);

        return "currency :: currency-usd(rentalCost="+rentalCost+")";
    }

    @RequestMapping("currency-eur")
    public String getCurrencyEUR(@RequestParam("cost") String rentalCostJSON){

        BigDecimal rentalCost = new JSONParser().parseJSONtoBigDecimal(rentalCostJSON);
        rentalCost = currencyService.convertAmountFromPLN(EUR, rentalCost);

        return "currency :: currency-eur(rentalCost="+rentalCost+")";
    }

}
