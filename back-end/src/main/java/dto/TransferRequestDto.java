package dto;

public class TransferRequestDto {

    public final double amount;
    public final String fromId;
    public final String toId;

    //Constructor for all fields
    public TransferRequestDto(double amount, String fromId, String toId) {
        this.amount = amount;
        this.fromId = fromId;
        this.toId = toId;
    }
}
