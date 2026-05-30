package fa.training.common;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static fa.training.common.Constants.DATE_FORMATTER;

public class Validator {
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isValidPositiveNumber(double number) {
        return number > 0;
    }

    public static boolean isValidDate(String date) {
        try{
            LocalDate.parse(date,DATE_FORMATTER);
        }catch (DateTimeParseException e){
            return false;
        }
        return true;
    }
}
