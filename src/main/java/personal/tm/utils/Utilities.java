package personal.tm.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static personal.tm.utils.Constants.PRINTED_DATE_FORMAT;

/**
 * Utility class
 */
public class Utilities {
    /**
     * Evaluates if a {@link String} represents a LocalDate in the format 'M/d/uu'
     * @param dateStr the date to check
     * @return {@link Boolean}
     */
    public static boolean isValidDateFromString(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(PRINTED_DATE_FORMAT));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
