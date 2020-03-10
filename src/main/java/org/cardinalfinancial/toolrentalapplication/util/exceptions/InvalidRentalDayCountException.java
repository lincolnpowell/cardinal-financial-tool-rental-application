package org.cardinalfinancial.toolrentalapplication.util.exceptions;

/**
 * The InvalidRentalDayCountException is a checked exception which represents an invalid rental day count value.
 *
 * @since 3/9/20
 * Current business rules require rental day count values to be positive integers.
 */
public class InvalidRentalDayCountException extends Exception {
    public InvalidRentalDayCountException(String message) {
        super(message);
    }
}
