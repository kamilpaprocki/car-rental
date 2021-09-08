package car_rental.api.utils;

import car_rental.api.exceptions.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class JSONParser {

    public BigDecimal parseJSONtoBigDecimal(String valueJSON){

        ObjectMapper mapper = new ObjectMapper();
        BigDecimal valueBigDecimal;
        try {
            valueBigDecimal = mapper.readValue(valueJSON, BigDecimal.class).setScale(2, RoundingMode.CEILING);
        } catch (IOException e) {
            throw new ParseException("Cannot parse JSON to Big Decimal");
        }
        return valueBigDecimal;

    }



}
