package car_rental.api.currency;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static car_rental.api.currency.Currency.PLN;

@Service
public class CurrencyService {

    private final CurrencyProvider currencyProvider;

    public CurrencyService(CurrencyProvider currencyProvider) {
        this.currencyProvider = currencyProvider;
    }

    public BigDecimal convertAmountFromPLN(Currency currency, BigDecimal amountPLN){
        if (currency == PLN) {
            return amountPLN.setScale(2, RoundingMode.CEILING);
        }
        return amountPLN.divide(BigDecimal.valueOf(currencyProvider.getExchangeRate(currency)), 2, RoundingMode.CEILING);
    }



}
