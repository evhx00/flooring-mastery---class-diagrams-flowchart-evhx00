package com.ev.flooring.service;

import com.ev.flooring.DAO.FlooringMasteryDao;
import com.ev.flooring.DAO.FlooringMasteryDaoException;
import com.ev.flooring.DTO.Order;
import com.ev.flooring.DTO.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
/**
 *
 * @author EverlynLeon
 *
 * */

public class FlooringMasteryDaoStubImpl implements FlooringMasteryDao {
    public Order orderTest;
    public Map<LocalDate,Order> filesTest = new HashMap<>();
    public LocalDate dateTest =LocalDate.parse("2022-01-01");
    public BigDecimal taxTest = new BigDecimal("25.00");
    public BigDecimal costPerSqFtTest = new BigDecimal("3.50");
    public Map<String,BigDecimal> taxesTest = new HashMap<>();
    public Map<String,BigDecimal> productsTest = new HashMap<>();
    public FlooringMasteryDaoStubImpl() throws FlooringMasteryPersistenceException {
        orderTest = new Order("1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06");
        filesTest.put(dateTest, orderTest);
        taxesTest.put("CA", taxTest);
        productsTest.put("Tile", costPerSqFtTest);
    }

    public FlooringMasteryDaoStubImpl(Order testOrder){
        this.orderTest = testOrder;
    }
    @Override
    public List<Order> getAllOrders(LocalDate date, Map<Integer,Order> order) throws FlooringMasteryPersistenceException {
        return new ArrayList<>(order.values());
    }
    @Override
    public int addID(LocalDate date) throws IOException, FlooringMasteryPersistenceException {
        int id = 1;

        if (filesTest.containsKey(date)){
            Map<Integer,Order> orders = loadOrders(date);
            List<Order> list = new ArrayList<>();
            list.add(orderTest);
            List<Integer> newList = new ArrayList<>();
            for (Order order: list){
                newList.add(order.getnum());
            }
            Collections.sort(newList);
            id = newList.get(newList.size()-1)+1;
            return id;
        } else {
            return id;
        }
    }

    @Override
    public void createFile(LocalDate date) throws IOException {

    }

    @Override
    public Map<String, Product> loadProducts() throws FlooringMasteryPersistenceException {
        return null;
    }


    @Override
    public Map<LocalDate, String> loadFiles() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        try{
            scanner = new Scanner(
                    new BufferedReader(new FileReader("testFile1.txt")));
        }catch(FileNotFoundException ex){
            throw new FlooringMasteryPersistenceException("Error.", ex);
        }
        String currLine;
        LocalDate date;
        Map<LocalDate,String> result = new HashMap<>();
        while(scanner.hasNextLine()) {
            currLine = scanner.nextLine();
            String[] token = currLine.split(",");
            date = LocalDate.parse(token[0]);
            String fileName = token[1];
            result.put(date, fileName);
        }
        scanner.close();
        return result;
    }

    @Override
    public Map<Integer, Order> loadOrders(LocalDate date) throws FlooringMasteryPersistenceException, IOException {
        Scanner scanner;
        try{
            scanner = new Scanner(
                    new BufferedReader(new FileReader("testOrder.txt")));
        }catch(FileNotFoundException ex){
            throw new FlooringMasteryPersistenceException("Error.", ex);
        }
        String currLine;
        Order currOrder;
        Map<Integer,Order> map = new HashMap<>();
        while(scanner.hasNextLine()) {
            currLine = scanner.nextLine();
            currOrder = new Order(currLine);
            map.put(currOrder.getnum(), currOrder);
        }
        scanner.close();
        return map;
    }

    @Override
    public Map<String, BigDecimal> loadTaxFile() throws FlooringMasteryPersistenceException {
        return taxesTest;
    }

    @Override
    public Order createOrder(int id, String name, String state, String product, BigDecimal area) throws FlooringMasteryPersistenceException {
        if (id == orderTest.getnum()){
            return orderTest;
        }else {
            return null;
        }
    }

    @Override
    public BigDecimal getTaxRate(String state) throws FlooringMasteryPersistenceException {
        return taxesTest.get(state);
    }

    @Override
    public BigDecimal getCostPerSqFt(String product) throws FlooringMasteryPersistenceException {
        if (product.equals(orderTest.getProduct())){
            return orderTest.getLaborCostPerSqft();
        }else{
            return null;
        }
    }

    @Override
    public BigDecimal getLaborCostPerSqFt(String product) throws FlooringMasteryPersistenceException {
        if (product.equals(orderTest.getProduct())){
            return orderTest.getLaborCostPerSqft();
        }else{
            return null;
        }
    }

    @Override
    public BigDecimal getMaterialCost(BigDecimal area, BigDecimal costPerSqFt) {
        if (area.equals(orderTest.getArea())){
            return orderTest.getMaterialCost();
        }else{
            return null;
        }
    }

    @Override
    public BigDecimal getLaborCost(BigDecimal area, BigDecimal laborCostPerSqFt) {
        if (area.equals(orderTest.getArea())){
            return orderTest.getLaborCost();
        }else{
            return null;
        }
    }

    @Override
    public BigDecimal getTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax) {
        if (materialCost.equals(orderTest.getMaterialCost())){
            return orderTest.getTax();
        }else{
            return null;
        }
    }

    @Override
    public void writeOrdersToFile(LocalDate date, Map<Integer, Order> order) throws FlooringMasteryPersistenceException, IOException {
    }

    @Override
    public Map<Integer, Order> addOrderToFile(Integer num, Order order, LocalDate date) throws FlooringMasteryDaoException, FlooringMasteryPersistenceException, IOException {
        Map<Integer,Order> orders = new HashMap<>();
        orders.put(num, order);
        return orders;
    }

    @Override
    public Order updateOrder(Order order, int num, LocalDate date, String name, String state, String product, BigDecimal area) throws FlooringMasteryPersistenceException, IOException {
        return null;
    }

    @Override
    public Order getCurrOrder(int num, LocalDate date) throws IOException, FlooringMasteryPersistenceException {
        if(num == orderTest.getnum()){
            return orderTest;
        } else {
            return null;
        }
    }

    @Override
    public void saveUpdate(Order order, LocalDate date) throws IOException, FlooringMasteryPersistenceException {

    }

    @Override
    public void removeOrder(LocalDate date, int num) throws IOException, FlooringMasteryPersistenceException {

    }


}
