package org.cardinalfinancial.toolrentalapplication.util.exceptions;

/**
 * The InvalidDiscountPercentException is a checked exception which represents an invalid discount percent value.
 *
 * @since 3/9/20
 * Current business rules require discount percent values to be integers in the range of 0 to 100.
 */
public class InvalidDiscountPercentException extends Exception {
    public InvalidDiscountPercentException(String message) {
        super(message);
    }
}
