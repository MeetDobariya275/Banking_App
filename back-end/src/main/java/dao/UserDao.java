package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDao extends BaseDao<UserDto> {

    private static UserDao instance;

    private UserDao(MongoCollection<Document> collection){
        super(collection);
    }

    public static UserDao getInstance(){
        if(instance != null){
            return instance;
        }
        instance = new UserDao(MongoConnection.getCollection("UserDao"));
        return instance;
    }

    public static UserDao getInstance(MongoCollection<Document> collection){
        instance = new UserDao(collection);
        return instance;
    }

    //Update single document
    public boolean updateOne(Document filter, Document updateSet) {
        UpdateResult result = getInstance().collection.updateOne(filter, updateSet, new UpdateOptions().upsert(true));
        if (result == null) {
            System.out.println("Update operation failed: result is null.");
            return false;
        }
        return result.wasAcknowledged();
    }


    //Delete user from collection
    public boolean deleteUser(String userName, Document delete) {  //method to delete
        Document filter = new Document().append("userName", userName);
        return getInstance()
                .collection
                .deleteOne(filter)
                .wasAcknowledged();
    }

    public List<UserDto> query(Document filter){
        if (filter.containsKey("password")){
            //Hash the password before querying
            filter.replace("password", filter.getString("password"), DigestUtils.sha256Hex(filter.getString("password")));
        }

        return getInstance()
                .collection
                .find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(UserDto::fromDocument)
                .collect(Collectors.toList());
    }
}
