package personal.tm.model;

import java.math.BigDecimal;

/**
 * ChargeInformationDTO is responsible for representing charge and pricing data
 */
public class ChargeInformationDTO {

    /**
     * The number of days that will be charged
     */
    public Integer chargeDays;

    /**
     * The rounded amount to be charged before an optional discount is applied
     */
    public BigDecimal roundedPreDiscountCharge;

    /**
     * The {@link Integer} charge days
     */
    public Integer getChargeDays() {
        return chargeDays;
    }

    /**
     * Sets the  {@link Integer} charge days
     */
    public void setChargeDays(Integer chargeDays) {
        this.chargeDays = chargeDays;
    }

    /**
     * The {@link BigDecimal} rounded charge before an optional discount is applied
     */
    public BigDecimal getRoundedPreDiscountCharge() {
        return roundedPreDiscountCharge;
    }

    /**
     * Sets the {@link BigDecimal} rounded charge before an optional discount is applied
     */
    public void setRoundedPreDiscountCharge(BigDecimal roundedPreDiscountCharge) {
        this.roundedPreDiscountCharge = roundedPreDiscountCharge;
    }
}
