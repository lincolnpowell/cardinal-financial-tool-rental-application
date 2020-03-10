package org.cardinalfinancial.toolrentalapplication.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.cardinalfinancial.toolrentalapplication.entity.Tool;

public class RentalAgreementBuilder {
    protected int chargeableDays, discountPercent, rentalDayCount;
    protected BigDecimal discountAmount, finalCharge, preDiscountCharge;
    protected LocalDate checkoutDate;
    protected Tool tool;
    private final Logger LOGGER = LogManager.getLogger(RentalAgreementBuilder.class);

    public RentalAgreementBuilder chargeableDays(int chargeableDays) {
        LOGGER.traceEntry("chargeableDays(chargeableDays={})", chargeableDays);
        this.chargeableDays = chargeableDays;
        return LOGGER.traceExit(this);
    }

    public RentalAgreementBuilder checkoutDate(LocalDate checkoutDate) {
        LOGGER.traceEntry("checkoutDate(checkoutDate={})", checkoutDate);
        this.checkoutDate = checkoutDate;
        return LOGGER.traceExit(this);
    }

    public RentalAgreementBuilder discountAmount(BigDecimal discountAmount) {
        LOGGER.traceEntry("discountAmount(discountAmount={})", discountAmount);
        this.discountAmount = discountAmount;
        return LOGGER.traceExit(this);
    }

    public RentalAgreementBuilder discountPercent(int discountPercent) {
        LOGGER.traceEntry("discountPercent(discountPercent={})", discountPercent);
        this.discountPercent = discountPercent;
        return LOGGER.traceExit(this);
    }

    public RentalAgreementBuilder finalCharge(BigDecimal finalCharge) {
        LOGGER.traceEntry("finalCharge(finalCharge={})", finalCharge);
        this.finalCharge = finalCharge;
        return LOGGER.traceExit(this);
    }

    public RentalAgreementBuilder preDiscountCharge(BigDecimal preDiscountCharge) {
        LOGGER.traceEntry("preDiscountCharge(preDiscountCharge={})", preDiscountCharge);
        this.preDiscountCharge = preDiscountCharge;
        return LOGGER.traceExit(this);
    }

    public RentalAgreementBuilder rentalDayCount(int rentalDayCount) {
        LOGGER.traceEntry("rentalDayCount(rentalDayCount={})", rentalDayCount);
        this.rentalDayCount = rentalDayCount;
        return LOGGER.traceExit(this);
    }

    public RentalAgreementBuilder tool(Tool tool) {
        LOGGER.traceEntry("tool(tool={})", tool);
        this.tool = tool;
        return LOGGER.traceExit(this);
    }

    public RentalAgreement build() {
        LOGGER.traceEntry("build()");
        return new RentalAgreement(this);
    }

    @Override
    public String toString() {
        return "{ tool: " + tool + ", rentalDayCount: " + rentalDayCount + ", checkoutDate: " + checkoutDate +
                ", dailyRentalCharge: " + (tool == null ? null : tool.getRentalCharge().getDailyCharge()) +
                ", chargeableDays: " + chargeableDays + ", preDiscountCharge: " + preDiscountCharge +
                ", discountPercent: " + discountPercent + ", discountAmount: " + discountAmount +
                ", finalCharge: " + finalCharge + " }";
    }
}
