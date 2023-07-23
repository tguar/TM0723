package personal.tm.model.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import personal.tm.model.tools.Chainsaw;

import java.math.BigDecimal;

public class ChainsawTest {

    @Test
    public void whenConstructed_ChainsawShouldAlwaysReturnCorrectPricing(){
        //Arrange, Act
        Chainsaw result = new Chainsaw("code", "type", "brand");

        //Assert
        Assertions.assertEquals(new BigDecimal("1.49"), result.getDailyCharge(), "Chainsaw Daily Charge should be correct value");
        Assertions.assertEquals(true, result.getWeekdayCharge(), "Chainsaw Weekday Charge should be correct value");
        Assertions.assertEquals(false, result.getWeekendCharge(), "Chainsaw Weekend Charge should be correct value");
        Assertions.assertEquals(true, result.getHolidayCharge(), "Chainsaw Holiday Charge should be correct value");
    }
}
