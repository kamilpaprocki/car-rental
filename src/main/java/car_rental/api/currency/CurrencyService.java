package car_rental.api.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static car_rental.api.currency.Currency.PLN;

@Service
public class CurrencyService {

    private final CurrencyProvider currencyProvider;

    private final static Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    public CurrencyService(CurrencyProvider currencyProvider) {
        this.currencyProvider = currencyProvider;
    }

    public BigDecimal convertAmountFromPLN(Currency currency, BigDecimal amountPLN){
        if (currency == PLN) {
            logger.info("Currency to convert and amount the same: PLN.");
            return amountPLN.setScale(2, RoundingMode.CEILING);
        }
        logger.info("Convert amount in PLN to {}.", currency.getName());
        return amountPLN.divide(BigDecimal.valueOf(currencyProvider.getExchangeRate(currency)), 2, RoundingMode.CEILING);
    }



}
