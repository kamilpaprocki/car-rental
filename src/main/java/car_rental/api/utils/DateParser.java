package car_rental.api.utils;

import car_rental.api.exceptions.WrongDataFormatException;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DateParser {

    public Date parseStringToDate(String dateFrom) {

        List<String> patterns = Arrays.asList("dd.MM.yyyy", "yyyy-MM-dd");
        DateFormat dateFormat;

        for(String pattern : patterns){
            dateFormat = new SimpleDateFormat(pattern);
            try{
                return Date.valueOf(dateFormat.parse(dateFrom).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }catch(ParseException e){

            }
        }


        throw new WrongDataFormatException("Wrong data format.");

    }

    public String parseDateToString(Date date){
        if (date == null){
            return null;
        }
        LocalDate localDate = date.toLocalDate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localDate.format(dateTimeFormatter);
    }
    }
