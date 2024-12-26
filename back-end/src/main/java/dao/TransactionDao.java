package dao;

import com.mongodb.client.MongoCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dto.TransactionDto;
import org.bson.Document;

public class TransactionDao extends BaseDao<TransactionDto> {

    private static TransactionDao instance;

    //Constructor
    private TransactionDao(MongoCollection<Document> collection){
        super(collection);
    }

    public static TransactionDao getInstance(){
        if(instance != null){
            return instance;
        }
        instance = new TransactionDao(MongoConnection.getCollection("TransactionDao"));
        return instance;
    }

    public static TransactionDao getInstance(MongoCollection<Document> collection){
        //Create new instance and return it
        instance = new TransactionDao(collection);
        return instance;
    }

    public List<TransactionDto> query(Document filter){
        return getInstance()
                .collection
                .find(filter)
                .into(new ArrayList<>())
                .stream()
                .map(TransactionDto::fromDocument)
                .collect(Collectors.toList());
    }

}
