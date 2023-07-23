package personal.tm.model.tools;

import java.math.BigDecimal;

/**
 * A Tool is a superclass object that all specific tools inherit from
 */
public abstract class Tool {
    /**
     * The tool code
     */
    public String toolCode;

    /**
     * The tool type
     */
    public String toolType;

    /**
     * The tool brand
     */
    public String brand;

    /**
     * The {@link String} tool code
     */
    public String getToolCode() {
        return toolCode;
    }

    /**
     * The {@link String} tool type
     */
    public String getToolType() {
        return toolType;
    }

    /**
     * The {@link String} tool brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * The {@link BigDecimal} daily charge amount
     */
    public abstract BigDecimal getDailyCharge();

    /**
     * {@link Boolean} if charged on a weekday
     */
    public abstract Boolean getWeekdayCharge();

    /**
     * {@link Boolean} if charged on a weekend
     */
    public abstract Boolean getWeekendCharge();

    /**
     * {@link Boolean} if charged on a holiday
     */
    public abstract Boolean getHolidayCharge();
}
