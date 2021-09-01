package car_rental.api.utils;

import car_rental.api.exceptions.WrongArgumentException;
import car_rental.api.rents.Currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CurrencyConverter {

    private final String api = "https://api.nbp.pl/api/exchangerates/rates/a/";

    public float getExchangeRate(Currency currency){
        float exchangeRate;
        try {
            URL url = new URL(api+currency.getName());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String content = reader.readLine();
            reader.close();
            int begin = content.indexOf("\"mid\":")+6;
            int end = begin + 6;
            exchangeRate = Float.parseFloat(content.substring(begin, end));
        } catch (IOException e) {
            throw new WrongArgumentException("Can not get current exchange rate.");
        }
    return exchangeRate;
    }


}
