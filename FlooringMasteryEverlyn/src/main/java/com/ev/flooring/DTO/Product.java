package com.ev.flooring.DTO;

import com.ev.flooring.service.FlooringMasteryPersistenceException;

import java.math.BigDecimal;
import java.util.regex.PatternSyntaxException;
/**
 *
 * @author EverlynLeon
 *
 * */

public class Product {
    private final String type;
    private final BigDecimal costPerSqFt;
    private final BigDecimal laborCostPerSqFt;
    private final String DELIMITER = ",";

    public Product(String productString) throws FlooringMasteryPersistenceException {
        try{

            String[] token = productString.split(DELIMITER);
            this.type = token[0];
            this.costPerSqFt = new BigDecimal(token[1]);
            this.laborCostPerSqFt = new BigDecimal(token[2]);
        }catch(PatternSyntaxException | NullPointerException | NumberFormatException ex){
            throw new FlooringMasteryPersistenceException(ex.getMessage());
        }
    }
    public String getType(){
        return type;
    }
    public BigDecimal getCostPerSqFt(){
        return costPerSqFt;
    }
    public BigDecimal getLaborCostPerSqFt(){
        return laborCostPerSqFt;
    }
}
