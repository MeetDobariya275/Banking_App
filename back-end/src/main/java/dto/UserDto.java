package dto;

import handler.GsonTool;
import org.bson.Document;

public class UserDto extends BaseDto{

    private String userName;
    private String password;
    private Double balance = 0.0d;
    private String email = "";
    private String bio = "";

    private String billingAddress = "";

    private String mobileNo = "";

    public UserDto() {
        super();
    }

    public UserDto(String uniqueId) {
        super(uniqueId);
    }

    //Getters and setters
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance != null ? balance : 0;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Document toDocument() {
        return (new Document()).append("balance", this.balance).append("userName", this.userName).append("password", this.password);
    }

    public static UserDto fromDocument(Document match) {
        UserDto userDto = GsonTool.GSON.fromJson(match.toJson(), UserDto.class);
        if (match.get("_id") != null) {
            userDto.loadUniqueId(match);
        }
        return userDto;
    }
}
