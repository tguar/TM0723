package personal.tm.model;

import personal.tm.model.tools.Tool;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static personal.tm.utils.Constants.PRINTED_DATE_FORMAT;

/**
 * Represents the information needed to check out a {@link Tool}
 */
public class RentalAgreementDTO {
    /**
     * The tool code
     */
    String toolCode;

    /**
     * The tool type
     */
    String toolType;

    /**
     * The tool brand
     */
    String toolBrand;

    /**
     * The number of rental days
     */
    Long rentalDays;

    /**
     * The date the tool was checked out
     */
    LocalDate checkoutDate;

    /**
     * The date the tool is due back
     */
    LocalDate dueDate;

    /**
     * The daily charge amount of the item
     */
    BigDecimal dailyRentalCharge;

    /**
     * The number of days charged
     */
    Integer chargeDays;

    /**
     * The amount due pre-discount
     */
    BigDecimal preDiscountCharge;

    /**
     * The discount percent
     */
    Integer discountPercent;

    /**
     * The discount amount
     */
    BigDecimal discountAmount;

    /**
     * The final charge
     */
    BigDecimal finalCharge;

    /**
     * Constructor
     */
    public RentalAgreementDTO(){};

    /**
     * The {@link String} tool code
     */
    public String getToolCode() {
        return toolCode;
    }

    /**
     * Sets the {@link String} tool code
     */
    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    /**
     * The {@link String} tool type
     */
    public String getToolType() {
        return toolType;
    }

    /**
     * Sets the {@link String} tool type
     */
    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    /**
     * The {@link String} tool brand
     */
    public String getToolBrand() {
        return toolBrand;
    }

    /**
     * Sets the {@link String} tool code
     */
    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    /**
     * The {@link Long} amount of rental days
     */
    public Long getRentalDays() {
        return rentalDays;
    }

    /**
     * Sets the {@link Long} amount of rental days
     */
    public void setRentalDays(Long rentalDays) {
        this.rentalDays = rentalDays;
    }

    /**
     * The {@link LocalDate} checkout date
     */
    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    /**
     * Sets the {@link LocalDate} checkout date
     */
    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    /**
     * The {@link LocalDate} due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the {@link LocalDate} due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * The {@link BigDecimal} daily charge rate
     */
    public BigDecimal getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    /**
     * Sets the {@link BigDecimal} daily charge rate
     */
    public void setDailyRentalCharge(BigDecimal dailyRentalCharge) {
        this.dailyRentalCharge = dailyRentalCharge;
    }

    /**
     * The {@link Integer} number of days charged
     */
    public Integer getChargeDays() {
        return chargeDays;
    }

    /**
     * Sets the {@link Integer} number of days charged
     */
    public void setChargeDays(Integer chargeDays) {
        this.chargeDays = chargeDays;
    }

    /**
     * The {@link BigDecimal} pre discount charge
     */
    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    /**
     * Sets the {@link BigDecimal} pre discount charge
     */
    public void setPreDiscountCharge(BigDecimal preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    /**
     * The {@link Integer} discount percent
     */
    public Integer getDiscountPercent() {
        return discountPercent;
    }

    /**
     * Sets the {@link Integer} pre discount charge
     */
    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    /**
     * The {@link BigDecimal} discount amount
     */
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Sets the {@link BigDecimal} pre discount charge
     */
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * The {@link BigDecimal} final charge
     */
    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    /**
     * Sets the {@link BigDecimal} final charge
     */
    public void setFinalCharge(BigDecimal finalCharge) {
        this.finalCharge = finalCharge;
    }

    /**
     * Prints a formatted {@link personal.tm.model.RentalAgreementDTO} to console
     */
    public void printRentalAgreement() {
        System.out.println("Tool code: " + this.toolCode);
        System.out.println("Tool type: " + this.toolType);
        System.out.println("Tool brand: " + this.toolBrand);
        System.out.println("Rental days: " + this.rentalDays);
        System.out.println("Check out date: " + this.checkoutDate.format(DateTimeFormatter.ofPattern(PRINTED_DATE_FORMAT)));
        System.out.println("Due date: " + this.dueDate.format(DateTimeFormatter.ofPattern(PRINTED_DATE_FORMAT)));
        System.out.println("Daily rental charge: " + NumberFormat.getCurrencyInstance().format(this.dailyRentalCharge));
        System.out.println("Charge days: " + this.chargeDays);
        System.out.println("Pre-discount charge: " + NumberFormat.getCurrencyInstance().format(this.preDiscountCharge));
        System.out.println("Discount percent: " + this.discountPercent + "%");
        System.out.println("Discount amount: " + NumberFormat.getCurrencyInstance().format(this.discountAmount));
        System.out.println("Final charge: " + NumberFormat.getCurrencyInstance().format(this.finalCharge));
    }
}
