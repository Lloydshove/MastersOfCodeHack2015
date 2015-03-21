package name.msutherland.hackathon.mvc;

import name.msutherland.hackathon.backend.BuyOrSell;
import name.msutherland.hackathon.backend.FindAction;
import name.msutherland.hackathon.backend.InsertAction;
import name.msutherland.hackathon.backend.MongoConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;

@RequestMapping("api")
public class ApiActions {
    
    private final Logger log = LogManager.getLogger(ApiActions.class);
    private final MongoConnection mongoConnection;
    
    public ApiActions(MongoConnection mongoConnection){
        log.debug("Created");
        this.mongoConnection = mongoConnection;
    }

    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        return "Hello Cruel World";
    }

    @RequestMapping("insert")
    @ResponseBody
    public Boolean insertPoint(@RequestParam String customerId,
                               @RequestParam BigDecimal xPosition,
                               @RequestParam BigDecimal yPosition,
                               @RequestParam String start,
                               @RequestParam String end,
                               @RequestParam List<String> keywords,
                               @RequestParam BuyOrSell buyOrSell){

        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyyyyHHmmss");
        DateTime startDate = formatter.parseDateTime(start);
        DateTime endDate = formatter.parseDateTime(end);
        InsertAction action = new InsertAction(customerId, xPosition, yPosition, startDate.toDate(), endDate.toDate(), keywords, buyOrSell, mongoConnection.getDb());
        action.execute();
        return true;
    }
    
    @RequestMapping(value="find", method=RequestMethod.POST, produces="application/json")
    @ResponseBody()
    public Collection<Map<String,Object>> findPointForApplication(@RequestParam BigDecimal xPosition,
                               @RequestParam BigDecimal yPosition,
                               @RequestParam String start,
                               @RequestParam String end,
                               @RequestParam List<String> keywords,
                               @RequestParam BuyOrSell buyOrSell){


        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyyyyHHmmss");
        DateTime startDate = formatter.parseDateTime(start);
        DateTime endDate = formatter.parseDateTime(end);

        FindAction action = new FindAction( xPosition, yPosition, startDate.toDate(), endDate.toDate(), buyOrSell, mongoConnection.getDb());
        action.setKeywords(keywords);
        return action.execute();
    }

    @RequestMapping(value="findAllNearby", method=RequestMethod.POST, produces="application/json")
    @ResponseBody()
    public Collection<Map<String,Object>> findAllNearby(@RequestParam BigDecimal xPosition,
                                                                  @RequestParam BigDecimal yPosition,
                                                                  @RequestParam String start,
                                                                  @RequestParam String end,
                                                                  @RequestParam BuyOrSell buyOrSell){


        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyyyyHHmmss");
        DateTime startDate = formatter.parseDateTime(start);
        DateTime endDate = formatter.parseDateTime(end);

        FindAction action = new FindAction( xPosition, yPosition, startDate.toDate(), endDate.toDate(), buyOrSell, mongoConnection.getDb());
        return action.execute();
    }
}
