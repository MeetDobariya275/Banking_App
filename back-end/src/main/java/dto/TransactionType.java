package dto;

public enum TransactionType {
    //Enum values with transaction types
    Deposit("Deposit"),
    Transfer("Transfer"),
    Withdraw("Withdraw"),

    Refund("Refund");

    private final String stringValue;

    TransactionType(final String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
