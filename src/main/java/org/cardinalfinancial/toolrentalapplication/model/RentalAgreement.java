package org.cardinalfinancial.toolrentalapplication.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.cardinalfinancial.toolrentalapplication.entity.Tool;

public class RentalAgreement {
    private int chargeableDays, discountPercent, rentalDayCount;
    private BigDecimal discountAmount, finalCharge, preDiscountCharge;
    private LocalDate checkoutDate;
    private Tool tool;
    private final Locale LOCALE = Locale.US;
    private final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(LOCALE);
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");
    private final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance(LOCALE);

    private RentalAgreement() {}

    protected RentalAgreement(RentalAgreementBuilder builder) {
        this.chargeableDays = builder.chargeableDays;
        this.checkoutDate = builder.checkoutDate;
        this.discountAmount = builder.discountAmount;
        this.discountPercent = builder.discountPercent;
        this.finalCharge = builder.finalCharge;
        this.preDiscountCharge = builder.preDiscountCharge;
        this.rentalDayCount = builder.rentalDayCount;
        this.tool = builder.tool;
    }

    /**
     * The print() method returns a pretty formatted String representation of a RentalAgreement object.
     * @return Pretty printed rental agreement
     */
    public String print() {
        return "Tool code: " + tool.getToolCode() +
                "\n\nTool type: " + tool.getRentalCharge().getToolType() +
                "\n\nTool brand: " + tool.getBrand() +
                "\n\nRental days: " + rentalDayCount +
                "\n\nCheck out date: " + DATE_TIME_FORMATTER.format(checkoutDate) +
                "\n\nDue date: " + DATE_TIME_FORMATTER.format(checkoutDate.plusDays(rentalDayCount)) +
                "\n\nDaily rental charge: " + CURRENCY_FORMATTER.format(tool.getRentalCharge().getDailyCharge()) +
                "\n\nCharge days: " + chargeableDays +
                "\n\nPre-discount charge: " + CURRENCY_FORMATTER.format(preDiscountCharge) +
                "\n\nDiscount percent: " + PERCENT_FORMATTER.format((double) discountPercent / 100) +
                "\n\nDiscount amount: " + CURRENCY_FORMATTER.format(discountAmount) +
                "\n\nFinal charge: " + CURRENCY_FORMATTER.format(finalCharge);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof RentalAgreement) {
            return this.toString().equals(object.toString());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "{ tool: " + tool +
                ", rentalDayCount: " + rentalDayCount +
                ", checkoutDate: " + DATE_TIME_FORMATTER.format(checkoutDate) +
                ", dueDate: " + DATE_TIME_FORMATTER.format(checkoutDate.plusDays(rentalDayCount)) +
                ", dailyRentalCharge: " + (tool == null ? null : CURRENCY_FORMATTER.format(tool.getRentalCharge().getDailyCharge())) +
                ", chargeableDays: " + chargeableDays +
                ", preDiscountCharge: " + CURRENCY_FORMATTER.format(preDiscountCharge) +
                ", discountPercent: " + PERCENT_FORMATTER.format((double) discountPercent / 100) +
                ", discountAmount: " + CURRENCY_FORMATTER.format(discountAmount) +
                ", finalCharge: " + CURRENCY_FORMATTER.format(finalCharge) +
                " }";
    }
}
