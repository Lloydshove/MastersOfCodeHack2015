package name.msutherland.hackathon.backend;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.DisposableBean;


public class MongoConnection implements DisposableBean {

    private MongoClient mongoClient;
    private MongoDatabase db;
    
    public MongoConnection(){
        mongoClient = new MongoClient( "10.1.2.5" , 27017 );
        db = mongoClient.getDatabase("mydb");
    }
    
    @Override
    public void destroy() throws Exception {
        mongoClient.close();
    }
    
    public MongoDatabase getDb(){
        return db;
    }
}
