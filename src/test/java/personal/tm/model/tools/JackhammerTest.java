package personal.tm.model.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class JackhammerTest {

    @Test
    public void whenConstructed_JackhammerShouldAlwaysReturnCorrectPricing(){
        //Arrange, Act
        Jackhammer result = new Jackhammer("code", "type", "brand");

        //Assert
        Assertions.assertEquals(new BigDecimal("2.99"), result.getDailyCharge(), "Jackhammer Daily Charge should be correct value");
        Assertions.assertEquals(true, result.getWeekdayCharge(), "Jackhammer Weekday Charge should be correct value");
        Assertions.assertEquals(false, result.getWeekendCharge(), "Jackhammer Weekend Charge should be correct value");
        Assertions.assertEquals(false, result.getHolidayCharge(), "Jackhammer Holiday Charge should be correct value");
    }
}
