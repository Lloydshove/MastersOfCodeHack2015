package name.msutherland.hackathon.backend;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Main {
    
    public static void main(String[] args){
        new Main();
    }
    
    public Main(){
        MongoClient mongoClient = new MongoClient( "10.1.2.5" , 27017 );

        MongoDatabase db = mongoClient.getDatabase("mydb");
        setupGeoLookup( db );
        mongoClient.close();
        
        
    }

    private void setupTestCollection(MongoDatabase db ){
        MongoCollection<Document> coll = db.getCollection("testCollection");
        
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));
        coll.insertOne(doc);
    }

    private void setupGeoLookup(MongoDatabase db ){
        MongoCollection<Document> coll = db.getCollection("clientLocations");


        coll.createIndex(new Document("loc", "2dsphere"));
        Document coordinates = new Document();
        coordinates.put("0", -73.97);
        coordinates.put("1", 40.77);
        coll.insertOne(new Document("name", "Central Park")
                .append("loc", new Document("type", "Point").append("coordinates", coordinates))
                .append("category", "Parks"));

        coordinates.put("0", -73.88);
        coordinates.put("1", 40.78);
        coll.insertOne(new Document("name", "La Guardia Airport")
                .append("loc", new Document("type", "Point").append("coordinates", coordinates))
                .append("category", "Airport"));


// Find whats within 500m of my location
        Document myLocation = new Document();
        myLocation.put("0", -73.965);
        myLocation.put("1", 40.769);
        FindIterable<Document> locations= coll.find(
                new BasicDBObject("loc",
                        new BasicDBObject("$near",
                                new BasicDBObject("$geometry",
                                        new BasicDBObject("type", "Point")
                                                .append("coordinates", myLocation))
                                        .append("$maxDistance",  500)
                        )
                )
        );
        for(Document location : locations) {
            System.out.println(location.get("name"));
        }
    }
}
