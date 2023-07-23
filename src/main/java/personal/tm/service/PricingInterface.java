package personal.tm.service;

import personal.tm.model.ChargeInformationDTO;
import personal.tm.model.tools.Tool;

import java.time.LocalDate;

/**
 * Contract to return charge information based on time and tool
 */
public interface PricingInterface {

    /**
     * Calculates the charge information given a checkout date, number of days, and tool
     * @param checkoutDate the date the tool is checked out
     * @param rentalDayCount the number of days the tool is to be checkout out
     * @param tool the specific {@link personal.tm.model.tools.Tool}
     * @return {@link ChargeInformationDTO}
     */
    ChargeInformationDTO getChargeInformation(LocalDate checkoutDate, Long rentalDayCount, Tool tool);
}
