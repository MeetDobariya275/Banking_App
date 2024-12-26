package dto;

import org.bson.Document;

import java.time.Instant;
import java.util.HashMap;

public class TransactionDto extends BaseDto{

    private String userId;
    private String toId;
    private Double amount;
    private TransactionType transactionType;
    private Long timestamp;

    public TransactionDto(){
        timestamp = Instant.now().toEpochMilli();
    }

    public TransactionDto(String uniqueId) {
        super(uniqueId);
        timestamp = Instant.now().toEpochMilli();
    }

    //Getter and setter methods
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionType() {
        return transactionType.toString();
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    //Convert TransactionDto to Doc
    public Document toDocument(){
        HashMap<String, Object> transactionDoc = new HashMap<>();
        transactionDoc.put("userId", getUserId());
        transactionDoc.put("toId", getToId());
        transactionDoc.put("amount", getAmount());
        transactionDoc.put("transactionType", getTransactionType());
        transactionDoc.put("timestamp", getTimestamp());

        return new Document(transactionDoc);
    }

    //Doc to TransactionDto
    public static TransactionDto fromDocument(Document document) {
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.loadUniqueId(document);
        transactionDto.setAmount(document.getDouble("amount"));
        transactionDto.setTimestamp(document.getLong("timestamp"));
        transactionDto.setTransactionType(TransactionType.valueOf(document.getString("transactionType")));
        transactionDto.setToId(document.getString("toId"));
        transactionDto.setUserId(document.getString("userId"));

        return transactionDto;
    }
}
