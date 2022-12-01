package com.ev.flooring.service;

import com.ev.flooring.DAO.FlooringMasteryDaoException;
import com.ev.flooring.DTO.Order;

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
public interface FlooringMasteryService {
    int addID(LocalDate date) throws IOException, FlooringMasteryPersistenceException;

    boolean checkDateAfter(LocalDate date) throws FlooringMasteryDataValidationException;

    boolean isTaxable(String state) throws FlooringMasteryPersistenceException;

    boolean validateArea(BigDecimal area) throws FlooringMasteryDataValidationException;

    void validateDate(LocalDate date) throws FlooringMasteryDataValidationException, IOException, FlooringMasteryPersistenceException;

    void exportAllData(LocalDate date, Map<Integer, Order> order) throws FlooringMasteryPersistenceException, IOException, FlooringMasteryDataValidationException;

    boolean validateID(int num, LocalDate date) throws IOException, FlooringMasteryPersistenceException;
    List<Order> getAllOrders(LocalDate date, Map<Integer,Order> map) throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException, IOException;
    Map<LocalDate, String> loadFiles() throws FlooringMasteryPersistenceException;
    Map<Integer, Order> loadOrders(LocalDate date) throws FlooringMasteryPersistenceException, IOException;
    Order createOrder(int id, String name, String state, String product, BigDecimal area) throws FlooringMasteryPersistenceException;
    Map<Integer,Order> saveOrderToFile(Integer num, Order order, LocalDate date) throws FlooringMasteryDaoException, FlooringMasteryPersistenceException, IOException;
    void writeOrdersToFile(LocalDate date, Map<Integer,Order> order) throws FlooringMasteryPersistenceException, IOException;
    Order updateOrder(Order order, int num, LocalDate date, String name, String state,
                             String product, BigDecimal area) throws FlooringMasteryPersistenceException, IOException;
    Order getCurrOrder(int num, LocalDate date) throws IOException, FlooringMasteryPersistenceException;
    void saveUpdate(Order order, LocalDate date) throws IOException, FlooringMasteryPersistenceException;
    void removeOrder(LocalDate date, int num) throws IOException, FlooringMasteryPersistenceException;
}
