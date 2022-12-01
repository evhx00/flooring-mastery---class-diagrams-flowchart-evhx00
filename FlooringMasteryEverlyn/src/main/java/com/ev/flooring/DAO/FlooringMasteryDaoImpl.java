package com.ev.flooring.DAO;

import com.ev.flooring.DTO.Order;
import com.ev.flooring.DTO.Product;
import com.ev.flooring.DTO.Tax;
import com.ev.flooring.service.FlooringMasteryPersistenceException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 *
 * @author EverlynLeon
 *
 * */

@Component
public class FlooringMasteryDaoImpl implements FlooringMasteryDao {
    private final Map<LocalDate, String> files = new HashMap<>();
    private final Map<String, BigDecimal> taxes = new HashMap<>();
    private final Map<String, Product> products = new HashMap<>();

    private String filePath = "C:\\Users\\eveel\\OneDrive\\Documents\\Computer_Science_Work\\Work\\WIT\\flooring-mastery---class-diagrams-flowchart-evhx00\\";

    public String orderFiles;
    public String currFiles;

    public FlooringMasteryDaoImpl(){
        orderFiles = "";
    }
    public FlooringMasteryDaoImpl(String orderFile, String currFile){
        orderFiles = orderFile;
        currFiles = currFile;
    }
    @Override
    public List<Order> getAllOrders(LocalDate date, Map<Integer,Order> order) throws FlooringMasteryPersistenceException, IOException {

        if (!files.containsKey(date)){
            throw new FlooringMasteryPersistenceException("Error.");
        }
        return new ArrayList<>(order.values());
    }
    @Override
    public Map<Integer,Order> addOrderToFile(Integer num, Order order, LocalDate date) throws FlooringMasteryDaoException, FlooringMasteryPersistenceException, IOException {

        loadFiles();
        Map<Integer,Order> orders = loadOrders(date);
        orders.put(num, order);
        return orders;
    }
    @Override
    public void writeOrdersToFile(LocalDate date,Map<Integer,Order> order) throws FlooringMasteryPersistenceException, IOException {

        PrintWriter out;
        try{
            orderFiles = files.get(date);
            out = new PrintWriter(new FileWriter(filePath + orderFiles));
        }catch (IOException ex){
            throw new FlooringMasteryPersistenceException("Error.", ex);
        }
        String orderAsText;
        List<Order> list = getAllOrders(date, order);

        for (Order currentOrder : list){
            orderAsText = currentOrder.marshalOrderAsText();
            out.println(orderAsText);
            out.flush();
        }
        out.close();
    }

