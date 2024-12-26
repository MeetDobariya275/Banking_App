package dao;

import com.mongodb.client.MongoCollection;
import dto.FixedDepositDto;
import org.bson.Document;

public class FixedDepositDao {

    private static FixedDepositDao instance;
    private final MongoCollection<Document> collection;

    private FixedDepositDao(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public static FixedDepositDao getInstance() {
        if (instance == null) {
            instance = new FixedDepositDao(MongoConnection.getCollection("FixedDeposits"));
        }
        return instance;
    }

    // Create a fixed deposit in the database
    public void createFixedDeposit(FixedDepositDto fdDetails) {
        Document document = fdDetails.toDocument();
        collection.insertOne(document);
    }
}
