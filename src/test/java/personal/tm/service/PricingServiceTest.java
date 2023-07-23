package personal.tm.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import personal.tm.model.ChargeInformationDTO;
import personal.tm.model.tools.Chainsaw;
import personal.tm.model.tools.Jackhammer;
import personal.tm.model.tools.Ladder;
import personal.tm.utils.Constants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Responsible for testing {@link personal.tm.service.PricingService}
 */
public class PricingServiceTest {

    PricingService pricingService = new PricingService();

    @Test
    public void whenRentingALadder_getChargeInformation_ShouldReturnCorrectChargeInformation(){
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2023, 7, 17);
        Long rentalDayCount = 7L;
        Ladder ladder = new Ladder("LCODE", "LADDER", "TEST");

        //Act
        ChargeInformationDTO result = pricingService.getChargeInformation(checkoutDate, rentalDayCount, ladder);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(7, result.chargeDays, "Five weekdays, two weekend days, zero holidays"),
                () -> assertEquals(new BigDecimal("13.93"), result.roundedPreDiscountCharge, "$1.99(5wd) + $1.99(2we) + $0.00(0hol) = $13.93"));
    }

    @Test
    public void whenRentingAChainsaw_getChargeInformation_ShouldReturnCorrectChargeInformation(){
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2023, 1, 1);
        Long rentalDayCount = 2L;
        Chainsaw chainsaw = new Chainsaw("LCODE", "CHAINSAW", "TEST");

        //Act
        ChargeInformationDTO result = pricingService.getChargeInformation(checkoutDate, rentalDayCount, chainsaw);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(1, result.chargeDays, "Five weekdays, two weekend days, zero holidays"),
                () -> assertEquals(new BigDecimal("1.49"), result.roundedPreDiscountCharge, "$1.49(1wd) + $0.00(1we) + $0.00(0hol) = $1.49"));
    }

    @Test
    public void whenRentingAChainsawOverAnObservedHoliday_getChargeInformation_ShouldReturnCorrectChargeInformation(){
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2023, 9, 1);
        Long rentalDayCount = 7L;
        Chainsaw chainsaw = new Chainsaw("CCODE", "CHAINSAW", "TEST");

        //Act
        ChargeInformationDTO result = pricingService.getChargeInformation(checkoutDate, rentalDayCount, chainsaw);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(5, result.chargeDays, "Four weekdays, two weekend days, one holiday observed"),
                () -> assertEquals(new BigDecimal("7.45"), result.roundedPreDiscountCharge, "$1.49(4wd) + $0.00(2we) + $1.49(1hol) = $7.45"));
    }

    @Test
    public void whenRentingAJackhammerOverAnObservedHoliday_getChargeInformation_ShouldReturnCorrectChargeInformation(){
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2020, 7, 4);
        Long rentalDayCount = 7L;
        Jackhammer jackhammer = new Jackhammer("JCODE", "JACKHAMMER", "TEST");

        //Act
        ChargeInformationDTO result = pricingService.getChargeInformation(checkoutDate, rentalDayCount, jackhammer);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(5, result.chargeDays, "Five weekdays, two weekend days, one holiday"),
                () -> assertEquals(new BigDecimal("14.95"), result.roundedPreDiscountCharge, "2.99(5wd) + $0.00(3we) + $0.00(1hol) = $14.95"));
    }


    @Test
    public void whenRenting_allWeekdays_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2023, 7, 17);
        Long rentalDayCount = 5L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(5, result.get(Constants.WEEKDAYS), "Five weekdays"),
                () -> assertEquals(0, result.get(Constants.WEEKEND_DAYS), "Zero weekends"),
                () -> assertEquals(0, result.get(Constants.HOLIDAYS_OBSERVED), "Zero holidays"));
    }

    @Test
    public void whenRenting_allWeekends_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2023, 7, 15);
        Long rentalDayCount = 2L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(0, result.get(Constants.WEEKDAYS), "Zero weekdays"),
                () -> assertEquals(2, result.get(Constants.WEEKEND_DAYS), "Two weekend days"),
                () -> assertEquals(0, result.get(Constants.HOLIDAYS_OBSERVED), "Zero holidays"));
    }

    @Test
    public void whenRenting_combinationOfWeekdaysAndWeekends_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2023, 7, 15);
        Long rentalDayCount = 14L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(10, result.get(Constants.WEEKDAYS), "Ten weekdays"),
                () -> assertEquals(4, result.get(Constants.WEEKEND_DAYS), "Four weekend days"),
                () -> assertEquals(0, result.get(Constants.HOLIDAYS_OBSERVED), "Zero holidays"));
    }

    @Test
    public void whenRenting_combinationOfWeekendsAndHolidays_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2023, 9, 2);
        Long rentalDayCount = 3L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(0, result.get(Constants.WEEKDAYS), "Zero weekdays"),
                () -> assertEquals(2, result.get(Constants.WEEKEND_DAYS), "Two weekend days"),
                () -> assertEquals(1, result.get(Constants.HOLIDAYS_OBSERVED), "One holiday observed"));
    }

    @Test
    public void whenRenting_combinationOfWeekDayAndIndependenceDayOnSaturday_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        Long rentalDayCount = 5L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(2, result.get(Constants.WEEKDAYS), "Two weekdays"),
                () -> assertEquals(2, result.get(Constants.WEEKEND_DAYS), "Two weekend days"),
                () -> assertEquals(1, result.get(Constants.HOLIDAYS_OBSERVED), "One holiday observed"));
    }

    @Test
    public void whenRenting_combinationOfWeekdayAndIndependenceDayOnSaturdayStartingBeforeSaturday_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        Long rentalDayCount = 3L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(1, result.get(Constants.WEEKDAYS), "One weekday"),
                () -> assertEquals(1, result.get(Constants.WEEKEND_DAYS), "One weekend day"),
                () -> assertEquals(1, result.get(Constants.HOLIDAYS_OBSERVED), "One holiday observed"));
    }

    @Test
    public void whenRenting_combinationOfWeekDayAndIndependenceDayOnSaturdayStartingSaturday_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2020, 7, 4);
        Long rentalDayCount = 3L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(1, result.get(Constants.WEEKDAYS), "One weekday"),
                () -> assertEquals(2, result.get(Constants.WEEKEND_DAYS), "Two weekend days"),
                () -> assertEquals(0, result.get(Constants.HOLIDAYS_OBSERVED), "Zero holidays since tool was not checked out before holiday was observed"));
    }

    @Test
    public void whenRenting_onIndependenceDayOnSaturdayStartingSaturday_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2020, 7, 4);
        Long rentalDayCount = 1L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(0, result.get(Constants.WEEKDAYS), "Zero weekdays"),
                () -> assertEquals(1, result.get(Constants.WEEKEND_DAYS), "One weekend day"),
                () -> assertEquals(0, result.get(Constants.HOLIDAYS_OBSERVED), "No holidays since rental was checked in before observed holiday"));
    }

    @Test
    public void whenRenting_combinationOfWeekDayAndIndependenceDayOnSunday_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2010, 7, 1);
        Long rentalDayCount = 6L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(3, result.get(Constants.WEEKDAYS), "Three weekdays"),
                () -> assertEquals(2, result.get(Constants.WEEKEND_DAYS), "Two weekend days"),
                () -> assertEquals(1, result.get(Constants.HOLIDAYS_OBSERVED), "One holiday observed"));
    }

    @Test
    public void whenRenting_multipleHolidaySpan_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2023, 7, 1);
        Long rentalDayCount = 70L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(48, result.get(Constants.WEEKDAYS), "Forty-eight weekdays"),
                () -> assertEquals(20, result.get(Constants.WEEKEND_DAYS), "Twenty weekend days"),
                () -> assertEquals(2, result.get(Constants.HOLIDAYS_OBSERVED), "Two holidays observed"));
    }

    @Test
    public void whenRenting_sameHolidaySpan_aggregateRentalDaysByTypeShouldReturnCorrectSetValues() {
        //Arrange
        LocalDate checkoutDate = LocalDate.of(2023, 7, 1);
        Long rentalDayCount = 390L;

        //Act
        HashMap<String, Integer> result = pricingService.aggregateRentalDaysByType(checkoutDate, rentalDayCount);

        //Assert
        Assertions.assertAll("Each value in set should be correct",
                () -> assertEquals(275, result.get(Constants.WEEKDAYS), "Two-hundred and seventy-five weekdays"),
                () -> assertEquals(112, result.get(Constants.WEEKEND_DAYS), "One hundred and twelve weekend days"),
                () -> assertEquals(3, result.get(Constants.HOLIDAYS_OBSERVED), "Three holidays observed"));
    }



    @Test
    public void whenDate_isLaborDay_isLaborDayShouldReturnTrue() {
        //Arrange
        LocalDate previousLaborDay = LocalDate.of(2020, 9, 7);
        LocalDate thisYearLaborDay = LocalDate.of(2023, 9, 4);
        LocalDate futureLaborDay = LocalDate.of(2024, 9, 2);

        //Act
        Boolean previousLaborDayResult = pricingService.isLaborDay(previousLaborDay);
        Boolean thisYearLaborDayResult = pricingService.isLaborDay(thisYearLaborDay);
        Boolean futureLaborDayResult = pricingService.isLaborDay(futureLaborDay);

        //Assert
        Assertions.assertAll("Each Labor Days should return true",
                () -> assertTrue(previousLaborDayResult),
                () -> assertTrue(thisYearLaborDayResult),
                () -> assertTrue(futureLaborDayResult));
    }

    @Test
    public void whenDate_isNotLaborDay_isLaborDayShouldReturnFalse() {
        //Arrange
        LocalDate nonLaborDayInSeptemberBeforeLaborDay = LocalDate.of(2020, 9, 4);
        LocalDate nonLaborDayInSeptemberAfterLaborDay = LocalDate.of(2023, 9, 11);
        LocalDate nonLaborDayInAugustBeforeLaborDay = LocalDate.of(2024, 8, 30);

        //Act
        Boolean previousLaborDayResult = pricingService.isLaborDay(nonLaborDayInSeptemberBeforeLaborDay);
        Boolean thisYearLaborDayResult = pricingService.isLaborDay(nonLaborDayInSeptemberAfterLaborDay);
        Boolean futureLaborDayResult = pricingService.isLaborDay(nonLaborDayInAugustBeforeLaborDay);

        //Assert
        Assertions.assertAll("Non Labor Days should return false",
                () -> assertFalse(previousLaborDayResult),
                () -> assertFalse(thisYearLaborDayResult),
                () -> assertFalse(futureLaborDayResult));
    }

    @Test
    public void whenDate_isIndependenceDay_isIndependenceDayShouldReturnTrue() {
        //Arrange
        LocalDate independenceDay = LocalDate.of(2023, 7, 4);

        //Act
        Boolean independenceDayResult = pricingService.isAmericanIndependenceDay(independenceDay);

        //Assert
        Assertions.assertTrue( independenceDayResult, "Independence Day returns true");
    }

    @Test
    public void whenDate_isNotIndependenceDay_isIndependenceDayShouldReturnFalse() {
        //Arrange
        LocalDate notIndependenceDay = LocalDate.of(2023, 7, 3);
        LocalDate alsoNotIndependenceDay = LocalDate.of(2023, 7, 5);

        //Act
        Boolean notIndependenceDayResult = pricingService.isAmericanIndependenceDay(notIndependenceDay);
        Boolean alsoNotIndependenceDayResult = pricingService.isAmericanIndependenceDay(alsoNotIndependenceDay);

        //Assert
        Assertions.assertAll("Non Independence Days should return false",
                () -> assertFalse(notIndependenceDayResult),
                () -> assertFalse(alsoNotIndependenceDayResult));
    }

}
