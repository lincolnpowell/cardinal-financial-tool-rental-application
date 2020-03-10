package org.cardinalfinancial.toolrentalapplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.cardinalfinancial.toolrentalapplication.model.RentalAgreement;
import org.cardinalfinancial.toolrentalapplication.service.Checkout;
import org.cardinalfinancial.toolrentalapplication.util.exceptions.InvalidDiscountPercentException;
import org.cardinalfinancial.toolrentalapplication.util.exceptions.InvalidRentalDayCountException;

public class ToolRentalApplication {
    private static final Checkout CHECKOUT = new Checkout();
    private static final Logger LOGGER = LogManager.getLogger(ToolRentalApplication.class);

    public static void main(String[] args) {
        LOGGER.traceEntry("main(args={})", args);
        long timer = System.nanoTime();

        Scanner scanner = new Scanner(System.in);
        String selection;

        menu: do {
            System.out.println("Please select one of the options:" +
                    "\n\n1. Enter new rental agreement" +
                    "\n0. Exit program");

            System.out.print("\nSelection: ");
            selection = scanner.nextLine();
            LOGGER.debug("Menu Selection: " + selection);

            switch (selection) {
                case "1": {
                    //TODO: Invalid tool code validation missing: Results in NullPointerException
                    System.out.print("\nEnter tool code: ");
                    String toolCode = scanner.nextLine().toUpperCase();
                    LOGGER.debug("Enter tool code: " + toolCode);

                    System.out.print("\nEnter number of rental days: ");
                    String rentalDayCount = scanner.nextLine();
                    LOGGER.debug("Enter number of rental days: " + rentalDayCount);

                    System.out.print("\nEnter discount percent: ");
                    String discountPercent = scanner.nextLine();
                    LOGGER.debug("Enter discount percent: " + discountPercent);

                    //TODO: Invalid checkout date validation missing: Could result in thrown unchecked exception
                    System.out.print("\nEnter checkout date: ");
                    String checkoutDate = scanner.nextLine();
                    System.out.println();
                    LOGGER.debug("Enter checkout date: " + checkoutDate);

                    RentalAgreement rentalAgreement = null;

                    try {
                        rentalAgreement = CHECKOUT.submit(toolCode, Integer.parseInt(rentalDayCount),
                                Integer.parseInt(discountPercent),
                                LocalDate.parse(checkoutDate, DateTimeFormatter.ofPattern("M/d/yy")));
                    } catch (InvalidDiscountPercentException | InvalidRentalDayCountException e) {
                        System.out.println(e.getMessage() + "\n");
                        LOGGER.error("", e);
                    } finally {
                        if (rentalAgreement != null) {
                            System.out.println(rentalAgreement.print() + "\n");
                            LOGGER.debug("\n\n" + rentalAgreement.print() + "\n");
                        }
                    }
                    break;
                }
                case "0": {
                    break menu;
                }
                default: {
                    System.out.println("\nInvalid selection. Try again...\n");
                    LOGGER.warn("Invalid input: " + selection);
                }
            }
        } while(true);

        scanner.close();
        LOGGER.traceExit("Program completion elapsed time: " + ((System.nanoTime() - timer) / 1000000) + " ms");
    }
}
