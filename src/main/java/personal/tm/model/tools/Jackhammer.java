package personal.tm.model.tools;

import java.math.BigDecimal;

/**
 Jackhammer is a subclass of {@link Tool}. It has a set dailyCharge rate of $2.99.
 It does have a weekday charge.
 It does not have a weekend charge.
 It does not have a holiday charge.
 */
public class Jackhammer extends Tool {
    /**
     * Constructor
     */
    public Jackhammer(String toolCode, String toolType, String brand) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
    }

    /**
     * The {@link BigDecimal} daily charge amount
     */
    @Override
    public BigDecimal getDailyCharge() {
        return new BigDecimal("2.99");
    }

    /**
     * {@link Boolean} if charged on a weekday
     */
    @Override
    public Boolean getWeekdayCharge() {
        return true;
    }

    /**
     * {@link Boolean} if charged on a weekend
     */
    @Override
    public Boolean getWeekendCharge() {
        return false;
    }

    /**
     * {@link Boolean} if charged on a holiday
     */
    @Override
    public Boolean getHolidayCharge() {
        return false;
    }
}
