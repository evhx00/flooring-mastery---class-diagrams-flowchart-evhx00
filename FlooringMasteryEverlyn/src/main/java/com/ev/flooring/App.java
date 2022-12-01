package com.ev.flooring;


import com.ev.flooring.DAO.FlooringMasteryDaoException;
import com.ev.flooring.controller.FlooringMasteryController;
import com.ev.flooring.service.FlooringMasteryDataValidationException;
import com.ev.flooring.service.FlooringMasteryPersistenceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


/**
 *
 * @author EverlynLeon
 *
 * */

public class App {
    public static void main(String[] args) throws FlooringMasteryDataValidationException,
            FlooringMasteryPersistenceException, FlooringMasteryDaoException, IOException {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        FlooringMasteryController controller = appContext.getBean("controller", FlooringMasteryController.class);
        controller.run();
    }
}