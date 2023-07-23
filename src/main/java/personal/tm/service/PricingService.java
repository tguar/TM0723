package personal.tm.service;

import personal.tm.model.ChargeInformationDTO;
import personal.tm.model.tools.Tool;
import personal.tm.utils.Constants;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.util.HashMap;

/**
 * Responsible for returning charge information based on time and {@link personal.tm.model.tools.Tool}
 */
public class PricingService implements PricingInterface{

    /**
     * Returns charge information based on a start date, length of time, and {@link personal.tm.model.tools.Tool}.
     * @param checkoutDate the date the tool is checked out
     * @param rentalDayCount the number of days the tool is to be checkout out
     * @param tool the specific {@link personal.tm.model.tools.Tool}
     * @return
     */
    public ChargeInformationDTO getChargeInformation(LocalDate checkoutDate, Long rentalDayCount, Tool tool){
        ChargeInformationDTO chargeInformationDTO = new ChargeInformationDTO(); //Create new pricing object to eventually return

        //Calculate each type of days value and aggregate it into a set
        HashMap<String, Integer> typeOfDayAndDayTotalAggregatedSet = aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Use tool's charging information to calculate days to charge
        Integer weekdaysToCharge = typeOfDayAndDayTotalAggregatedSet.get(Constants.WEEKDAYS) * (tool.getWeekdayCharge() ? 1 : 0) ;
        Integer weekendDaysToCharge = typeOfDayAndDayTotalAggregatedSet.get(Constants.WEEKEND_DAYS) * (tool.getWeekendCharge() ? 1 : 0);
        Integer holidaysToCharge = typeOfDayAndDayTotalAggregatedSet.get(Constants.HOLIDAYS_OBSERVED) * (tool.getHolidayCharge() ? 1 : 0);

        //Sum tool's total charge days
        chargeInformationDTO.setChargeDays(weekdaysToCharge + weekendDaysToCharge + holidaysToCharge);

        //Multiply daily charge x number of charge days
        chargeInformationDTO.setRoundedPreDiscountCharge(tool.getDailyCharge().multiply(new BigDecimal(chargeInformationDTO.getChargeDays())));

        return chargeInformationDTO;
    }

    /**
     * Helper method to aggregate the count of days for day type (WEEKDAY, WEEKEND, OBSERVED HOLIDAY) for a tool given a time span.
     * WEEKDAYS are defined as: MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY.
     * WEEKENDS are defined as: SATURDAY, SUNDAY.
     * OBSERVED_HOLIDAYS are defined as: LABOR DAY - the First Monday in September, AMERICAN INDEPENDENCE DAY - July Fourth. <br>
     *
     * OBSERVED_HOLIDAYS are only counted when the tool's checkout time spans the holiday itself <em>and</em> the holiday's observed day.
     * For instance, if a tool is checked out beginning on Saturday, July 4th 2020 and is returned Monday, July 6th 2020,
     * then no holiday would apply since the checkout time does not span both the holiday and the observed holiday. Instead, it would be treated
     * like a normal weekend and weekday.<br>
     *
     * @param checkoutDate date the tool is checked out
     * @param rentalDayCount the number of days the tool is rented for
     * @return {@link HashMap<String, Integer>} set of day types and their respective counts for a tool over a time span
     */
    protected HashMap<String, Integer> aggregateRentalDaysByType(LocalDate checkoutDate, Long rentalDayCount) {
        HashMap<String, Integer> typeOfDayAndDayTotalSet = new HashMap<>(3); //Create new set to store day types and their respective counts
        Integer numberOfWeekdays = 0, numberOfWeekendDays = 0, numberOfHolidays = 0; //Initialize day counts to zero

        LocalDate dateToProcess = checkoutDate; //The checkout date
        LocalDate checkinDate = checkoutDate.plusDays(rentalDayCount - 1); //The checkin date

        //Iterate from checkout date to checkin date (inclusive)
        while (!dateToProcess.isAfter(checkinDate)) {
            switch(dateToProcess.getDayOfWeek()){
                case MONDAY:
                    //Only increment holiday and noop weekday since Labor Day is always observed on a weekday
                    if(isLaborDay(dateToProcess)){
                        numberOfHolidays++;
                        break;
                    }
                case TUESDAY:
                case WEDNESDAY:
                case THURSDAY:
                case FRIDAY:
                    if(isAmericanIndependenceDay(dateToProcess)) {
                        numberOfHolidays++;
                    }
                    //For all non-holiday weekdays, increment weekdays
                    else {
                        numberOfWeekdays++;
                    }
                    break;
                case SATURDAY:
                    //If July 4th and observed holiday spans rental time, increment holidays and decrement weekday count, then always increment weekend
                    if(isAmericanIndependenceDay(dateToProcess) && dateToProcess.isAfter(checkoutDate)){
                        numberOfHolidays++;
                        numberOfWeekdays--;
                    }
                    numberOfWeekendDays++;
                    break;
                case SUNDAY:
                    //If July 4th and observed holiday spans rental time, increment holidays and decrement weekday count, then always increment weekend
                    if(isAmericanIndependenceDay(dateToProcess) && dateToProcess.isBefore(checkinDate))
                    {
                        numberOfHolidays++;
                        numberOfWeekdays--;
                    }
                    numberOfWeekendDays++;
                    break;
            }
            //Always increment dateToProcess to next day
            dateToProcess = dateToProcess.plusDays(1L);
        }

        //Days should never be negative numbers. If they happen to be, set to zero
        typeOfDayAndDayTotalSet.put(Constants.WEEKDAYS, Math.max(0, numberOfWeekdays));
        typeOfDayAndDayTotalSet.put(Constants.WEEKEND_DAYS, Math.max(0, numberOfWeekendDays));
        typeOfDayAndDayTotalSet.put(Constants.HOLIDAYS_OBSERVED, Math.max(0, numberOfHolidays));

        return typeOfDayAndDayTotalSet;
    }

    /**
     * Determines whether a {@link LocalDate} is Labor Day by evaluating whether if it is the first Monday in September
     * @param date
     * @return {@link Boolean}
     */
    protected Boolean isLaborDay(LocalDate date){
        return date.getMonth() == Month.SEPTEMBER
                && date.getDayOfWeek() == DayOfWeek.MONDAY
                && date.minusDays(7).getMonth() == Month.AUGUST;
    }

    /**
     * Determines whether a {@link LocalDate} is American Independence Day by evaluating if it is equal to July 4
     * @param date
     * @return {@link Boolean}
     */
    protected Boolean isAmericanIndependenceDay(LocalDate date) {
        return MonthDay.from(date).equals(MonthDay.parse(Constants.INDEPENDENCE_DAY_FORMATTED_STRING));
    }
}
