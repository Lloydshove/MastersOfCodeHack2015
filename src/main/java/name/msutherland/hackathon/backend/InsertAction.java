package name.msutherland.hackathon.backend;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InsertAction {


    private final Logger log = LogManager.getLogger(InsertAction.class);
    
    private final String customerId;
    private final BigDecimal xPosition;
    private final BigDecimal yPosition;
    private final Date start;
    private final Date end;
    private final List<String> keywords;
    private String title;
    private final BuyOrSell buyOrSell;
    private final MongoDatabase db;

    public InsertAction(String customerId, BigDecimal xPosition, BigDecimal yPosition, Date start, Date end, List<String> keywords,
                        String title, BuyOrSell buyOrSell, MongoDatabase db) {
        this.customerId = customerId;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.start = start;
        this.end = end;
        this.keywords = keywords;
        this.title = title;
        this.buyOrSell = buyOrSell;
        this.db = db;
    }
    
    public void execute(){

        MongoCollection<Document> coll = db.getCollection("clientLocations");


        coll.createIndex(new Document("loc", "2dsphere"));
        coll.createIndex(new Document("description", 1));
        
        Document coordinates = new Document();
        coordinates.put("0", yPosition.doubleValue());
        coordinates.put("1", xPosition.doubleValue());

        coll.insertOne(new Document("id", UUID.randomUUID())
                .append("loc", new Document("type", "Point").append("coordinates", coordinates))
                .append("start", start)
                .append("end", end)
                .append("keyword", keywords)
                .append("title", title)
                .append("buyOrSell", buyOrSell.name())
                .append("customerName", customerId));
        log.debug(new FormattedMessage("Inserted location for %s", customerId));

    }
}
