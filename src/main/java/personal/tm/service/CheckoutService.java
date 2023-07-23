package personal.tm.service;

import personal.tm.model.ChargeInformationDTO;
import personal.tm.model.RentalAgreementDTO;
import personal.tm.model.tools.Tool;
import personal.tm.model.tools.ToolFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static personal.tm.utils.Constants.PRINTED_DATE_FORMAT;
import static personal.tm.utils.Utilities.isValidDateFromString;

/**
 * Responsible for calculating rental agreement information based on input
 */
public class CheckoutService implements CheckoutInterface {
    private final ToolFactory toolFactory;
    private final PricingInterface pricingService;
    public CheckoutService(ToolFactory toolFactory, PricingInterface pricingService){
        this.toolFactory = toolFactory;
        this.pricingService = pricingService;
    }

    /**
     * This is a public facade that first sanitizes user input before continuing to generate rental agreement information
     * @param toolCode the tool code
     * @param rentalDayCount the number of rental days
     * @param discountPercent the percent discount
     * @param checkoutDate the date the tool is checked out
     * @return {@link personal.tm.model.RentalAgreementDTO}
     * @throws IllegalArgumentException if one or more agrguments is illegal
     */
    public RentalAgreementDTO checkoutWithRawInput(String toolCode, String rentalDayCount, String discountPercent, String checkoutDate) throws IllegalArgumentException{
        try {
            if (null == rentalDayCount || Long.parseLong(rentalDayCount) < 1) {
                throw new IllegalArgumentException("Rental day count must be for one or more days.");
            }
            if (null == discountPercent || Integer.parseInt(discountPercent) < 0 || Integer.parseInt(discountPercent) > 100) {
                throw new IllegalArgumentException("Discount percent must a whole number be between 0 and 100.");
            }
            if (null == checkoutDate || !isValidDateFromString(checkoutDate)){
                throw new IllegalArgumentException("Checkout date must be in 'mm/dd/yy' format.");
            }
        }
        catch (NumberFormatException ex){
            throw new IllegalArgumentException("Rental day count and discount percent must be whole numbers. Value inputted was '" + ex.getLocalizedMessage().substring(ex.getLocalizedMessage().indexOf('"') + 1, ex.getLocalizedMessage().length() - 1) + "'.");
        }
        return checkoutWithValidatedInput(toolCode, Long.parseLong(rentalDayCount), Integer.parseInt(discountPercent), LocalDate.parse(checkoutDate, DateTimeFormatter.ofPattern(PRINTED_DATE_FORMAT)));
    }

    /**
     * Helper checkout method that assumes input is sanitized and valid. It is responsible for generating a rental agreement object
     * @param toolCode the tool code
     * @param rentalDayCount the number of rental days
     * @param discountPercent the percent discount
     * @param checkoutDate the date the tool is checked out
     * @return {@link personal.tm.model.RentalAgreementDTO}
     */
    protected RentalAgreementDTO checkoutWithValidatedInput(String toolCode, Long rentalDayCount, Integer discountPercent, LocalDate checkoutDate){
        Tool tool = toolFactory.generateToolByToolCode(toolCode);
        ChargeInformationDTO chargeInformationDTO = pricingService.getChargeInformation(checkoutDate, rentalDayCount, tool);
        LocalDate dueDate = checkoutDate.plusDays(rentalDayCount-1);

        RentalAgreementDTO rentalAgreementDTO = new RentalAgreementDTO();
            rentalAgreementDTO.setToolCode(tool.getToolCode());
            rentalAgreementDTO.setToolType(tool.getToolType());
            rentalAgreementDTO.setToolBrand(tool.getBrand());
            rentalAgreementDTO.setRentalDays(rentalDayCount);
            rentalAgreementDTO.setCheckoutDate(checkoutDate);
            rentalAgreementDTO.setDueDate(dueDate);
            rentalAgreementDTO.setDailyRentalCharge(tool.getDailyCharge());
            rentalAgreementDTO.setChargeDays(chargeInformationDTO.getChargeDays());
            rentalAgreementDTO.setPreDiscountCharge(chargeInformationDTO.getRoundedPreDiscountCharge());
            rentalAgreementDTO.setDiscountPercent(discountPercent);
            rentalAgreementDTO.setDiscountAmount(chargeInformationDTO.getRoundedPreDiscountCharge().multiply(BigDecimal.valueOf(discountPercent)).divide(new BigDecimal("100"), RoundingMode.HALF_UP));
            rentalAgreementDTO.setFinalCharge(chargeInformationDTO.getRoundedPreDiscountCharge().subtract(rentalAgreementDTO.getDiscountAmount()));

        return rentalAgreementDTO;
    }
}
