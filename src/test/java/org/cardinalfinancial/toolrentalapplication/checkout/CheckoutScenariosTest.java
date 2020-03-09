package org.cardinalfinancial.toolrentalapplication.checkout;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.cardinalfinancial.toolrentalapplication.dao.ToolDAO;
import org.cardinalfinancial.toolrentalapplication.model.RentalAgreement;
import org.cardinalfinancial.toolrentalapplication.model.RentalAgreementBuilder;
import org.cardinalfinancial.toolrentalapplication.service.Checkout;
import org.cardinalfinancial.toolrentalapplication.util.exceptions.InvalidDiscountPercentException;
import org.cardinalfinancial.toolrentalapplication.util.exceptions.InvalidRentalDayCountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckoutScenariosTest {
    private Checkout checkout;
    private ToolDAO toolDAO;
    private RentalAgreement rentalAgreementMock;
    private final Logger LOGGER = LogManager.getLogger(CheckoutScenariosTest.class);

    @BeforeEach
    public void initBeforeEach() {
        checkout = new Checkout();
        toolDAO = new ToolDAO();
    }

    @AfterEach
    public void destroyAfterEach() {
        checkout = null;
        rentalAgreementMock = null;
        toolDAO = null;
    }

    @Test
    public void test1() {
        Throwable throwable = assertThrows(InvalidDiscountPercentException.class, () -> checkout.submit(
                "JAKR", 5, 101,
                LocalDate.parse("9/3/15", DateTimeFormatter.ofPattern("M/d/yy"))
        ));

        assertEquals("Discount percent value must be between 0 and 100", throwable.getMessage());
    }

    @Test
    public void test2() {
        rentalAgreementMock = new RentalAgreementBuilder()
                .tool(toolDAO.getTool("LADW"))
                .rentalDayCount(3)
                .discountPercent(10)
                .checkoutDate(LocalDate.parse("7/2/20", DateTimeFormatter.ofPattern("M/d/yy")))
                .chargeableDays(3)
                .preDiscountCharge(new BigDecimal("5.97"))
                .discountAmount(new BigDecimal("0.60"))
                .finalCharge(new BigDecimal("5.37"))
                .build();

        RentalAgreement rentalAgreement = null;

        try {
            rentalAgreement = checkout.submit("LADW", 3, 10,
                    LocalDate.parse("7/2/20", DateTimeFormatter.ofPattern("M/d/yy")));
        } catch (InvalidDiscountPercentException | InvalidRentalDayCountException e) {
            LOGGER.error("", e);
        }

        assertEquals(rentalAgreementMock, rentalAgreement);
    }

    @Test
    public void test3() {
        rentalAgreementMock = new RentalAgreementBuilder()
                .tool(toolDAO.getTool("CHNS"))
                .rentalDayCount(5)
                .discountPercent(25)
                .checkoutDate(LocalDate.parse("7/2/15", DateTimeFormatter.ofPattern("M/d/yy")))
                .chargeableDays(3)
                .preDiscountCharge(new BigDecimal("4.47"))
                .discountAmount(new BigDecimal("1.12"))
                .finalCharge(new BigDecimal("3.35"))
                .build();

        RentalAgreement rentalAgreement = null;

        try {
            rentalAgreement = checkout.submit("CHNS", 5, 25,
                    LocalDate.parse("7/2/15", DateTimeFormatter.ofPattern("M/d/yy")));
        } catch (InvalidDiscountPercentException | InvalidRentalDayCountException e) {
            LOGGER.error("", e);
        }

        assertEquals(rentalAgreementMock, rentalAgreement);
    }

    @Test
    public void test4() {
        rentalAgreementMock = new RentalAgreementBuilder()
                .tool(toolDAO.getTool("JAKR"))
                .rentalDayCount(6)
                .discountPercent(0)
                .checkoutDate(LocalDate.parse("9/3/15", DateTimeFormatter.ofPattern("M/d/yy")))
                .chargeableDays(4)
                .preDiscountCharge(new BigDecimal("11.96"))
                .discountAmount(new BigDecimal("0.00"))
                .finalCharge(new BigDecimal("11.96"))
                .build();

        RentalAgreement rentalAgreement = null;

        try {
            rentalAgreement = checkout.submit("JAKR", 6, 0,
                    LocalDate.parse("9/3/15", DateTimeFormatter.ofPattern("M/d/yy")));
        } catch (InvalidDiscountPercentException | InvalidRentalDayCountException e) {
            LOGGER.error("", e);
        }

        assertEquals(rentalAgreementMock, rentalAgreement);
    }

    @Test
    public void test5() {
        rentalAgreementMock = new RentalAgreementBuilder()
                .tool(toolDAO.getTool("JAKR"))
                .rentalDayCount(9)
                .discountPercent(0)
                .checkoutDate(LocalDate.parse("7/2/15", DateTimeFormatter.ofPattern("M/d/yy")))
                .chargeableDays(7)
                .preDiscountCharge(new BigDecimal("20.93"))
                .discountAmount(new BigDecimal("0.00"))
                .finalCharge(new BigDecimal("20.93"))
                .build();

        RentalAgreement rentalAgreement = null;

        try {
            rentalAgreement = checkout.submit("JAKR", 9, 0,
                    LocalDate.parse("7/2/15", DateTimeFormatter.ofPattern("M/d/yy")));
        } catch (InvalidDiscountPercentException | InvalidRentalDayCountException e) {
            LOGGER.error("", e);
        }

        assertEquals(rentalAgreementMock, rentalAgreement);
    }

    @Test
    public void test6() {
        rentalAgreementMock = new RentalAgreementBuilder()
                .tool(toolDAO.getTool("JAKR"))
                .rentalDayCount(4)
                .discountPercent(50)
                .checkoutDate(LocalDate.parse("7/2/20", DateTimeFormatter.ofPattern("M/d/yy")))
                .chargeableDays(2)
                .preDiscountCharge(new BigDecimal("5.98"))
                .discountAmount(new BigDecimal("2.99"))
                .finalCharge(new BigDecimal("2.99"))
                .build();

        RentalAgreement rentalAgreement = null;

        try {
            rentalAgreement = checkout.submit("JAKR", 4, 50,
                    LocalDate.parse("7/2/20", DateTimeFormatter.ofPattern("M/d/yy")));
        } catch (InvalidDiscountPercentException | InvalidRentalDayCountException e) {
            LOGGER.error("", e);
        }

        assertEquals(rentalAgreementMock, rentalAgreement);
    }
}
