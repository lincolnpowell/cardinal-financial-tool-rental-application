package org.cardinalfinancial.toolrentalapplication.util.adjusters;

import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class IndependenceDayAdjuster implements TemporalAdjuster {
    /**
     * The adjustInto(Temporal) method adjusts the inputted Temporal object to the next observed Independence
     * Day holiday.  If the 4th of July falls on a weekend, it is observed on the closest weekday (if Saturday,
     * then Friday before, or, if Sunday, the Monday after).
     * @param input
     * @return Adjusted Temporal object representing the observed Independence Day holiday
     */
    @Override
    public Temporal adjustInto(Temporal input) {
        LocalDate independenceDay = getIndependenceDay(LocalDate.from(input));
        switch (independenceDay.getDayOfWeek()) {
            case SATURDAY:
                return input.with(independenceDay.minusDays(1));
            case SUNDAY:
                return input.with(independenceDay.plusDays(1));
            default:
                return input.with(independenceDay);
        }
    }

    /**
     * The getIndependenceDay(LocalDate) method evaluates and returns a LocalDate object representing the
     * next 4th of July.
     * @param localDate
     * @return LocalDate object representing July 4th
     */
    private LocalDate getIndependenceDay(LocalDate localDate) {
        LocalDate independenceDay = LocalDate.of(localDate.getYear(), 7, 4);
        return localDate.isAfter(independenceDay) ? independenceDay.plusYears(1) : independenceDay;
    }
}
