package getithere.backend;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GetActionTest {
    
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
        GetAction action = new GetAction("47692f4a-4475-4a6e-8825-249ec4146977", db);
        action.execute();

    }
}