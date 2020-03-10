package org.cardinalfinancial.toolrentalapplication.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tool")
public class Tool {
    /**
     * Unique identifier for a tool instance.
     */
    @Id
    @Column(name = "tool_code", length = 4)
    private String toolCode;

    /**
     * The brand of the tool.
     */
    @Column(name = "brand")
    private String brand;

    /**
     * The type of tool, referenced from the linked RentalCharge object
     * which specifies the daily rental charge and the days for which the daily rental charge applies.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tool_type", referencedColumnName = "tool_type", nullable = false)
    private RentalCharge rentalCharge;

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public RentalCharge getRentalCharge() {
        return rentalCharge;
    }

    public void setRentalCharge(RentalCharge rentalCharge) {
        this.rentalCharge = rentalCharge;
    }

    @Override
    public String toString() {
        return "{ toolCode: " + toolCode + ", brand: " + brand + ", toolType: " + rentalCharge.getToolType() + " }";
    }
}
