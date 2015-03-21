package name.msutherland.hackathon.backend;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class InsertAction {
    
    private final String customerId;
    private final BigDecimal xPosition;
    private final BigDecimal yPosition;
    private final Date start;
    private final Date end;
    private final Collection<String> keywords;
    private final BuyOrSell buyOrSell;
    private final MongoDatabase db;

    public InsertAction(String customerId, BigDecimal xPosition, BigDecimal yPosition, Date start, Date end, Collection<String> keywords,
                        BuyOrSell buyOrSell, MongoDatabase db) {
        this.customerId = customerId;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.start = start;
        this.end = end;
        this.keywords = keywords;
        this.buyOrSell = buyOrSell;
        this.db = db;
    }
    
    public void execute(){

        MongoCollection<Document> coll = db.getCollection("clientLocations");


        coll.createIndex(new Document("loc", "2dsphere"));
        coll.createIndex(new Document("description", 1));
        
        Document coordinates = new Document();
        coordinates.put("0", xPosition.doubleValue());
        coordinates.put("1", yPosition.doubleValue());

        coll.insertOne(new Document("customerId", customerId)
                .append("loc", new Document("type", "Point").append("coordinates", coordinates))
                .append("start", start)
                .append("end", end)
                .append("keyword", keywords)
                .append("buyOrSell", buyOrSell.name()));

    }
}