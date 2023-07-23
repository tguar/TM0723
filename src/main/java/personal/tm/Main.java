package personal.tm;

import personal.tm.model.RentalAgreementDTO;
import personal.tm.model.tools.ToolFactory;
import personal.tm.service.*;

/**
 * Generates and pretty prints a {@link personal.tm.model.RentalAgreementDTO}
 * @param args[0] the toolCode
 * @param args[1] the rental day count
 * @param args[2] the discount percent as a whole number
 * @param args[3] the checkout date in M/d/uu format
 */
public class Main {
    public static void main(String[] args) {
        String toolCodeInput = args[0];
        String rentalDayCountInput = args[1];
        String discountPercentInput = args[2];
        String checkoutDateInput = args[3];

        // Initialize services and generate rental agreement from input
        try {
            ToolLookupInterface itemLookupService = new ToolLookupService();
            ToolFactory toolFactory = new ToolFactory(itemLookupService);
            PricingInterface pricingService = new PricingService();
            CheckoutInterface checkoutService = new CheckoutService(toolFactory, pricingService);
            RentalAgreementDTO rentalAgreementDTO = checkoutService.checkoutWithRawInput(toolCodeInput, rentalDayCountInput, discountPercentInput, checkoutDateInput);
            rentalAgreementDTO.printRentalAgreement();
        }
        // Writes friendly message to user
        catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }
}