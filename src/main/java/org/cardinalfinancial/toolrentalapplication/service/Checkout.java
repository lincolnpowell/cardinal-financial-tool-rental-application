package org.cardinalfinancial.toolrentalapplication.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.cardinalfinancial.toolrentalapplication.dao.ToolDAO;
import org.cardinalfinancial.toolrentalapplication.entity.Tool;
import org.cardinalfinancial.toolrentalapplication.model.RentalAgreement;
import org.cardinalfinancial.toolrentalapplication.model.RentalAgreementBuilder;
import org.cardinalfinancial.toolrentalapplication.util.adjusters.IndependenceDayAdjuster;
import org.cardinalfinancial.toolrentalapplication.util.adjusters.LaborDayAdjuster;
import org.cardinalfinancial.toolrentalapplication.util.exceptions.InvalidDiscountPercentException;
import org.cardinalfinancial.toolrentalapplication.util.exceptions.InvalidRentalDayCountException;

public class Checkout {
    private final Logger LOGGER = LogManager.getLogger(Checkout.class);

    /**
     * The generateRentalAgreement(Tool, int, int, LocalDate) helper method performs the necessary calculations which
     * are part of the rental agreement and returns a RentalAgreement object representing the created rental agreement.
     * @param tool A Tool object queried from the submitted tool code
     * @param rentalDayCount An int representing the submitted rental day count
     * @param discountPercent An int representing the submitted discount percent
     * @param checkoutDate A LocalDate object representing the submitted checkout date
     * @return RentalAgreement object representing a rental agreement
     */
    private RentalAgreement generateRentalAgreement(Tool tool, int rentalDayCount, int discountPercent, LocalDate checkoutDate) {
        LOGGER.traceEntry("generateRentalAgreement(tool={}, rentalDayCount={}, discountPercent={}, checkoutDate={})",
                tool, rentalDayCount, discountPercent, checkoutDate);

        //TODO: Refactor - Below method calls could be moved to its own abstraction; thus, eliminating the need for the
        // following RentalAgreementBuilder methods (instead passing an abstraction via setter which would represent
        // the below data):
        // + chargeableDays(int): RentalAgreementBuilder
        // + preDiscountCharge(BigDecimal): RentalAgreementBuilder
        // + discountAmount(BigDecimal): RentalAgreementBuilder
        // + finalCharge(BigDecimal): RentalAgreementBuilder
        int chargeableDays = calculateChargeableDays(tool, rentalDayCount, checkoutDate);
        BigDecimal preDiscountCharge = calculatePreDiscountCharge(chargeableDays, tool.getRentalCharge().getDailyCharge());
        BigDecimal discountAmount = calculateDiscountAmount(discountPercent, preDiscountCharge);
        BigDecimal finalCharge = calculateFinalCharge(discountAmount, preDiscountCharge);

        return LOGGER.traceExit(new RentalAgreementBuilder()
                .chargeableDays(chargeableDays)
                .checkoutDate(checkoutDate)
                .discountAmount(discountAmount)
                .discountPercent(discountPercent)
                .finalCharge(finalCharge)
                .preDiscountCharge(preDiscountCharge)
                .rentalDayCount(rentalDayCount)
                .tool(tool)
                .build());
    }

    /**
     * The submit(String, int, int, LocalDate) method validates a submitted rental request and, if successful,
     * returns a RentalAgreement object representing the generated rental agreement.
     * @param toolCode A String representing a tool code
     * @param rentalDayCount An int representing the rental day count
     * @param discountPercent An int representing the discount percent
     * @param checkoutDate A LocalDate object representing the checkout date
     * @return RentalAgreement object representing a rental agreement
     * @throws InvalidRentalDayCountException Is the rental day count value greater than 0?
     * @throws InvalidDiscountPercentException Is the discount percent value between 0 and 100?
     */
    public RentalAgreement submit(String toolCode, int rentalDayCount, int discountPercent, LocalDate checkoutDate)
            throws InvalidRentalDayCountException, InvalidDiscountPercentException {
        LOGGER.traceEntry("checkout(toolCode={}, rentalDayCount={}, discountPercent={}, checkoutDate={})",
                toolCode, rentalDayCount, discountPercent, checkoutDate);
        if (rentalDayCount < 1) {
            throw new InvalidRentalDayCountException("Rental day count must be greater than 0");
        } else if (0 > discountPercent || discountPercent > 100) {
            throw new InvalidDiscountPercentException("Discount percent value must be between 0 and 100");
        } else {
            return LOGGER.traceExit(generateRentalAgreement(new ToolDAO().getTool(toolCode), rentalDayCount, discountPercent, checkoutDate));
        }
    }

    //TODO: Refactor - Below methods could be moved to another abstraction (e.g. a calculator or calendar)

