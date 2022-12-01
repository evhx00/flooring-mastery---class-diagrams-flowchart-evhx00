package com.ev.flooring.service;

import com.ev.flooring.DAO.FlooringMasteryAuditDao;
import com.ev.flooring.DAO.FlooringMasteryDao;
import com.ev.flooring.DAO.FlooringMasteryDaoException;
import com.ev.flooring.DTO.Order;
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
public class FlooringMasteryServiceImpl implements FlooringMasteryService {
    FlooringMasteryDao dao;
    FlooringMasteryAuditDao auditDao;

    @Autowired
    public FlooringMasteryServiceImpl(FlooringMasteryDao dao, FlooringMasteryAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }
    @Override
    public int addID(LocalDate date) throws IOException, FlooringMasteryPersistenceException {
        return dao.addID(date);
    }
    @Override
    public Order updateOrder(Order order, int num, LocalDate date, String name, String state,
                             String product, BigDecimal area) throws FlooringMasteryPersistenceException, IOException{
        return dao.updateOrder(order, num, date, name, state, product, area);
    }
    @Override
    public Order getCurrOrder(int num, LocalDate date) throws IOException, FlooringMasteryPersistenceException {
        return dao.getCurrOrder(num, date);
    }
    @Override
    public void saveUpdate(Order order, LocalDate date) throws IOException, FlooringMasteryPersistenceException {
        dao.saveUpdate(order, date);
        auditDao.writeAuditEntry("Order number " + order.getnum() + " for " + date.toString() + " updated.");
    }
    @Override
    public void removeOrder(LocalDate date, int num) throws IOException, FlooringMasteryPersistenceException {
        dao.removeOrder(date, num);
        auditDao.writeAuditEntry("Order number " + num + " for "+ date.toString() + " deleted.");
    }

    @Override
    public void validateDate(LocalDate date) throws FlooringMasteryDataValidationException, IOException, FlooringMasteryPersistenceException {
        try {
            dao.loadOrders(date);
        } catch (FlooringMasteryPersistenceException e) {
            throw new FlooringMasteryPersistenceException("Error.");
        }
    }

    @Override
    public void exportAllData(LocalDate date, Map<Integer, Order> order) throws FlooringMasteryPersistenceException, IOException, FlooringMasteryDataValidationException {
        Map<Integer, Order> map = loadOrders(date);
        dao.writeOrdersToFile(date, map);
    }

    @Override
    public boolean validateID(int num, LocalDate date) throws IOException, FlooringMasteryPersistenceException {
        Map<Integer, Order> map = dao.loadOrders(date);
        return map.containsKey(num);
    }
    public boolean checkDateAfter(LocalDate date) throws FlooringMasteryDataValidationException {
        return date.isAfter(LocalDate.now());
    }
    @Override
    public List<Order> getAllOrders(LocalDate date,Map<Integer,Order> map) throws FlooringMasteryPersistenceException, FlooringMasteryDataValidationException, IOException {
        return dao.getAllOrders(date, map);
    }
    @Override
    public boolean isTaxable(String state) throws FlooringMasteryPersistenceException {
        Map<String, BigDecimal> tax;
        tax = dao.loadTaxFile();
        return tax.containsKey(state);
    }
    public boolean validateArea(BigDecimal area) throws FlooringMasteryDataValidationException {
        BigDecimal minimum = new BigDecimal(200);
        return area.compareTo(minimum) >= 0;
    }
    public Order createOrder(int id, String name, String state, String product, BigDecimal area) throws FlooringMasteryPersistenceException {
        return dao.createOrder(id, name, state, product, area);
    }
    @Override
    public Map<Integer,Order> saveOrderToFile(Integer num, Order order, LocalDate date) throws FlooringMasteryDaoException, FlooringMasteryPersistenceException, IOException {
        auditDao.writeAuditEntry("Order number " + num + " for " + date.toString() + " added.");
        return dao.addOrderToFile(num, order, date);
    }
    public void writeOrdersToFile(LocalDate date, Map<Integer,Order> order) throws FlooringMasteryPersistenceException, IOException{
        dao.writeOrdersToFile(date, order);
    }
    @Override
    public Map<LocalDate, String> loadFiles() throws FlooringMasteryPersistenceException {
        return dao.loadFiles();
    }
    @Override
    public Map<Integer, Order> loadOrders(LocalDate date) throws FlooringMasteryPersistenceException, IOException {
        return dao.loadOrders(date);
    }

}

