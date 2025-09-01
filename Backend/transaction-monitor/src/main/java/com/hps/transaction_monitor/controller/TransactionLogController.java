package com.hps.transaction_monitor.controller;

import com.hps.transaction_monitor.model.TransactionSummary;
import com.hps.transaction_monitor.service.TransactionService;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@ComponentScan
@RequestMapping("/api/logs")
public class TransactionLogController {
    private final TransactionService transactionService;
@Autowired
    public TransactionLogController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionSummary>> getTransactionSummary(@RequestParam String index, @RequestParam(defaultValue = "0")@PositiveOrZero Long lastTimeStamp) {
        try {
            List<TransactionSummary> summaries = transactionService.getTransactionSummeries(index, lastTimeStamp);
            return ResponseEntity.ok(summaries);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        }
    @GetMapping("/test")
    public String test() {
        return "Application is running!";
    }
}