    /**
     * The calculateChargeableDays(Tool, int, LocalDate) helper method iterates through each rental day returning
     * the count of chargeable days applicable to the tool type (e.g. does the tool type charge on weekdays,
     * weekends or holidays).
     * @param tool A Tool object queried from the submitted tool code
     * @param rentalDayCount An int representing the submitted rental day count
     * @param checkoutDate A LocalDate object representing the submitted checkout date
     * @return Number of chargeable days
     */
    private int calculateChargeableDays(Tool tool, int rentalDayCount, LocalDate checkoutDate) {
        LOGGER.traceEntry("calculateChargeableDays(tool={}, rentalDayCount={}, checkoutDate={})",
                tool, rentalDayCount, checkoutDate);
        int chargeableDays = 0;
        for (LocalDate localDate = checkoutDate; localDate.isBefore(checkoutDate.plusDays(rentalDayCount)); localDate = localDate.plusDays(1)) {
            if (tool.getRentalCharge().isChargedOnHolidays() && isHoliday(localDate) ||
                    tool.getRentalCharge().isChargedOnWeekends() && isWeekend(localDate) ||
                    tool.getRentalCharge().isChargedOnWeekdays() && isWeekday(localDate)) {
                chargeableDays++;
            }
        }
        return LOGGER.traceExit(chargeableDays);
    }

    /**
     * The calculateDiscountAmount(int, BigDecimal) helper method calculates the discount percentage multiplied by
     * the pre-discount charge.  The resultant discount amount value is rounded half up to cents.
     * @param discountPercent An int representing the discount percent
     * @param preDiscountAmount A BigDecimal object representing the pre-discount amount
     * @return Discount amount
     */
    private BigDecimal calculateDiscountAmount(int discountPercent, BigDecimal preDiscountAmount) {
        LOGGER.traceEntry("calculateDiscountAmount(discountPercent={}, preDiscountAmount={})",
                discountPercent, preDiscountAmount);
        return LOGGER.traceExit(BigDecimal.valueOf((double) discountPercent / 100)
                .multiply(preDiscountAmount)
                .setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * The calculateFinalCharge(BigDecimal, BigDecimal) helper method calculates the pre-discount charge minus the
     * discount amount and returns the final charge.
     * @param discountAmount A BigDecimal object representing the discount amount
     * @param preDiscountCharge A BigDecimal object representing the pre-discount charge
     * @return Final charge
     */
    private BigDecimal calculateFinalCharge(BigDecimal discountAmount, BigDecimal preDiscountCharge) {
        LOGGER.traceEntry("calculateFinalCharge(discountAmount={}, preDiscountCharge={})",
                discountAmount, preDiscountCharge);
        return LOGGER.traceExit(preDiscountCharge.subtract(discountAmount));
    }

    /**
     * The calculatePreDiscountCharge(int, BigDecimal) helper method calculates the number of chargeable days
     * multiplied by the daily charge rate of the tool type.  The resultant pre-discount charge value is rounded
     * half up to cents.
     * @param chargeableDays An int representing the number of chargeable days
     * @param dailyCharge A BigDecimal object representing the daily charge of the tool type
     * @return Pre-discount charge
     */
    private BigDecimal calculatePreDiscountCharge(int chargeableDays, BigDecimal dailyCharge) {
        LOGGER.traceEntry("calculatePreDiscountCharge(chargeableDays={}, dailyCharge={})",
                chargeableDays, dailyCharge);
        return LOGGER.traceExit(new BigDecimal(chargeableDays)
                .multiply(dailyCharge)
                .setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * The isHoliday(LocalDate) helper method adjusts the inputted LocalDate object to the custom holiday temporal
     * adjusters and evaluates if the inputted LocalDate object is a holiday.
     * @param localDate A LocalDate object representing a date value
     * @return Is the inputted LocalDate object a holiday?
     */
    private boolean isHoliday(LocalDate localDate) {
        LOGGER.traceEntry("isHoliday(localDate={})", localDate);
        return LOGGER.traceExit(localDate.equals(localDate.with(new IndependenceDayAdjuster())) ||
                localDate.equals(localDate.with(new LaborDayAdjuster())));
    }

    private boolean isWeekday(LocalDate localDate) {
        LOGGER.traceEntry("isWeekday(localDate={})", localDate);
        switch(localDate.getDayOfWeek()) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                return LOGGER.traceExit(true);
            default:
                return LOGGER.traceExit(false);
        }
    }

    private boolean isWeekend(LocalDate localDate) {
        LOGGER.traceEntry("isWeekend(localDate={})", localDate);
        switch(localDate.getDayOfWeek()) {
            case SATURDAY:
            case SUNDAY:
                return LOGGER.traceExit(true);
            default:
                return LOGGER.traceExit(false);
        }
    }
}
