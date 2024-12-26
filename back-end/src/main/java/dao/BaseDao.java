package dao;

import com.mongodb.client.MongoCollection;
import dto.BaseDto;
import java.util.List;

import org.bson.Document;

public abstract class BaseDao<T extends BaseDto> {

    final MongoCollection<Document> collection;

    //Constructor to set collection
    protected BaseDao(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    abstract List<T> query(Document filter);


    //Insert or update DTO
    public void put(T dto) {
        //If null-insert new doc, else replace the existing doc
        if (dto.getUniqueId() == null) {
            collection.insertOne(dto.toDocument());
        } else {
            collection.replaceOne(dto.getObjectId(), dto.toDocument());
        }
    }

}
