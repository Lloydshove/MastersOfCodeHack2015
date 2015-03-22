package name.msutherland.hackathon.mvc;

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

//        WebClient client = new WebClient();
//        WebRequest req = new WebRequest(new URL("http://127.0.0.1:8080/dynamic/api/insert"), HttpMethod.POST);
//        req.setAdditionalHeader("Accept", "application/json");
//        List<NameValuePair> requestParams = new ArrayList<>();
//        requestParams.add(new NameValuePair("customerId", "0.1"));
//        requestParams.add(new NameValuePair("xPosition", "0.1"));
//        requestParams.add(new NameValuePair("yPosition", "0"));
//        requestParams.add(new NameValuePair("start", "01011000010101"));
//        requestParams.add(new NameValuePair("end", "01011300010101"));
//
//        requestParams.add(new NameValuePair("keywords","Hello"));
//
//        requestParams.add(new NameValuePair("keywords","Hello2"));
//
//        requestParams.add(new NameValuePair("buyOrSell","BUY"));
//        req.setRequestParameters(requestParams);
//        System.out.println(client.getPage(req).getWebResponse().getContentAsString());
    }


    @Test
    public void submitInsert2() throws Exception {

        submitStandard("Mr Big", "Size 1 baby shoes.  Designer Minolo Blahnic's.  Brand New.  3 inch heels.", "New Baby Shoes, minolo blahnic.  Bargain", "0.1", "0");
        submitStandard("M. Thatcher", "British Telecom, Royal Mail, National Rail and large parts of NHS MUST GO!  All at rock bottom prices", "Old public services", "114.15953636169434", "22.282028075082735");
        submitStandard("J R Hartley", "Looking back on a lifetime of great fishing, the British angler shares his fondest memories of fly fishing in the chalk streams, spate rivers, and lochs of his native country.", "Second hand book - Fly Fishing", "114.1933536529541", "22.28766689490731");
        submitStandard("Marleen Hart", "Some Book.", "Second hand book - Some Book",  "114.1910536529541", "22.28566689490731");
        submitStandard("Kat Aitken", "Some Book Again.", "Second hand book - Some Book Again", "114.1873536529541", "22.25766689490731");
        submitStandard("Darcy Cross", "Yet More Book.", "Second hand book - Yet More Book", "114.1754545954149", "21.99766689490731");
    }

    private void submitStandard(String customer, String keywords, String title, String xPosition, String yPosition) throws java.io.IOException {
        String start = "01011000010101";
        String end = "01011300010101";
        WebClient client = new WebClient();
        WebRequest req = new WebRequest(new URL("http://127.0.0.1:8080/dynamic/api/insert"), HttpMethod.POST);
        req.setAdditionalHeader("Accept", "application/json");
        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new NameValuePair("customerId", customer));
        requestParams.add(new NameValuePair("xPosition", xPosition));
        requestParams.add(new NameValuePair("yPosition", yPosition));
        requestParams.add(new NameValuePair("start", start));
        requestParams.add(new NameValuePair("end", end));
        requestParams.add(new NameValuePair("keywords", keywords));
        requestParams.add(new NameValuePair("buyOrSell", "BUY"));
        requestParams.add(new NameValuePair("title", title));

        req.setRequestParameters(requestParams);
        System.out.println(client.getPage(req).getWebResponse().getContentAsString());
    }


    private void submitStandard(String customer, String keywords, String title) throws java.io.IOException {
        String start = "01011000010101";
        String end = "01011300010101";
        WebClient client = new WebClient();
        WebRequest req = new WebRequest(new URL("http://127.0.0.1:8080/dynamic/api/insert"), HttpMethod.POST);
        req.setAdditionalHeader("Accept", "application/json");
        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new NameValuePair("customerId", customer));
        requestParams.add(new NameValuePair("xPosition", "0.1"));
        requestParams.add(new NameValuePair("yPosition", "0"));
        requestParams.add(new NameValuePair("start", start));
        requestParams.add(new NameValuePair("end", end));
        requestParams.add(new NameValuePair("keywords", keywords));
        requestParams.add(new NameValuePair("buyOrSell", "BUY"));
        requestParams.add(new NameValuePair("title", title));

        req.setRequestParameters(requestParams);
        System.out.println(client.getPage(req).getWebResponse().getContentAsString());
    }

    @Test
    public void submitFind() throws Exception {

        WebClient client = new WebClient();
        WebRequest req = new WebRequest(new URL("http://127.0.0.1:8080/dynamic/api/find"), HttpMethod.POST);
        req.setAdditionalHeader("Accept", "application/json");
        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new NameValuePair("xPosition", "0.1"));
        requestParams.add(new NameValuePair("yPosition", "0"));
        requestParams.add(new NameValuePair("start", "01011000010101"));
        requestParams.add(new NameValuePair("end", "01011300010101"));

        requestParams.add(new NameValuePair("keywords", "keyword"));

        requestParams.add(new NameValuePair("buyOrSell", "BUY"));
        req.setRequestParameters(requestParams);
        System.out.println(client.getPage(req).getWebResponse().getContentAsString());
    }


    @Test
    public void submitFindAllNearby() throws Exception {
        WebClient client = new WebClient();
        WebRequest req = new WebRequest(new URL("http://127.0.0.1:8080/dynamic/api/findAllNearby"), HttpMethod.POST);
        req.setAdditionalHeader("Accept", "application/json");
        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new NameValuePair("xPosition", "0.1"));
        requestParams.add(new NameValuePair("yPosition", "0"));
        requestParams.add(new NameValuePair("start", "01011000010101"));
        requestParams.add(new NameValuePair("end", "01011300010101"));
        requestParams.add(new NameValuePair("buyOrSell", "BUY"));
        req.setRequestParameters(requestParams);
        System.out.println(client.getPage(req).getWebResponse().getContentAsString());
    }

}
