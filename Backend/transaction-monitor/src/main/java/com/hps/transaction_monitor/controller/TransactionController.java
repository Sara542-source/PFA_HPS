package com.hps.transaction_monitor.controller;

import com.hps.transaction_monitor.model.DetectionAlert;
import com.hps.transaction_monitor.model.Transaction;
import com.hps.transaction_monitor.storage.DatabaseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final DatabaseStorage databaseStorage;

    @Autowired
    public TransactionController(DatabaseStorage databaseStorage) {
        this.databaseStorage = databaseStorage;
    }

    @GetMapping("/alerts")
    public List<DetectionAlert> getAllAlerts() {
        return databaseStorage.findAllAlerts();
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return databaseStorage.findAllTransactions();
    }
}
