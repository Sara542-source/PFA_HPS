package com.hps.transaction_monitor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hps.transaction_monitor.model.DetectionAlert;
import com.hps.transaction_monitor.model.Transaction;
import com.hps.transaction_monitor.model.TransactionSummary;
import com.hps.transaction_monitor.storage.DatabaseStorage;
import com.hps.transaction_monitor.storage.ElasticsearchStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final ElasticsearchStorage elasticsearchStorage1;
    private final DatabaseStorage databaseStorage;
    private final SimpMessagingTemplate messagingTemplate;
    private final JavaMailSender mailSender;
    private long lastTimestamp = 0;

@Autowired
    public TransactionService(ElasticsearchStorage elasticsearchStorage1, DatabaseStorage databaseStorage, SimpMessagingTemplate messagingTemplate, JavaMailSender mailSender) {
        this.elasticsearchStorage1 = elasticsearchStorage1;
        this.databaseStorage = databaseStorage;
        this.messagingTemplate = messagingTemplate;
        this.mailSender = mailSender;
}
    public List<TransactionSummary> getTransactionSummeries(String index, Long lastTime) throws IOException {
        List<ObjectNode> logs = elasticsearchStorage1.getLogs(index, lastTime);
        return logs.stream()
                .map(this::mapToTransactionSummary)
                .collect(Collectors.toList());

    }

    @Scheduled(fixedRate = 5000)
    public void processNewLogs() throws IOException {
        List<ObjectNode> logs = elasticsearchStorage1.getLogs("transactions-*", lastTimestamp);
        for (ObjectNode log : logs) {
            TransactionSummary summary = mapToTransactionSummary(log);
            Transaction transaction = mapToTransaction(summary);
            databaseStorage.saveEntity(transaction);
            // Check CREQ_RECEPTION_ms
            if (log.has("CREQ_RECEPTION_ms") && !log.get("CREQ_RECEPTION_ms").isNull() && log.get("CREQ_RECEPTION_ms").asDouble() > 100) {
                saveAlert(summary, "CREQ_RECEPTION_ms", "Response time exceeded 100ms for transaction " + summary.getTransactionId());
            }
            // Check CHALLENGE_DISPLAY_ms
            if (log.has("CHALLENGE_DISPLAY_ms") && !log.get("CHALLENGE_DISPLAY_ms").isNull() && log.get("CHALLENGE_DISPLAY_ms").asDouble() > 100) {
                saveAlert(summary, "CHALLENGE_DISPLAY_ms", "Challenge display time exceeded 100ms for transaction " + summary.getTransactionId());
            }
            // Check RREQ_SENDING_ms
            if (log.has("RREQ_SENDING_ms") && !log.get("RREQ_SENDING_ms").isNull() && log.get("RREQ_SENDING_ms").asDouble() > 100) {
                saveAlert(summary, "RREQ_SENDING_ms", "RREQ sending time exceeded 100ms for transaction " + summary.getTransactionId());
            }

            if (log.has("@timestamp")) {
                lastTimestamp = Math.max(lastTimestamp, ZonedDateTime.parse(log.get("@timestamp").asText()).toInstant().toEpochMilli());
            }
        }
    }

    private Transaction mapToTransaction(TransactionSummary summary) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(summary.getTransactionId());
        transaction.setTimestamp(summary.getTimestamp() != null ? summary.getTimestamp().toLocalDateTime() : LocalDateTime.now());
        transaction.setCreqReceptionMs(summary.getCreqReceptionMs());
        transaction.setChallengeDisplayMs(summary.getChallengeDisplayMs());
        transaction.setRreqSendingMs(summary.getRreqSendingMs());
        return transaction;
    }

    private void saveAlert(TransactionSummary summary, String field, String message) {
        DetectionAlert alert = new DetectionAlert();
        alert.setAlertType("THRESHOLD_EXCEEDED");
        alert.setField(field);
        alert.setMessage(message);
        alert.setTimestamp(LocalDateTime.now());
        databaseStorage.saveEntity(alert);
        messagingTemplate.convertAndSend("/topic/alerts", alert);

        // Envoyer un email d'alerte
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("selimanisara2@gmail.com"); // Remplace par l'email du destinataire
        mailMessage.setSubject("Alerte Transaction : " + field);
        mailMessage.setText("Transaction ID: " + summary.getTransactionId() + "\n" +
                "Champ: " + field + "\n" +
                "Message: " + message + "\n" +
                "Timestamp: " + LocalDateTime.now());
        mailSender.send(mailMessage);
    }

    public TransactionSummary mapToTransactionSummary(ObjectNode log) {
        TransactionSummary summary = new TransactionSummary();

        // Gestion des champs racines
        summary.setTransactionId(log.has("transaction_id") ? log.get("transaction_id").asText() : null);
        summary.setAuthStep(log.has("auth_step") ? log.get("auth_step").asText() : null);
        summary.setTimestamp(log.has("@timestamp") ? ZonedDateTime.parse(log.get("@timestamp").asText()) : null);


        // Gestion des champs de durée
        if (log.has("CREQ_RECEPTION_ms") && !log.get("CREQ_RECEPTION_ms").isNull()) {
            summary.setCreqReceptionMs(log.get("CREQ_RECEPTION_ms").asDouble());
        }
        if (log.has("CHALLENGE_DISPLAY_ms") && !log.get("CHALLENGE_DISPLAY_ms").isNull()) {
            summary.setChallengeDisplayMs(log.get("CHALLENGE_DISPLAY_ms").asDouble());
        }
        if (log.has("RREQ_SENDING_ms") && !log.get("RREQ_SENDING_ms").isNull()) {
            summary.setRreqSendingMs(log.get("RREQ_SENDING_ms").asDouble());
        }

        // Parsing du champ 'message' si présent
        /*if (log.has("message")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode messageNode = mapper.readTree(log.get("message").asText());

                // Extraction des champs imbriqués
                JsonNode transactionInfo = messageNode.path("transactionInfo");
                JsonNode transactionDetail = messageNode.path("transactionDetail");

                summary.setTransactionId(transactionInfo.path("transactionID").asText());
                // operationCode"
            } catch (IOException e) {
                // Log l'erreur ou ignorer
            }
        } else {
            // Fallback aux champs racines si 'message' n'existe pas
            summary.setTransactionId(log.has("transaction_id") ? log.get("transaction_id").asText() : null);
        }
*/
        return summary;
    }

}
