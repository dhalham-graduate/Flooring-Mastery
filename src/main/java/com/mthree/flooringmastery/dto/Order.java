package com.mthree.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Order {

    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSqFt;
    private BigDecimal laborCostPerSqFt;
    private LocalDate date;
    private String dateString;

    public BigDecimal materialCost(){
        return area.multiply(costPerSqFt);
    }

    public BigDecimal laborCost(){
        return area.multiply(laborCostPerSqFt);
    }

    public BigDecimal tax(){
        BigDecimal temp = materialCost().add(laborCost());
        BigDecimal actualTaxRate = taxRate.divide(new BigDecimal("100"));
        return temp.multiply(actualTaxRate);
    }

    public BigDecimal total(){
        return materialCost().add(laborCost()).add(tax());
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber &&
                customerName.equals(order.customerName) &&
                state.equals(order.state) &&
                taxRate.equals(order.taxRate) &&
                productType.equals(order.productType) &&
                area.equals(order.area) &&
                costPerSqFt.equals(order.costPerSqFt) &&
                laborCostPerSqFt.equals(order.laborCostPerSqFt) &&
                date.equals(order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, customerName, state, taxRate, productType, area, costPerSqFt, laborCostPerSqFt, date);
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", state='" + state + '\'' +
                ", taxRate=" + taxRate +
                ", productType='" + productType + '\'' +
                ", area=" + area +
                ", costPerSqFt=" + costPerSqFt +
                ", laborCostPerSqFt=" + laborCostPerSqFt +
                ", date=" + date +
                ", dateString='" + dateString + '\'' +
                '}';
    }
}
