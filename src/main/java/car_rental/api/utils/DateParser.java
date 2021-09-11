package car_rental.api.utils;

import car_rental.api.exceptions.WrongDataFormatException;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class DateParser {

    public Date parseDate(String dateFrom) {

        boolean correctDateFormat;
        List<String> patterns = Arrays.asList("dd.MM.yyyy", "yyyy-MM-dd");
        DateFormat dateFormat;
        Date dateTo;

        for(String pattern : patterns){
            dateFormat = new SimpleDateFormat(pattern);

            try{
                return Date.valueOf(dateFormat.parse(dateFrom).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }catch(ParseException e){

            }
        }


        throw new WrongDataFormatException("Wrong data format.");

    }

    }
