package com.ev.flooring.DTO;

import com.ev.flooring.service.FlooringMasteryPersistenceException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author EverlynLeon
 *
 * */

public class Order {
    private int num;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String product;
    private BigDecimal area;
    private BigDecimal costPerSqft;
    private BigDecimal laborCostPerSqft;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    private final String DELIMITER = ",";

    public Order(int num){
        this.num = num;
    }

    public Order(int num, String customerName, String state, BigDecimal taxRate, String product,
                 BigDecimal area, BigDecimal costPerSqft, BigDecimal laborCostPerSqft, BigDecimal materialCost,
                 BigDecimal laborCost, BigDecimal tax, BigDecimal total) {

        this.num = num;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.product = product;
        this.area = area;
        this.costPerSqft = costPerSqft;
        this.laborCostPerSqft = laborCostPerSqft;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.tax = tax;
        this.total = total;
    }

    public Order(String orderString) throws FlooringMasteryPersistenceException {

        try{
            String[] token = orderString.split(DELIMITER);
            this.num = Integer.parseInt(token[0]);
            this.customerName = token[1];
            this.state = token[2];
            this.taxRate = new BigDecimal(token[3]);
            this.product = token[4];
            this.area = new BigDecimal(token[5]);
            this.costPerSqft = new BigDecimal(token[6]);
            this.laborCostPerSqft = new BigDecimal(token[7]);
            this.materialCost = new BigDecimal(token[8]);
            this.laborCost = new BigDecimal(token[9]);
            this.tax = new BigDecimal(token[10]);
            this.total = new BigDecimal(token[11]);

        }catch(PatternSyntaxException ex){
            throw new FlooringMasteryPersistenceException(ex.getMessage());
        }catch(NullPointerException | NumberFormatException ex){
            throw new FlooringMasteryPersistenceException("Error");
        }
    }
    public String marshalOrderAsText() {

        return this.getnum() +
                DELIMITER + this.getCustomerName() +
                DELIMITER + this.getState() +
                DELIMITER + this.getTaxRate() +
                DELIMITER + this.getProduct() +
                DELIMITER + this.getArea() +
                DELIMITER + this.getCostPerSqft() +
                DELIMITER + this.getLaborCostPerSqft() +
                DELIMITER + this.getMaterialCost() +
                DELIMITER + this.getLaborCost() +
                DELIMITER + this.getTax() +
                DELIMITER + this.getTotal();
    }

    public int getnum(){
        return num;
    }
    public void setnum(int num){
        this.num = num;
    }
    public String getCustomerName(){
        return customerName;
    }
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state = state;
    }

    public BigDecimal getTaxRate(){
        return taxRate;
    }
    public void setTaxRate(BigDecimal taxRate){
        this.taxRate = taxRate;
    }

    public String getProduct(){
        return product;
    }
    public void setProduct(String product){
        this.product = product;
    }

    public BigDecimal getArea(){
        return area;
    }
    public void setArea(BigDecimal area){
        this.area = area;
    }

    public BigDecimal getCostPerSqft(){
        return costPerSqft;
    }
    public void setCostPerSqft(BigDecimal costPerSqft){
        this.costPerSqft = costPerSqft;
    }

    public BigDecimal getLaborCostPerSqft(){
        return laborCostPerSqft;
    }
    public void setLaborCostPerSqft(BigDecimal laborCostPerSqft){
        this.laborCostPerSqft = laborCostPerSqft;
    }

    public BigDecimal getMaterialCost(){
        return materialCost;
    }
    public void setMaterialCost(BigDecimal materialCost){
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost(){
        return laborCost;
    }
    public void setLaborCost(BigDecimal laborCost){
        this.laborCost = laborCost;
    }

    public BigDecimal getTax(){
        return tax;
    }
    public void setTax(BigDecimal tax){
        this.tax = tax;
    }

    public BigDecimal getTotal(){
        return total;
    }
    public void setTotal(BigDecimal total){
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getnum() == order.getnum() &&
                getCustomerName().equals(order.getCustomerName()) &&
                getState().equals(order.getState()) &&
                getTaxRate().equals(order.getTaxRate()) &&
                getProduct().equals(order.getProduct()) &&
                getArea().equals(order.getArea()) &&
                getCostPerSqft().equals(order.getCostPerSqft()) &&
                getLaborCostPerSqft().equals(order.getLaborCostPerSqft()) &&
                getMaterialCost().equals(order.getMaterialCost()) &&
                getLaborCost().equals(order.getLaborCost()) &&
                getTax().equals(order.getTax()) &&
                getTotal().equals(order.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getnum(),
                getCustomerName(),
                getState(),
                getTaxRate(),
                getProduct(),
                getArea(),
                getCostPerSqft(),
                getLaborCostPerSqft(),
                getMaterialCost(),
                getLaborCost(),
                getTax(),
                getTotal(), DELIMITER);
    }


    @Override
    public String toString() {
        return "Order{" +
                "num = " + num +
                ", customerName = '" + customerName + '\'' +
                ", state = '" + state + '\'' +
                ", taxRate = " + taxRate +
                ", product = '" + product + '\'' +
                ", area = " + area +
                ", costPerSqft = " + costPerSqft +
                ", laborCostPerSqft = " + laborCostPerSqft +
                ", materialCost = " + materialCost +
                ", laborCost = " + laborCost +
                ", tax = " + tax +
                ", total = " + total +
                ", DELIMITER = '" + DELIMITER + '\'' +
                '}';
    }
}
