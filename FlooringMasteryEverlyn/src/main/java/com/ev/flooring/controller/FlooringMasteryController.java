package com.ev.flooring.controller;

import com.ev.flooring.DAO.FlooringMasteryDaoException;
import com.ev.flooring.DTO.Order;
import com.ev.flooring.UI.FlooringMasteryView;
import com.ev.flooring.service.FlooringMasteryDataValidationException;
import com.ev.flooring.service.FlooringMasteryPersistenceException;
import com.ev.flooring.service.FlooringMasteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author EverlynLeon
 *
 * */

@Component
public class FlooringMasteryController {
    private final FlooringMasteryView view;
    private final FlooringMasteryService service;

    @Autowired
    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryService service){
        this.view = view;
        this.service = service;
    }
    public void run() throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException, FlooringMasteryDaoException, IOException {
        boolean keepGoing = true;
        int choice = 0;
        try{
            while (keepGoing){

                service.loadFiles();
                    choice = getMenuSelection();

                switch(choice){
                    case 1:

                        LocalDate date;
                        view.displayOrderBanner();
                        try{
                            date = view.getDate();
                            service.validateDate(date);
                            displayOrder(date);
                        } catch (FlooringMasteryDataValidationException | FlooringMasteryPersistenceException e){
                            displayErrorMessage(e.getMessage());
                        }
                        break;

                    case 2:

                        view.displayAddBanner();
                        addOrder();
                        break;

                    case 3:

                        view.displayEditBanner();
                        try{
                            date = view.getDate();
                            service.validateDate(date);
                            editOrder(date);
                        } catch (FlooringMasteryDataValidationException | FlooringMasteryPersistenceException e){
                            displayErrorMessage(e.getMessage());
                        }
                        break;

                    case 4:

                        view.displayDeleteBanner();
                        try{
                            date = view.getDate();
                            service.validateDate(date);
                            removeOrder(date);
                        } catch (FlooringMasteryDataValidationException | FlooringMasteryPersistenceException e){
                            displayErrorMessage(e.getMessage());
                        }
                        break;
                    case 5:

                        view.displayExportBanner();
                        try{
                            date = view.getDate();
                            service.validateDate(date);
                            exportAllData(date);
                        } catch (FlooringMasteryDataValidationException | FlooringMasteryPersistenceException e){
                            displayErrorMessage(e.getMessage());
                        }
                        break;
                    case 6:

                        keepGoing = false;
                        break;

                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch(FlooringMasteryDaoException e){
            throw new FlooringMasteryDaoException("Invalid input.");
        }
    }



    private void displayOrder(LocalDate date) throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException, IOException {

        Map<Integer,Order> map = service.loadOrders(date);
        List<Order> list = service.getAllOrders(date, map);
        view.displaylist(list);

    }

    private void addOrder() throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException, FlooringMasteryDaoException, IOException {

        LocalDate date = getOrderDate();
        int id = service.addID(date);

        String name = view.getCustomerName();
        String state = getOrderState();
        String product = view.getProduct();
        BigDecimal area = getOrderArea();
        Order newOrder = service.createOrder(id, name, state, product, area);

        view.displaySummary(newOrder);
        if(view.addConfirmation()){
            Map<Integer,Order> orders = service.saveOrderToFile(id, newOrder, date);
            service.writeOrdersToFile(date, orders);
            view.displaySuccessBanner();
        }
    }

    private LocalDate getOrderDate() throws FlooringMasteryDataValidationException {

        boolean isValid = false;
        LocalDate date = LocalDate.now();

        while(!isValid){
            date = view.getDate();
            if (service.checkDateAfter(date)){
                isValid = true;
            } else {
                view.displayErrorMessage("Invalid input.");
            }
        }
        return date;
    }

    private String getOrderState() throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {

        boolean isValid = false;
        String state = "";
        while(!isValid){
            state = view.getState().toUpperCase();
            if(service.isTaxable(state)) {
                isValid = true;
            }else{
                view.displayErrorMessage("Invalid input. ");
            }
        }
        return state;
    }
    private String getUpdatedOrderState() throws FlooringMasteryDataValidationException, FlooringMasteryPersistenceException {

        boolean isValid = false;
        String state = "";
        while(!isValid){
            state = view.getUpdatedState().toUpperCase();
            if(service.isTaxable(state)|| state.equals("")) {
                isValid = true;
            }else{
                view.displayErrorMessage("Invalid input. ");
            }
        }
        return state;
    }

    private BigDecimal getOrderArea() throws FlooringMasteryDataValidationException {

        boolean isValid = false;
        BigDecimal area = new BigDecimal(0);
        while(!isValid){
            area = view.getArea();
            if(service.validateArea(area)) {
                isValid = true;
            }else {
                view.displayErrorMessage("Invalid input. ");
            }
        }
        return area;
    }
    private BigDecimal getUpdatedOrderArea() throws FlooringMasteryDataValidationException {

        boolean isValid = false;
        BigDecimal newArea = new BigDecimal(0);
            while (!isValid) {
                String area = view.getUpdatedArea();
                if (area.equals("")){
                    return BigDecimal.valueOf(0);
                } else{
                    newArea = new BigDecimal(area);
                    if (service.validateArea(newArea)) {
                        isValid = true;
                    } else {
                        view.displayErrorMessage("Invalid input. ");
                    }
                }
            }
            return newArea;
    }

    private void editOrder(LocalDate date) throws FlooringMasteryDataValidationException, IOException, FlooringMasteryPersistenceException {
        int num = view.getnumber();
        Order currOrder = service.getCurrOrder(num, date);
        String name = view.getUpdatedCustomerName(currOrder.getCustomerName());
        String state = getUpdatedOrderState();
        String product = view.getUpdatedproduct();
        BigDecimal area = getUpdatedOrderArea();
        Order newOrder = service.updateOrder(currOrder, num, date, name, state, product, area);
        view.displayNewOrder(newOrder);

        if (view.editConfirmation()){
            service.saveUpdate(newOrder, date);
            view.displayUpdateSuccessBanner();
        }
    }
    private void removeOrder(LocalDate date) throws FlooringMasteryDataValidationException, IOException, FlooringMasteryPersistenceException {
        int num = view.getnumber();
        Order currOrder = service.getCurrOrder(num, date);
        if(!service.validateID(num, date)){
            throw new FlooringMasteryPersistenceException("Error. ");
        }
        view.displaySummary(currOrder);
        if (view.deleteConfirmation()){
            service.removeOrder(date, num);
            view.displayDeleteSuccessBanner();
        }
    }
    private void exportAllData(LocalDate date) throws FlooringMasteryPersistenceException, IOException, FlooringMasteryDataValidationException {
        Map<Integer,Order> map = service.loadOrders(date);
        view.displayExportAllDataBanner();
        service.exportAllData(date,map);
        view.displayExportSuccessBanner();
    }
    public int getMenuSelection(){
        return view.displayMainMenuAndGetSelection();
    }

    private void exitMessage(){
        view.displayExitBanner();
    }
    void displayErrorMessage(String message){
        view.displayErrorMessage(message);
    }

    private void unknownCommand(){
        view.displayUnknownCommandBanner();
    }
}
