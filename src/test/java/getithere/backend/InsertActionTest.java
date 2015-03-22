package getithere.backend;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

public class InsertActionTest{
    
    private static MongoClient mongoClient;
    private static MongoDatabase db;
    
    @BeforeClass
    public static void beforeClass(){
        mongoClient = new MongoClient(MongoConnection.HOST, MongoConnection.PORT);
        db = mongoClient.getDatabase("mydb");
    }
    
    @AfterClass
    public static void afterClass(){
        mongoClient.close();
    }

    @Test
    public void testExecute() throws Exception {
        InsertAction action = new InsertAction("customerId", new BigDecimal("0"), new BigDecimal(0), new Date(), new Date(),
                Collections.emptyList(), "", BuyOrSell.SELL, db
        );
        action.execute();

    }
}