package personal.tm.model.tools;

import java.math.BigDecimal;

/**
 Ladder is a subclass of {@link Tool}. It has a set dailyCharge rate of $1.99.
 It does have a weekday charge.
 It does have a weekend charge.
 It does not have a holiday charge.
 */
public class Ladder extends Tool {
    /**
     * Constructor
     */
    public Ladder(String toolCode, String toolType, String brand) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
    }

    /**
     * The {@link BigDecimal} daily charge amount
     */
    @Override
    public BigDecimal getDailyCharge() {
        return new BigDecimal("1.99");
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
        return true;
    }

    /**
     * {@link Boolean} if charged on a holiday
     */
    @Override
    public Boolean getHolidayCharge() {
        return false;
    }
}
