package name.msutherland.hackathon.backend;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.*;

public class FindAction {


    private final Logger log = LogManager.getLogger(FindAction.class);
    
    private final BigDecimal xPosition;
    private final BigDecimal yPosition;
    private final Date start;
    private final Date end;
    private final Collection<String> keywords;
    private final BuyOrSell buyOrSell;
    private final MongoDatabase db;

    public FindAction(BigDecimal xPosition, BigDecimal yPosition, Date start, Date end, Collection<String> keywords, BuyOrSell buyOrSell, MongoDatabase db) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.start = start;
        this.end = end;
        this.keywords = keywords;
        this.buyOrSell = buyOrSell;
        this.db = db;
    }

    public Collection<String> execute(){
        log.debug("Keywords = "+keywords);

        MongoCollection<Document> coll = db.getCollection("clientLocations");
        
        Document coordinates = new Document();
        coordinates.put("0", xPosition.doubleValue());
        coordinates.put("1", yPosition.doubleValue());



        Document dateQueryStart = new Document("start",
                new Document("$lte", end));

        Document dateQueryEnd = new Document("end",
                new Document("$gte", start));
        
        Document locationQuery =
                new Document("loc",
                        new Document("$near",
                                new Document("$geometry",
                                        new Document("type", "Point")
                                                .append("coordinates", coordinates))
                                        .append("$maxDistance",  500)
                        )
                );

        Document buyOrSellQuery =
                new Document("buyOrSell",buyOrSell.name()
                );
        
        List<Document> queries = new ArrayList<>();
        queries.add(dateQueryStart);
        queries.add(dateQueryEnd);
        queries.add(locationQuery);
        queries.add(buyOrSellQuery);
        for(String keyword : keywords) {
            queries.add(new Document("keyword",keyword));
        }
        Document allSubQuery = new Document("$and", queries);

        FindIterable<Document> locations= coll.find(allSubQuery);

        Collection<String> results = new HashSet<>();
        for(Document location : locations) {
            results.add((String)location.get("customerId"));
        }
        return results;
    }
}
