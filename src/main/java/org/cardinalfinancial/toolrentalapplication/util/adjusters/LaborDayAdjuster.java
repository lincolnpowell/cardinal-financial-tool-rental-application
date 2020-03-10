package org.cardinalfinancial.toolrentalapplication.util.adjusters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class LaborDayAdjuster implements TemporalAdjuster {
    /**
     * The adjustInto(Temporal) method adjusts the inputted Temporal object to the next observed Labor Day
     * holiday, which is the first Monday in September.
     * @param input
     * @return Adjusted Temporal object representing the Labor Day holiday
     */
    @Override
    public Temporal adjustInto(Temporal input) {
        LocalDate localDate = LocalDate.from(input);
        LocalDate laborDay = getLaborDay(localDate);

        // If the LocalDate object is after its current year Labor Day holiday, return next year's Labor
        // Day holiday date; else, return the Labor Day holiday date.
        return localDate.isAfter(laborDay) ? laborDay.plusYears(1) : laborDay;
    }

    /**
     * The getLaborDay(LocalDate) method adjusts the inputted LocalDate object to its current year Labor
     * Day holiday, which is the first Monday in September.
     * @param localDate
     * @return LocalDate object representing the Labor Day holiday
     */
    private LocalDate getLaborDay(LocalDate localDate) {
        return LocalDate.of(localDate.getYear(), 9, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
    }
}
