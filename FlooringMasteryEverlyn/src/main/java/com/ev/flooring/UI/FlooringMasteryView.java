package com.ev.flooring.UI;

import com.ev.flooring.DTO.Order;
import com.ev.flooring.service.FlooringMasteryDataValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author EverlynLeon
 *
 * */


@Component
public class FlooringMasteryView {
    private final UserIO io;

    @Autowired
    public FlooringMasteryView(UserIO io){
        this.io = io;
    }
    public int displayMainMenuAndGetSelection(){
        io.print("  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        io.print("   * <<Flooring Program>>");
        io.print("   * 1. Display Orders");
        io.print("   * 2. Add an Order");
        io.print("   * 3. Edit an Order");
        io.print("   * 4. Remove an Order");
        io.print("   * 5. Export All Data");
        io.print("   * 6. Quit");
        io.print("   *");
        io.print("   * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return io.readInt("Please select an option: ", 1, 6);
    }

    public LocalDate getDate() throws FlooringMasteryDataValidationException {
        LocalDate date = io.readDate("Please input date: MM/DD/YYYY");
        return date;
    }
    public int getnumber() throws FlooringMasteryDataValidationException {
        int num = io.readInt("Please input order number: ");
        return num;
    }
    public String getCustomerName() throws FlooringMasteryDataValidationException {
        String name = io.readString("Please input Customer Name: ");
        return name;
    }
    public String getUpdatedCustomerName(String name) throws FlooringMasteryDataValidationException {
        return io.readString("Please input new Customer Name: " + "(" + name + ")");
    }

    public String getState() throws FlooringMasteryDataValidationException {
        String state = io.readString("Please input Customer State: ");
        return state;
    }
    public String getUpdatedState() throws FlooringMasteryDataValidationException {
        String state = io.readString("Please input new Customer State: ");
        return state;
    }

    public String getProduct() throws FlooringMasteryDataValidationException {
        io.print("***** Products *****");
        io.print("1. Tile, $3.50, $4.15");
        io.print("2. Metal, $4.15, $4.75");
        io.print("3. Wood, $3.75, $4.10");
        io.print("");
        int product = io.readInt("Please input Product: ", 1, 3);
        switch(product){
            case 1:
                return "Tile";
            case 2:
                return "Laminate";
            case 3:
                return "Metal";

        }
        return null;
    }

    public String getUpdatedproduct() throws FlooringMasteryDataValidationException {
        io.print("***** Products *****");
        io.print("1. Tile, $3.50, $4.15");
        io.print("2. Metal, $4.15, $4.75");
        io.print("3. Wood, $3.75, $4.10");
        io.print("");
        io.print("4. No Change");
        io.print("");
        int product = io.readInt("Please input new Product: ",1,4);
        switch(product){
            case 1:
                return "Tile";
            case 2:
                return "Metal";
            case 3:
                return "Wood";
            case 4:
                return "";
        }
        return null;
    }
    public BigDecimal getArea() throws FlooringMasteryDataValidationException {
        BigDecimal area = io.readBigDecimal("Please input area: ");
        return area;
    }
    public String getUpdatedArea() throws FlooringMasteryDataValidationException {
        String area = io.readString("Please input new area: ");
        return area;
    }

    public void displaylist(List<Order> list){
        for (Order currentOrder : list){
            String OrderInfo=String.format("%s: %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s,",
                    currentOrder.getnum(),
                    currentOrder.getCustomerName(),
                    currentOrder.getState(),
                    currentOrder.getTax(),
                    currentOrder.getProduct(),
                    currentOrder.getArea(),
                    currentOrder.getCostPerSqft(),
                    currentOrder.getLaborCostPerSqft(),
                    currentOrder.getMaterialCost(),
                    currentOrder.getLaborCost(),
                    currentOrder.getTax(),
                    currentOrder.getTotal());
            io.print(OrderInfo);
        }
        io.readString("Please hit enter to continue");
    }

    public void displaySummary(Order order){
        io.print("Order Number: " + order.getnum());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: " + order.getTaxRate()+"%");
        io.print("Product Type: " + order.getProduct());
        io.print("Area: " + order.getArea());
        io.print("Cost/SqFt: $" + order.getCostPerSqft());
        io.print("Labor Cost/SqFt: $" + order.getLaborCostPerSqft());
        io.print("Material Cost: $" + order.getMaterialCost());
        io.print("Labor Cost: $" + order.getLaborCost());
        io.print("Tax: $" + order.getTax());
        io.print("Total: $" + order.getTotal());
        io.print("");
    }

    public void displayNewOrder(Order order){
        io.print("Order Number: " + order.getnum());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: " + order.getTaxRate()+"%");
        io.print("Product Type: " + order.getProduct());
        io.print("Area: " + order.getArea());
        io.print("Cost/SqFt: $" + order.getCostPerSqft());
        io.print("Labor Cost/SqFt: $" + order.getLaborCostPerSqft());
        io.print("Material Cost: $" + order.getMaterialCost());
        io.print("Labor Cost: $" + order.getLaborCost());
        io.print("Tax: $" + order.getTax());
        io.print("Total: $" + order.getTotal());
        io.print("");
    }
    public void displayErrorMessage(String message) {
        io.print(message + '\n');
        io.readString("Please hit enter to continue.");
    }
    public boolean addConfirmation(){
        String confirm = io.readString("Do you want to place this order? (Y/N) ").toUpperCase();
        return confirm.equals("Y");
    }
    public boolean editConfirmation(){
        String confirm = io.readString("Do you want to edit this order? (Y/N) ").toUpperCase();
        return confirm.equals("Y");
    }

    public boolean deleteConfirmation(){
        String confirm = io.readString("Are you sure you want to delete? (Y/N) ").toUpperCase();
        return confirm.equals("Y");
    }

    public void displayExportAllDataBanner() {
        io.print(" === EXPORT ALL DATA === \n");
    }

    public void displayDeleteSuccessBanner(){io.print(" === Order deleted successfully === ");}
    public void displayUpdateSuccessBanner(){io.print(" === Order updated successfully! === ");}
    public void displaySuccessBanner(){io.print(" === Order added successfully === ");}
    public void displayExportSuccessBanner(){io.print(" === Order added successfully === ");}
    public void displayUnknownCommandBanner() {
        io.print(" === Unknown Command. === ");
    }
    public void displayExitBanner() {
        io.print(" === Good Bye. === ");
    }

    public void displayOrderBanner(){
        io.print(" === DISPLAY ORDER === ");
    }
    public void displayAddBanner(){io.print(" === ADD ORDER === ");}
    public void displayEditBanner(){io.print(" === EDIT ORDER === ");}
    public void displayExportBanner(){io.print(" === EXPORT ORDER === ");}
    public void displayDeleteBanner(){io.print(" === DELETE ORDER === ");}



}
