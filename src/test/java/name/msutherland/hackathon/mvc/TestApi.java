package name.msutherland.hackathon.mvc;
import com.gargoylesoftware.htmlunit.FormEncodingType;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelsutherland on 21/03/2015.
 */
public class TestApi {


    @Test
    public void submitInsert() throws Exception {

        WebClient client = new WebClient();
        WebRequest req = new WebRequest(new URL("http://127.0.0.1:8080/dynamic/api/insert"), HttpMethod.POST);
        req.setAdditionalHeader("Accept", "application/json");
        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new NameValuePair("customerId", "0.1"));
        requestParams.add(new NameValuePair("xPosition", "0.1"));
        requestParams.add(new NameValuePair("yPosition", "0"));
        requestParams.add(new NameValuePair("start", "01011000010101"));
        requestParams.add(new NameValuePair("end", "01011300010101"));

        requestParams.add(new NameValuePair("keywords","Hello"));

        requestParams.add(new NameValuePair("keywords","Hello2"));

        requestParams.add(new NameValuePair("buyOrSell","BUY"));
        req.setRequestParameters(requestParams);
        System.out.println(client.getPage(req).getWebResponse().getContentAsString());
    }


    @Test
    public void submitFind() throws Exception {

        WebClient client = new WebClient();
        WebRequest req = new WebRequest(new URL("http://127.0.0.1:8080/dynamic/api/find"), HttpMethod.POST);
        req.setAdditionalHeader("Accept", "application/json");
        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new NameValuePair("xPosition","0.1"));
        requestParams.add(new NameValuePair("yPosition","0"));
        requestParams.add(new NameValuePair("start","01011000010101"));
        requestParams.add(new NameValuePair("end","01011300010101"));

        requestParams.add(new NameValuePair("keywords","keyword"));

        requestParams.add(new NameValuePair("buyOrSell","BUY"));
        req.setRequestParameters(requestParams);
        System.out.println(client.getPage(req).getWebResponse().getContentAsString());
    }


    @Test
    public void submitFindAllNearby() throws Exception {
        WebClient client = new WebClient();
        WebRequest req = new WebRequest(new URL("http://127.0.0.1:8080/dynamic/api/findAllNearby"), HttpMethod.POST);
        req.setAdditionalHeader("Accept", "application/json");
        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new NameValuePair("xPosition","0.1"));
        requestParams.add(new NameValuePair("yPosition","0"));
        requestParams.add(new NameValuePair("start","01011000010101"));
        requestParams.add(new NameValuePair("end","01011300010101"));
        requestParams.add(new NameValuePair("buyOrSell","BUY"));
        req.setRequestParameters(requestParams);
        System.out.println(client.getPage(req).getWebResponse().getContentAsString());
    }

}
