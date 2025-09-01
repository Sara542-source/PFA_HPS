package com.hps.transaction_monitor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
//d’un DTO/POJO TransactionSummary vide qui va recevoir les champs extraits du JSON.
import java.time.ZonedDateTime;
//est une classe de modèle (ou DTO) qui représente les données des logs de transactions récupérés depuis Elasticsearch de manière structurée et typée.
@Component
public class TransactionSummary {
    @JsonProperty("transaction_id")
    public String transactionId = null;
    //@JsonProperty("operation_code")
    //public String operationCode;
    @JsonProperty("@timestamp")
    //Used for @timestamp to handle the ISO 8601 timestamp format (e.g., 2025-07-23T10:21:42.906Z) from the logs
    public ZonedDateTime timestamp = null;
    @JsonProperty("auth_step")
    public String authStep = null;
    @JsonProperty("CREQ_RECEPTION_ms")
    private Double creqReceptionMs= null;

    @JsonProperty("CHALLENGE_DISPLAY_ms")
    private Double challengeDisplayMs;

    @JsonProperty("RREQ_SENDING_ms")
    private Double rreqSendingMs;





    public String getAuthStep() {
        return authStep;
    }

    public void setAuthStep(String authStep) {
        this.authStep = authStep;
    }



    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

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

    public Double getRreqSendingMs() {
        return rreqSendingMs;
    }

    public void setRreqSendingMs(Double rreqSendingMs) {
        this.rreqSendingMs = rreqSendingMs;
    }

    public TransactionSummary(String authStep, ZonedDateTime timestamp, String transactionId, Double rreqSendingMs, Double challengeDisplayMs, Double creqReceptionMs) {
        this.authStep = authStep;
        this.timestamp = timestamp;
        this.transactionId = transactionId;
        this.rreqSendingMs = rreqSendingMs;
        this.challengeDisplayMs = challengeDisplayMs;
        this.creqReceptionMs = creqReceptionMs;
    }
    public TransactionSummary() {}
}

