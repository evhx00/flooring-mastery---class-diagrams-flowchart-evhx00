package com.ev.flooring.dao;

import com.ev.flooring.DAO.FlooringMasteryDao;
import com.ev.flooring.DAO.FlooringMasteryDaoImpl;
import com.ev.flooring.DTO.Order;
import com.ev.flooring.service.FlooringMasteryDaoStubImpl;
import com.ev.flooring.service.FlooringMasteryPersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author EverlynLeon
 *
 * */

public class FlooringMasteryDaoImplTest {
    String testFile = "testOrder.txt";
    String currFile = "testFile1.txt";
    FlooringMasteryDao testDao = new FlooringMasteryDaoImpl(testFile,currFile);
    FlooringMasteryDao stubDao = new FlooringMasteryDaoStubImpl();

    public FlooringMasteryDaoImplTest() throws FlooringMasteryPersistenceException {

    }
    @BeforeEach
    public void setUp() throws Exception{
    }
    @Test
    public void testgetAllOrders() throws Exception{
        System.out.println("getAllOrders");
        Order testOrders = new Order("1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06");
        Map<Integer,Order> map = new HashMap<>();
        map.put(1,testOrders);
        LocalDate date = LocalDate.parse("2022-11-29");
        List<Order> result = stubDao.getAllOrders(date,map);
        List<Order> requiredResult = new ArrayList<>();
        requiredResult.add(testOrders);
        assertEquals(requiredResult, result, "Test correct list");
    }
    @Test
    public void testaddID() throws Exception{
        System.out.println("addID");
        LocalDate date = LocalDate.parse("2022-11-29");
        int result = stubDao.addID(date);
        int requiredResult = 1;
        assertEquals(requiredResult,result, "Test add id");
    }
    @Test
    public void testLoadFiles() throws FlooringMasteryPersistenceException {
        System.out.println("loadFiles");
        LocalDate date = LocalDate.parse("2022-11-29");
        String orderName = "testOrders";
        Map<LocalDate,String> requiredResult = new HashMap<>();
        Map<LocalDate,String> result = stubDao.loadFiles();
        requiredResult.put(date,orderName);
        assertEquals(requiredResult,result,"Test loaded file");
        assertTrue(result.containsKey(date),"Contains correct key");
        assertEquals(result.size(),1,"Should have size 1");
    }
    @Test
    public void testloadOrders() throws Exception {
        System.out.println("loadOrders");
        LocalDate date = LocalDate.parse("2022-01-01");
        int id = 1;
        Order testOrders = new Order("1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06");
        Map<Integer,Order> requiredResult = new HashMap<>();
        requiredResult.put(id,testOrders);
        Map<Integer,Order> result = stubDao.loadOrders(date);
        assertEquals(requiredResult,result,"Test loaded file");
        assertTrue(result.containsKey(1), "Contains correct key");
        assertEquals(result.size(),1,"Should have size 1");
    }

    @Test
    public void testGetMaterialCost() throws Exception{
        System.out.println("getMaterialCost");
        BigDecimal area = new BigDecimal(249);
        BigDecimal costPerSqFt = new BigDecimal("4.15");
        BigDecimal requiredResult = area.multiply(costPerSqFt);
        requiredResult = requiredResult.setScale(1, RoundingMode.HALF_UP);
        BigDecimal result = testDao.getMaterialCost(area,costPerSqFt);
        assertEquals(requiredResult,result,"Test getMaterialCostPerSqFt");
    }

    @Test
    public void testGetTax() throws Exception{
        System.out.println("getTax");
        BigDecimal materialCost = new BigDecimal("871.5");
        BigDecimal laborCost = new BigDecimal("1033.35");
        BigDecimal taxRate = new BigDecimal("25.00");
        BigDecimal requiredResult = new BigDecimal("476.21");
        requiredResult = requiredResult.setScale(0,RoundingMode.HALF_UP);
        BigDecimal result = testDao.getTax(materialCost, laborCost, taxRate);
        assertEquals(requiredResult,result,"Test getTax.");
    }
    @Test
    public void testAddOrderToFile() throws Exception{
        int num = 1;
        LocalDate date = LocalDate.parse("2022-11-29");
        Order testOrders = new Order("1,Ada Lovelace,CA,25.00,Tile,249,3.50,4.15,871.50,1033.35,476,2380.85");
        Map<Integer,Order> requiredResult = new HashMap<>();
        requiredResult.put(1, testOrders);
        Map<Integer, Order> result = stubDao.addOrderToFile(num, testOrders, date);
        assertEquals(requiredResult,result,"Test addOrderToFile");
        assertNotNull(result,"Check not null");
        assertEquals(result.size(),1,"Check order");
    }
    @Test
    public void testUpdateOrder() throws Exception {
        System.out.println("updateOrder");
       Order testOrders =  new Order("1,Ada Lovelace,CA,25.00,Tile,249,3.50,4.15,871.50,1033.35,476,2380.85");
       int num = 1;
       LocalDate date = LocalDate.parse("2020-01-01");
       String name = "Everlyn Leon";
       String state = "";
       String product = "";
       BigDecimal area = new BigDecimal(0);
       Order requiredResult = new Order("1,Everlyn Leon,CA,25.00,Tile,249,3.50,4.15,871.50,1033.35,476,2380.85");
       Order result = testDao.updateOrder(testOrders,1,date,name,state,product,area);
       assertEquals(requiredResult,result,"Test updateOrder.");
    }
    @Test
    public void testGetCurrOrder() throws Exception{
        System.out.println("getCurrOrder");
        int num=1;
        LocalDate date = LocalDate.parse("2022-11-29");
        Order requiredResult = new Order("1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06");
        Order result = stubDao.getCurrOrder(num,date);
        assertEquals(requiredResult,result,"Test getCurrOrder.");
    }
}
