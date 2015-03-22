package getithere.mvc;

import getithere.backend.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
                               @RequestParam String title,
                               @RequestParam BuyOrSell buyOrSell){

        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyyyyHHmmss");
        DateTime startDate = formatter.parseDateTime(start);
        DateTime endDate = formatter.parseDateTime(end);
        InsertAction action = new InsertAction(customerId, xPosition, yPosition, startDate.toDate(), endDate.toDate(), keywords, title, buyOrSell, mongoConnection.getDb());
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

        FindAction action = new FindAction( xPosition, yPosition, startDate.toDate(), endDate.toDate(), buyOrSell, mongoConnection.getDb(), 5000);
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

        FindAction action = new FindAction( xPosition, yPosition, startDate.toDate(), endDate.toDate(), buyOrSell, mongoConnection.getDb(), 5000);
        return action.execute();
    }


    @RequestMapping(value="findAllFurther", method=RequestMethod.POST, produces="application/json")
    @ResponseBody()
    public Collection<Map<String,Object>> findAllFurther(@RequestParam BigDecimal xPosition,
                                                        @RequestParam BigDecimal yPosition,
                                                        @RequestParam String start,
                                                        @RequestParam String end,
                                                        @RequestParam BuyOrSell buyOrSell){


        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyyyyHHmmss");
        DateTime startDate = formatter.parseDateTime(start);
        DateTime endDate = formatter.parseDateTime(end);

        FindAction action = new FindAction( xPosition, yPosition, startDate.toDate(), endDate.toDate(), buyOrSell, mongoConnection.getDb(), 20000);
        return action.execute();
    }

    @RequestMapping(value="getById", method=RequestMethod.POST, produces="application/json")
    @ResponseBody()
    public Collection<Map<String,Object>> getById(@RequestParam String id){

        GetAction action = new GetAction(id, mongoConnection.getDb());
        return action.execute();
    }

    @RequestMapping(value="imageUpload", method=RequestMethod.POST, produces="application/json")
    @ResponseBody()
    public void imageUpload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        String filePath = request.getContextPath();
        String pathname = filePath + "/upload/";
        System.out.println("Transferring file to " + pathname);
        File dest = new File(pathname);
        file.transferTo(dest);


        HashMap<String, Object> map = new HashMap<>();
        map.put("result", "success");
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
     //   return list;
    }
}
