package com.ev.flooring.service;
/**
 *
 * @author EverlynLeon
 *
 * */

public class FlooringMasteryDataValidationException extends Exception{

    public FlooringMasteryDataValidationException(String message) {
        super(message);
    }

    public FlooringMasteryDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
