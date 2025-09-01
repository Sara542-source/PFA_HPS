package com.hps.transaction_monitor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_info")
public class TransactionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private String esecureId;
    private String transactionId;
    private String authenticationStep;
    //@JsonProperty("")
    //private String details;
    private String issuerName;
    private String bin;
    private String product;
    private String bank;
    private String network;
    private String authMethod;
    private String merchantName;
    private LocalDateTime date;


    public String getAuthenticationStep() {
        return authenticationStep;
    }

    public void setAuthenticationStep(String authenticationStep) {
        this.authenticationStep = authenticationStep;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    //public String getDetails() {
      //  return details;
    //}

   // public void setDetails(String details) {
        //this.details = details;
   // }

    public String getEsecureId() {
        return esecureId;
    }

    public void setEsecureId(String esecureId) {
        this.esecureId = esecureId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
