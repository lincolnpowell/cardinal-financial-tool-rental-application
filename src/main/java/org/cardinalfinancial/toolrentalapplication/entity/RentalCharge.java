package org.cardinalfinancial.toolrentalapplication.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rental_charge")
public class RentalCharge {
    @Id
    @Column(name = "tool_type")
    private String toolType;

    @Column(name = "daily_charge", scale = 2)
    private BigDecimal dailyCharge;

    @Column(name = "is_charged_on_weekdays")
    private boolean isChargedOnWeekdays;

    @Column(name = "is_charged_on_weekends")
    private boolean isChargedOnWeekends;

    @Column(name = "is_charged_on_holidays")
    private boolean isChargedOnHolidays;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rentalCharge")
    private List<Tool> tools = new ArrayList<>();

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean isChargedOnWeekdays() {
        return isChargedOnWeekdays;
    }

    public void setChargedOnWeekdays(boolean chargedOnWeekdays) {
        isChargedOnWeekdays = chargedOnWeekdays;
    }

    public boolean isChargedOnWeekends() {
        return isChargedOnWeekends;
    }

    public void setChargedOnWeekends(boolean chargedOnWeekends) {
        isChargedOnWeekends = chargedOnWeekends;
    }

    public boolean isChargedOnHolidays() {
        return isChargedOnHolidays;
    }

    public void setChargedOnHolidays(boolean chargedOnHolidays) {
        isChargedOnHolidays = chargedOnHolidays;
    }

    public void addTool(Tool tool) {
        this.tools.add(tool);
        tool.setRentalCharge(this);
    }

    @Override
    public String toString() {
        return "{ toolType: " + toolType + ", dailyCharge: " + dailyCharge +
                ", isChargedOnWeekdays: " +  isChargedOnWeekdays + ", isChargedOnWeekends: " + isChargedOnWeekends +
                ", isChargedOnHolidays: " + isChargedOnHolidays + " }";
    }
}