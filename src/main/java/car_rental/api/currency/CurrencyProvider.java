package car_rental.api.currency;

import car_rental.api.exceptions.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Component
public class CurrencyProvider {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyProvider.class);

    private final String apiURL = "https://api.nbp.pl/api/exchangerates/rates/a/";

    public float getExchangeRate(Currency currency){
        float exchangeRate;
        try {
            logger.info("Connect to NPB api.");
            URL url = new URL(apiURL +currency.getName());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String content = reader.readLine();
            reader.close();
            int begin = content.indexOf("\"mid\":")+6;
            int end = begin + 6;
            exchangeRate = Float.parseFloat(content.substring(begin, end));
        } catch (IOException e) {
            logger.error("Cannot get current exchange rate. Cannot parse String to float");
            throw new ParseException("Cannot get current exchange rate. Cannot parse String to float");
        }
        logger.info("Get exchange rate from NPB api.");
    return exchangeRate;
    }

}
