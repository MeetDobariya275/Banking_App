package dto;

public enum ApprovalStatus {
    //Enum constants with their values
    Approved("Approved"),

    Pending("Pending"),

    Rejected("Rejected"),

    Closed("Closed"),

    Processed("Processed");
    private final String stringValue;

    ApprovalStatus(final String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
