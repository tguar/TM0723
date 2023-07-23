package personal.tm.service;

import personal.tm.model.RentalAgreementDTO;

import java.time.LocalDate;

/**
 * Contract to take raw input from a user and transform it into a rental agreement
 */
public interface CheckoutInterface {

    /**
     * Responsible for creating a rental agreement from unsanitized input
     * @param toolCode the tool code
     * @param rentalDayCount the number of rental days
     * @param discountPercent the percent discount
     * @param checkoutDate the date the tool is checked out
     * @return {@link personal.tm.model.RentalAgreementDTO}
     * @throws IllegalArgumentException
     */
    RentalAgreementDTO checkoutWithRawInput(String toolCode, String rentalDayCount, String discountPercent, String checkoutDate) throws IllegalArgumentException;
}
