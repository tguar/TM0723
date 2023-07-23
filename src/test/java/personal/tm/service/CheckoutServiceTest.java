package personal.tm.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import personal.tm.model.ChargeInformationDTO;
import personal.tm.model.RentalAgreementDTO;
import personal.tm.model.tools.Tool;
import personal.tm.model.tools.ToolFactory;
import personal.tm.model.tools.Chainsaw;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Responsible for testing {@link personal.tm.service.CheckoutService}
 */
public class CheckoutServiceTest {

    ToolFactory mockToolFactory = new ToolFactory(new MockToolLookUpService());
    CheckoutService checkoutService = new CheckoutService(mockToolFactory, new MockPricingService());

    @Test
    public void whenCheckingOut_ifRentalDayCountMethodParameterNull_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", null, "", "");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Rental day count must be for one or more days."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_ifRentalDayCountMethodIsInvalidValue_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", "-2", "", "");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Rental day count must be for one or more days."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_ifRentalDayCountMethodIsInvalidCharacter_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", "A", "", "");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Rental day count and discount percent must be whole numbers. Value inputted was 'A'."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_ifDiscountPercentMethodIsInvalidCharacter_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", "3", "A", "");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Rental day count and discount percent must be whole numbers. Value inputted was 'A'."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_ifDiscountPercentMethodParameterNull_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", "3", null, "");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Discount percent must a whole number be between 0 and 100."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_ifDiscountPercentMethodIsInvalidValue_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", "3", "101", "");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Discount percent must a whole number be between 0 and 100."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_ifDiscountPercentMethodIsAnotherInvalidValue_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", "3", "-101", "");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Discount percent must a whole number be between 0 and 100."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_ifDiscountPercentMethodIsAnotherInvalidValue2_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", "3", "1.01", "");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Rental day count and discount percent must be whole numbers. Value inputted was '1.01'."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_ifcheckoutDateMethodIsNull_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", "3", "0", null);
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Checkout date must be in 'mm/dd/yy' format."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_ifcheckoutDateMethodIsInvalid_checkoutWithRawInputShouldThrowIllegalArgumentException() {
        //Arrange
        //Act
        IllegalArgumentException result = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.checkoutWithRawInput("", "3", "0", "13/2/93");
        });

        //Assert
        Assertions.assertTrue(result.getMessage().contains("Checkout date must be in 'mm/dd/yy' format."), "Correct exception message returned");
    }

    @Test
    public void whenCheckingOut_aHappyPath_shouldReturnCorrectValues() {
        //Arrange
        String toolCode = "TESTCHNS";
        Long rentalDayCount = 2l;
        Integer discountPercent = 10;
        LocalDate checkoutDate = LocalDate.of(2023, 01, 2);

        //Act
        RentalAgreementDTO result = checkoutService.checkoutWithValidatedInput(toolCode, rentalDayCount, discountPercent, checkoutDate);

        //Assert
        Assertions.assertAll("All values should be correct",
                () -> Assertions.assertEquals("TESTCHNS", result.getToolCode(), "Tool Code should match"),
                () -> Assertions.assertEquals("Chainsaw", result.getToolType(), "Tool Type should match"),
                () -> Assertions.assertEquals("TestBrand", result.getToolBrand(), "Tool brand should match"),
                () -> Assertions.assertEquals(rentalDayCount, result.getRentalDays(), "Rental days should match"),
                () -> Assertions.assertEquals(checkoutDate, result.getCheckoutDate(), "Checkout date should match"),
                () -> Assertions.assertEquals(LocalDate.of(2023, 01, 3), result.getDueDate(), "Due date should match"),
                () -> Assertions.assertEquals(new BigDecimal("1.49"), result.getDailyRentalCharge(), "Daily rental charge should match"),
                () -> Assertions.assertEquals(new BigDecimal("5.00"), result.getPreDiscountCharge(), "Pre discount charge should match"),
                () -> Assertions.assertEquals(10, result.getDiscountPercent(), "Discount percent should match"),
                () -> Assertions.assertEquals(new BigDecimal("0.50"), result.getDiscountAmount(), "Discount amount should match"),
                () -> Assertions.assertEquals(new BigDecimal("4.50"), result.getFinalCharge(), "Final charge should match"));
    }
}

    class MockToolLookUpService implements ToolLookupInterface {
        public Tool getNonPricingToolInformationByToolCode(String toolCode) {
            return new Chainsaw("TESTCHNS", "Chainsaw", "TestBrand");
        }
    }

    class MockPricingService implements PricingInterface {

        public ChargeInformationDTO getChargeInformation(LocalDate checkoutDate, Long rentalDayCount, Tool tool) {
          ChargeInformationDTO result = new ChargeInformationDTO();
          result.setRoundedPreDiscountCharge(new BigDecimal("5.00"));
          result.setChargeDays(2);
          return result;
        }
    }
