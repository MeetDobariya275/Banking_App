package dto;

import org.bson.Document;

import java.util.HashMap;

public class AuthDto extends BaseDto{

    private String userName;
    private Long expireTime;
    private String hash;

    //Get and set methods
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getUserName() {
        return userName;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public String getHash() {
        return hash;
    }

    //Convert AuthDto object to Document
    @Override
    public Document toDocument() {
        HashMap<String, Object> authDoc = new HashMap<>();
        authDoc.put("userName", getUserName());
        authDoc.put("hash", getHash());
        authDoc.put("expireTime", getExpireTime());
        return new Document(authDoc);
    }

    //Convert Doc to AuthDto object
    public static AuthDto fromDocument(Document document){
        AuthDto authDto = new AuthDto();
        authDto.setUserName(document.getString("userName"));
        authDto.setHash(document.getString("hash"));
        authDto.setExpireTime(document.getLong("expireTime"));
        return authDto;
    }
}
