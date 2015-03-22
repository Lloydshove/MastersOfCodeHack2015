package getithere.backend;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class FindActionTest {

    private static MongoClient mongoClient;
    private static MongoDatabase db;

    @BeforeClass
    public static void beforeClass(){
        mongoClient = new MongoClient(MongoConnection.HOST, MongoConnection.PORT);
        db = mongoClient.getDatabase("mydb");

        InsertAction action = new InsertAction("customerId", new BigDecimal(0), new BigDecimal(0), new Date(), new Date(),
                Collections.singletonList("Alpha"), "title", BuyOrSell.SELL, db
        );
        action.execute();
    }

    @AfterClass
    public static void afterClass(){
        mongoClient.close();
    }

    @Test
    public void testExecuteWithNarrowDateRange() throws Exception {
        FindAction action = new FindAction(new BigDecimal(0), new BigDecimal(0), new Date(), new Date(), BuyOrSell.SELL, db, 500);
        Collection results = action.execute();
        assertThat(results.size(), equalTo(0));
    }

    @Test
    public void testExecuteWithHugeRange() throws Exception {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        DateTime start = formatter.parseDateTime("12/12/1000 00:00:00");
        DateTime end = formatter.parseDateTime("12/12/3000 00:00:00");
        
        FindAction action = new FindAction(new BigDecimal(0), new BigDecimal(0), start.toDate(), end.toDate(), BuyOrSell.SELL, db, 500);
        Collection results = action.execute();
        assertThat(results.isEmpty(),equalTo(false));
    }

    @Test
    public void testExecuteWithHugeRangeWrongLocation() throws Exception {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        DateTime start = formatter.parseDateTime("12/12/1000 00:00:00");
        DateTime end = formatter.parseDateTime("12/12/3000 00:00:00");

        FindAction action = new FindAction(new BigDecimal(44), new BigDecimal(44), start.toDate(), end.toDate(), BuyOrSell.SELL, db, 500);
        Collection results = action.execute();
        assertThat(results.isEmpty(),equalTo(true));
    }

    @Test
    public void testExecuteWithHugeRangeWrongSell() throws Exception {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        DateTime start = formatter.parseDateTime("12/12/1000 00:00:00");
        DateTime end = formatter.parseDateTime("12/12/3000 00:00:00");

        FindAction action = new FindAction(new BigDecimal(0), new BigDecimal(0), start.toDate(), end.toDate(),  BuyOrSell.BUY, db, 500);
        Collection results = action.execute();
        assertThat(results.isEmpty(),equalTo(true));
    }

    @Test
    public void testExecuteWithHugeRangeWrongKeyword() throws Exception {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        DateTime start = formatter.parseDateTime("12/12/1000 00:00:00");
        DateTime end = formatter.parseDateTime("12/12/3000 00:00:00");

        FindAction action = new FindAction(new BigDecimal(0), new BigDecimal(0), start.toDate(), end.toDate(),  BuyOrSell.SELL, db, 500);
        action.setKeywords(Collections.singletonList("NeverEntered"));
        Collection results = action.execute();
        assertThat(results.isEmpty(),equalTo(true));
    }
}