    @Override
    public Map<Integer, Order> loadOrders(LocalDate date) throws FlooringMasteryPersistenceException, IOException {
        Map<Integer, Order> orders = new HashMap<>();
        if (!files.containsKey(date)){
            createFile(date);
            orders.clear();
        }
        orderFiles = files.get(date);
        Scanner scanner;
        try{
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(filePath + orderFiles)));
        }catch(FileNotFoundException ex){
            throw new FlooringMasteryPersistenceException("Error.", ex);
        }
        String currentLine;
        Order currentOrder;
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = new Order(currentLine);
            orders.put(currentOrder.getnum(), currentOrder);
        }
        scanner.close();
        return orders;
    }

    @Override
    public int addID(LocalDate date) throws IOException, FlooringMasteryPersistenceException {

        int id = 1;
        if (files.containsKey(date)){
            Map<Integer,Order> orders = loadOrders(date);
            List<Order> list = this.getAllOrders(date,orders);
            List<Integer> idList = new ArrayList<>();
            for (Order order: list){
                idList.add(order.getnum());
            }
            Collections.sort(idList);
            id = idList.get(idList.size()-1)+1;
            return id;
        } else {
            return id;
        }
    }
    @Override
    public Map<LocalDate, String> loadFiles() throws FlooringMasteryPersistenceException {

        Scanner scanner;
        try{
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader("files.txt")));
        }catch(FileNotFoundException ex){
            throw new FlooringMasteryPersistenceException(".", ex);
        }
        String currentLine;
        LocalDate date;
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            String[] token = currentLine.split(",");
            date = LocalDate.parse(token[0]);
            String fileName = token[1];
            files.put(date, fileName);
        }
        scanner.close();
        return files;
    }
    @Override
    public void createFile(LocalDate date) throws IOException {

        String dateString = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        String fileName = "orders_" + dateString + ".txt";
        files.put(date, fileName);
        File newFile = new File(filePath + fileName);
        newFile.createNewFile();
        PrintWriter writer = new PrintWriter(new FileWriter("files.txt"));
        for (LocalDate dateKey : files.keySet()){
            String fileString = dateKey + "," + files.get(dateKey);
            writer.println(fileString);
            writer.flush();
        }
        writer.close();
    }

    @Override
    public Map<String, BigDecimal> loadTaxFile() throws FlooringMasteryPersistenceException {

        orderFiles = "taxes.txt";
        Scanner scanner;
        try{
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(orderFiles)));
        }catch(FileNotFoundException ex){
            throw new FlooringMasteryPersistenceException("Error.", ex);
        }
        String currentLine;
        Tax currentTax;
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = new Tax(currentLine);
            taxes.put(currentTax.getStateAbr(), currentTax.getTax());
        }
        scanner.close();
        return taxes;

    }
    public Order createOrder(int id, String name, String state, String product, BigDecimal area) throws FlooringMasteryPersistenceException {

        Order order = new Order(id);
        order.setCustomerName(name);
        order.setState(state);
        order.setProduct(product);
        order.setArea(area);

        BigDecimal taxRate = getTaxRate(state);
        order.setTaxRate(taxRate);

        BigDecimal costPerSqFt = getCostPerSqFt(product);
        order.setCostPerSqft(costPerSqFt);
        order.setLaborCostPerSqft(getLaborCostPerSqFt(product));
        BigDecimal materialCost = getMaterialCost(area, costPerSqFt);
        order.setMaterialCost(materialCost);

        BigDecimal laborCost = getLaborCost(area, getLaborCostPerSqFt(product));
        order.setLaborCost(laborCost);

        BigDecimal tax =getTax(materialCost, laborCost, taxRate);
        order.setTax(tax);
        order.setTotal(materialCost.add(laborCost).add(tax));
        return order;
    }
    @Override
    public BigDecimal getTaxRate(String state) throws FlooringMasteryPersistenceException {

        Map<String, BigDecimal> taxes = new HashMap<>();
        taxes = loadTaxFile();
        return taxes.get(state);
    }
    @Override
    public Map<String, Product> loadProducts() throws FlooringMasteryPersistenceException {

        orderFiles = "products.txt";
        Scanner scanner;
        try{
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(orderFiles)));
        }catch(FileNotFoundException ex){
            throw new FlooringMasteryPersistenceException("Error.", ex);
        }
        String currentLine;
        Product currentProduct;
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = new Product(currentLine);
            products.put(currentProduct.getType(), currentProduct);
        }
        scanner.close();
        return products;
    }

    @Override
    public BigDecimal getCostPerSqFt(String product) throws FlooringMasteryPersistenceException {

        Map<String, Product> allproducts = new HashMap<>();
        allproducts = loadProducts();
        Product products = allproducts.get(product);
        BigDecimal costPerSqFt = products.getCostPerSqFt();
        return costPerSqFt;

    }
    @Override
    public BigDecimal getLaborCostPerSqFt(String product) throws FlooringMasteryPersistenceException {

        Map<String, Product> allproducts = new HashMap<>();
        allproducts = loadProducts();
        Product products = allproducts.get(product);
        BigDecimal laborCostPerSqFt = products.getLaborCostPerSqFt();
        return laborCostPerSqFt;
    }
    @Override
    public BigDecimal getMaterialCost(BigDecimal area, BigDecimal costPerSqFt){

        BigDecimal materialCost = area.multiply(costPerSqFt);
        return materialCost;
    }
    @Override
    public BigDecimal getLaborCost(BigDecimal area, BigDecimal laborCostPerSqFt){

        BigDecimal laborCost = area.multiply(laborCostPerSqFt);
        laborCost = laborCost.setScale(2,RoundingMode.HALF_UP);
        return laborCost;
    }
    @Override
    public BigDecimal getTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate){

        BigDecimal tax = (materialCost.add(laborCost)).multiply(taxRate.divide(BigDecimal.valueOf(100)));
        tax=tax.setScale(0, RoundingMode.HALF_UP);
        return tax;
    }

    @Override
    public Order getCurrOrder(int num, LocalDate date) throws IOException, FlooringMasteryPersistenceException {

        Map<Integer, Order> map = loadOrders(date);
        return map.get(num);
    }
    @Override
    public void saveUpdate(Order order, LocalDate date) throws IOException, FlooringMasteryPersistenceException {

        Map<Integer, Order> map = loadOrders(date);
        map.put(order.getnum(), order);
        writeOrdersToFile(date,map);
    }
    @Override
    public Order updateOrder(Order order, int num, LocalDate date, String name, String state,
                             String product, BigDecimal area) throws FlooringMasteryPersistenceException, IOException {

        Order newOrder = new Order(num);

        if (!name.equals("")){
            newOrder.setCustomerName(name);
        }else{
            newOrder.setCustomerName(order.getCustomerName());
        }
        if (!state.equals("")){
            newOrder.setState(state);
            newOrder.setTaxRate(getTaxRate(state));
        } else {
            newOrder.setState(order.getState());
            newOrder.setTaxRate(order.getTaxRate());
        }
        if (!product.equals("")){
            newOrder.setProduct(product);
            newOrder.setCostPerSqft(getCostPerSqFt(product));
            newOrder.setLaborCostPerSqft(getLaborCostPerSqFt(product));
        } else {
            newOrder.setProduct(order.getProduct());
            newOrder.setCostPerSqft(order.getCostPerSqft());
            newOrder.setLaborCostPerSqft(order.getLaborCostPerSqft());
        }
        if(area.compareTo(BigDecimal.ZERO) == 0){
            newOrder.setArea(order.getArea());
        } else {
            newOrder.setArea(area);
        }
        newOrder.setMaterialCost(getMaterialCost(newOrder.getArea(),newOrder.getCostPerSqft()));
        newOrder.setLaborCost(getLaborCost(newOrder.getArea(), newOrder.getLaborCostPerSqft()));
        newOrder.setTax(getTax(newOrder.getMaterialCost(), newOrder.getLaborCost(), newOrder.getTaxRate()));
        newOrder.setTotal(newOrder.getMaterialCost().add(newOrder.getLaborCost()).add(newOrder.getTax()));
        return newOrder;
    }
    @Override
    public void removeOrder(LocalDate date, int num) throws IOException, FlooringMasteryPersistenceException {

        Map<Integer, Order> map = loadOrders(date);
        map.remove(num);
        writeOrdersToFile(date,map);
    }

}
