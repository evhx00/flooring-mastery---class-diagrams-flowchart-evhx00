package com.ev.flooring.DAO;

import com.ev.flooring.service.FlooringMasteryPersistenceException;
/**
 *
 * @author EverlynLeon
 *
 * */

public interface FlooringMasteryAuditDao {
    void writeAuditEntry(String entry) throws FlooringMasteryPersistenceException;
}
