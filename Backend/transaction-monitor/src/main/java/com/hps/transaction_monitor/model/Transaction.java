package com.hps.transaction_monitor.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "creq_reception_ms")
    private Double creqReceptionMs;

    @Column(name = "challenge_display_ms")
    private Double challengeDisplayMs;

    @Column(name = "rreq_sending_ms")
    private Double rreqSendingMs;

    public Double getChallengeDisplayMs() {
        return challengeDisplayMs;
    }

    public void setChallengeDisplayMs(Double challengeDisplayMs) {
        this.challengeDisplayMs = challengeDisplayMs;
    }

    public Double getCreqReceptionMs() {
        return creqReceptionMs;
    }

    public void setCreqReceptionMs(Double creqReceptionMs) {
        this.creqReceptionMs = creqReceptionMs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRreqSendingMs() {
        return rreqSendingMs;
    }

    public void setRreqSendingMs(Double rreqSendingMs) {
        this.rreqSendingMs = rreqSendingMs;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
