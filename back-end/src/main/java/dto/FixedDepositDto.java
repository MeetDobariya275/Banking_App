package dto;

import org.bson.Document;
import request.ParsedRequest;

public class FixedDepositDto {
    private Double amount;
    private Integer durationYears;
    private Double interestRate;
    private Double maturityAmount;


    // Getters and setters
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDurationYears() {
        return durationYears;
    }

    public void setDurationYears(Integer durationYears) {
        this.durationYears = durationYears;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(Double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    // Convert FixedDepositDto to MongoDB Document
    public Document toDocument() {
        Document document = new Document();
        document.append("amount", this.amount);
        document.append("durationYears", this.durationYears);
        document.append("interestRate", this.interestRate);
        document.append("maturityAmount", this.maturityAmount);
        return document;
    }

    // Convert a MongoDB Document to FixedDepositDto
    public static FixedDepositDto fromDocument(Document document) {
        FixedDepositDto fdDto = new FixedDepositDto();
        fdDto.setAmount(getDoubleValue(document.get("amount")));
        fdDto.setDurationYears(getIntegerValue(document.get("durationYears")));
        fdDto.setInterestRate(getDoubleValue(document.get("interestRate")));
        fdDto.setMaturityAmount(getDoubleValue(document.get("maturityAmount")));
        return fdDto;
    }

    // Convert a ParsedRequest body to FixedDepositDto
    public static FixedDepositDto fromRequest(ParsedRequest request) {
        Document document = Document.parse(request.getBody());
        return fromDocument(document);
    }

    // Helper methods for type-safe conversions
    private static Double getDoubleValue(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return null;
    }

    private static Integer getIntegerValue(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return null;
    }
}
