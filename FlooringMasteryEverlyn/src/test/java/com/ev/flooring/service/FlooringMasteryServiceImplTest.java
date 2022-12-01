package com.ev.flooring.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *
 * @author EverlynLeon
 *
 * */

public class FlooringMasteryServiceImplTest {
    FlooringMasteryService testService;
    public FlooringMasteryServiceImplTest(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testService = ctx.getBean("serviceLayer", FlooringMasteryService.class);
    }

    @Test
    public void validateDate() throws IOException, FlooringMasteryPersistenceException, FlooringMasteryDataValidationException {
     try{
         System.out.println("validateDate");
         LocalDate date = LocalDate.parse("2022-01-01");
         testService.validateDate(date);
     }catch (FlooringMasteryDataValidationException ex){
         fail("No exception should be thrown");
     }
    }
    @Test
    public void testvalidateID() throws IOException, FlooringMasteryPersistenceException {
        try{
            System.out.println("validateID");
            LocalDate date = LocalDate.parse("2022-01-01");
            int id = 1;
            testService.validateID(id,date);
        }catch(FlooringMasteryPersistenceException ex){
            fail("No exception should be thrown");
        }
    }
    @Test
    public void testCheckDateAfter() throws FlooringMasteryDataValidationException {
        try{
            System.out.println("checkDateAfter");
            LocalDate date = LocalDate.parse("2023-01-01");
            boolean isAfter = testService.checkDateAfter(date);
            assertTrue(isAfter,"Test if valid.");
        }catch(FlooringMasteryDataValidationException ex){
            fail("No exception should be thrown");
        }
    }
    @Test
    public void testIsTaxable() throws FlooringMasteryPersistenceException {
        try{
            System.out.println("isTaxable");
            String state = "CA";
            boolean isTaxable = testService.isTaxable(state);
            assertTrue(isTaxable,"Test if valid");
        }catch(FlooringMasteryPersistenceException ex){
            fail("No exception should be thrown");
        }
    }
    @Test
    public void testValidArea() throws FlooringMasteryDataValidationException {
        System.out.println("validArea");
        BigDecimal area = new BigDecimal(250);
        boolean isValidArea = testService.validateArea(area);
        assertTrue(isValidArea,"Test if valid");
    }
}
