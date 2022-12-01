package com.ev.flooring.DAO;

import com.ev.flooring.DTO.Order;
import com.ev.flooring.DTO.Product;
import com.ev.flooring.service.FlooringMasteryPersistenceException;

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

public interface FlooringMasteryDao {

    void createFile(LocalDate date) throws IOException;

    int addID(LocalDate date) throws IOException, FlooringMasteryPersistenceException;
    Map<String, Product> loadProducts() throws FlooringMasteryPersistenceException;

    Map<LocalDate, String> loadFiles() throws FlooringMasteryPersistenceException;

    Map<Integer, Order> loadOrders(LocalDate date) throws FlooringMasteryPersistenceException, IOException;

    void writeOrdersToFile(LocalDate date,Map<Integer,Order> order) throws FlooringMasteryPersistenceException, IOException;


    List<Order> getAllOrders(LocalDate date,Map<Integer,Order> order) throws FlooringMasteryPersistenceException, IOException;

    Order createOrder(int id, String name, String state, String product, BigDecimal area) throws FlooringMasteryPersistenceException;

    Map<Integer,Order> addOrderToFile(Integer num, Order order, LocalDate date) throws FlooringMasteryDaoException, FlooringMasteryPersistenceException, IOException;

    Order updateOrder(Order order, int num, LocalDate date, String name, String state,
                             String product, BigDecimal area) throws FlooringMasteryPersistenceException, IOException;

    void removeOrder(LocalDate date, int num) throws IOException, FlooringMasteryPersistenceException;

    Order getCurrOrder(int num, LocalDate date) throws IOException, FlooringMasteryPersistenceException;

    void saveUpdate(Order order, LocalDate date) throws IOException, FlooringMasteryPersistenceException;

    Map<String, BigDecimal> loadTaxFile() throws FlooringMasteryPersistenceException;

    BigDecimal getTaxRate(String state) throws FlooringMasteryPersistenceException;


    BigDecimal getCostPerSqFt(String product) throws FlooringMasteryPersistenceException;

    BigDecimal getLaborCostPerSqFt(String product) throws FlooringMasteryPersistenceException;


    BigDecimal getMaterialCost(BigDecimal area, BigDecimal costPerSqFt);

    BigDecimal getLaborCost(BigDecimal area, BigDecimal laborCostPerSqFt);

    BigDecimal getTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax);


}
