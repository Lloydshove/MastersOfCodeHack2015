package name.msutherland.hackathon.backend;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;

public class GetAction {
    private String id;
    private MongoDatabase db;

    public GetAction(String id, MongoDatabase db) {
        this.id = id;
        this.db = db;
    }


    public Collection<Map<String, Object>> execute() {
        MongoCollection<Document> coll = db.getCollection("clientLocations");
        BasicDBObject query = new BasicDBObject();
        query.put("id", UUID.fromString(id));

        FindIterable<Document> documents = coll.find(query);

        Collection<Map<String, Object>> results = new HashSet<>();
        for (Document document : documents) {
            System.out.println(document);
            Map<String, Object> result = new HashMap<>();
            result.put("customer", document.get("customerName"));
            result.put("keyword", document.get("keyword"));
            result.put("id", document.get("id"));
            result.put("title", document.get("title"));
            Document coordinatesOut = (Document) ((Document) document.get("loc")).get("coordinates");
            result.put("xPosition", coordinatesOut.get("0"));
            result.put("yPosition", coordinatesOut.get("1"));
            results.add(result);
        }
        return results;
    }
}
