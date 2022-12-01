package com.ev.flooring.DAO;

import com.ev.flooring.service.FlooringMasteryPersistenceException;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author EverlynLeon
 *
 * */
@Component
public class FlooringMasteryAuditDaoImpl implements FlooringMasteryAuditDao {
    public static final String AUDIT_FILE = "audit.txt";
    public void writeAuditEntry(String entry) throws FlooringMasteryPersistenceException {

        PrintWriter out;
        try{
            out = new PrintWriter(new FileWriter(AUDIT_FILE,true));
        } catch (IOException e){
            throw new FlooringMasteryPersistenceException("Error.");
        }
        LocalDateTime timeStamp = LocalDateTime.now();
        out.println(timeStamp +" : "+ entry);
        out.flush();
    }
}
