package personal.tm.model.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class LadderTest {

    @Test
    public void whenConstructed_LadderShouldAlwaysReturnCorrectPricing(){
        //Arrange, Act
        Ladder result = new Ladder("code", "type", "brand");

        //Assert
        Assertions.assertEquals(new BigDecimal("1.99"), result.getDailyCharge(), "Chainsaw Daily Charge should be correct value");
        Assertions.assertEquals(true, result.getWeekdayCharge(), "Chainsaw Weekday Charge should be correct value");
        Assertions.assertEquals(true, result.getWeekendCharge(), "Chainsaw Weekend Charge should be correct value");
        Assertions.assertEquals(false, result.getHolidayCharge(), "Chainsaw Holiday Charge should be correct value");
    }
}
