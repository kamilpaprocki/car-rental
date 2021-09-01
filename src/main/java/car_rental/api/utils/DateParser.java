package car_rental.api.utils;

import car_rental.api.exceptions.WrongDataFormatException;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;

public class DateParser {

    public Date parseDate(String dateFrom) {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date dateTo;
        try{
            dateTo = Date.valueOf(dateFormat.parse(dateFrom).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }catch(ParseException e){
            throw new WrongDataFormatException("Wrong data format.");
        }
        return dateTo;

    }

    }
