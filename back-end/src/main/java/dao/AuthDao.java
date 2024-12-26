package dao;

import com.mongodb.client.MongoCollection;
import dto.AuthDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;

public class AuthDao extends BaseDao<AuthDto> {
    //Singleton instance
    private static AuthDao instance;

    //Constructor- intialize AuthDao 
    private AuthDao(MongoCollection<Document> collection){
        super(collection);
    }

    public static AuthDao getInstance(){
        if(instance != null){
            return instance;  //Returns existing instance
        }
        //Initialize instance with Default Collection
        instance = new AuthDao(MongoConnection.getCollection("AuthDao"));
        return instance;
    }

    public static AuthDao getInstance(MongoCollection<Document> collection){
        instance = new AuthDao(collection);
        return instance;
    }

    //Queries the database
    @Override
    public List<AuthDto> query(Document filter) {
        return getInstance().collection  //Access the collection
                .find(filter)   //Execute query
                .into(new ArrayList<>())  //Retrieve results
                .stream() //convert results to stream
                .map(AuthDto::fromDocument)  //map each doc
                .collect(Collectors.toList());  //collect mapped objects into list
    }
}
