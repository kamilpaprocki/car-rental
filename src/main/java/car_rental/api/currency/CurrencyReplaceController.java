package car_rental.api.currency;

import car_rental.api.utils.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(CurrencyReplaceController.class);

    public CurrencyReplaceController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping("")
    public String loadPage(){
        return "rents/rent-summary";
    }

    @RequestMapping("currency-pln")
    public String getCurrencyPLN(@RequestParam("cost") String rentalCostJSON){

        BigDecimal rentalCost = new JSONParser().parseJSONtoBigDecimal(rentalCostJSON);
        logger.info("Return rental cost in PLN");
        return "rents/currency :: currency-pln(rentalCost="+rentalCost+")";
    }

    @RequestMapping("currency-usd")
    public String getCurrencyUSD(@RequestParam("cost") String rentalCostJSON){

        BigDecimal rentalCost = new JSONParser().parseJSONtoBigDecimal(rentalCostJSON);
        rentalCost = currencyService.convertAmountFromPLN(USD, rentalCost);
        logger.info("Return rental cost in USD");
        return "rents/currency :: currency-usd(rentalCost="+rentalCost+")";
    }

    @RequestMapping("currency-eur")
    public String getCurrencyEUR(@RequestParam("cost") String rentalCostJSON){

        BigDecimal rentalCost = new JSONParser().parseJSONtoBigDecimal(rentalCostJSON);
        rentalCost = currencyService.convertAmountFromPLN(EUR, rentalCost);
        logger.info("Return rental cost in EUR");
        return "rents/currency :: currency-eur(rentalCost="+rentalCost+")";
    }

}
