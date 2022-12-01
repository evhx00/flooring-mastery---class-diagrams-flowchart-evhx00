package com.ev.flooring.DTO;

import com.ev.flooring.service.FlooringMasteryPersistenceException;

import java.math.BigDecimal;
import java.util.regex.PatternSyntaxException;
/**
 *
 * @author EverlynLeon
 *
 * */

public class Tax {
    private String stateAbr;
    private String state;
    private BigDecimal tax;
    private final String DELIMITER = ",";

    public Tax(String taxString) throws FlooringMasteryPersistenceException {

        try{
            String[] token = taxString.split(DELIMITER);
            this.stateAbr = token[0];
            this.state = token[1];
            this.tax = new BigDecimal((token[2]));
        }catch(PatternSyntaxException | NullPointerException | NumberFormatException ex){
            throw new FlooringMasteryPersistenceException(ex.getMessage());
        }

    }

    public String getStateAbr(){
        return stateAbr;
    }

    public void setStateAbr(String stateAbr){
        this.stateAbr = stateAbr;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }

    public BigDecimal getTax(){
        return tax;
    }

    public void setTax(BigDecimal tax){
        this.tax = tax;
    }

}
