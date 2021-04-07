package com.mthree.flooringmastery.dto;

import java.math.BigDecimal;

public class Tax {

    private String stateName;
    private String stateAbrv;
    private BigDecimal taxRate;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateAbrv() {
        return stateAbrv;
    }

    public void setStateAbrv(String stateAbrv) {
        this.stateAbrv = stateAbrv;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
