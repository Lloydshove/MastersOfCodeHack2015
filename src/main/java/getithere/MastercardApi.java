package getithere;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.jetty.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang3.StringUtils.rightPad;

public class MastercardApi {

    String url = "http://dmartin.org:8021/moneysend/v2/transfer?Format=XML";

    String sampleXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<TransferRequest>\n" +
            "   <LocalDate>0612</LocalDate>\n" +
            "   <LocalTime>161222</LocalTime>\n" +
            "   <TransactionReference>TRANSACTION_NUMBER_TO_REPLACE</TransactionReference>\n" +
            "   <SenderName>John Doe</SenderName>\n" +
            "   <SenderAddress>\n" +
            "      <Line1>123 Main Street</Line1>\n" +
            "\n" +
            "      <City>Arlington</City>\n" +
            "      <CountrySubdivision>VA</CountrySubdivision>\n" +
            "      <PostalCode>22207</PostalCode>\n" +
            "      <Country>USA</Country>\n" +
            "   </SenderAddress>\n" +
            "   <FundingCard>\n" +
            "      <AccountNumber>5184680430000014</AccountNumber>\n" +
            "      <ExpiryMonth>11</ExpiryMonth>\n" +
            "      <ExpiryYear>2016</ExpiryYear>\n" +
            "   </FundingCard>\n" +
            "   <FundingMasterCardAssignedId>123456</FundingMasterCardAssignedId>\n" +
            "   <FundingAmount>\n" +
            "      <Value>15000</Value>\n" +
            "      <Currency>840</Currency>\n" +
            "   </FundingAmount>\n" +
            "   <ReceiverName>Jose Lopez</ReceiverName>\n" +
            "   <ReceiverAddress>\n" +
            "      <Line1>Pueblo Street</Line1>\n" +
            "      <Line2>PO BOX 12</Line2>\n" +
            "      <City>El PASO</City>\n" +
            "      <CountrySubdivision>TX</CountrySubdivision>\n" +
            "      <PostalCode>79906</PostalCode>\n" +
            "      <Country>USA</Country>\n" +
            "   </ReceiverAddress>\n" +
            "   <ReceiverPhone>1800639426</ReceiverPhone>\n" +
            "   <ReceivingCard>\n" +
            "      <AccountNumber>5184680430000006</AccountNumber>\n" +
            "   </ReceivingCard>\n" +
            "   <ReceivingAmount>\n" +
            "      <Value>REPLACE_AMOUNT</Value>\n" +
            "      <Currency>484</Currency>\n" +
            "   </ReceivingAmount>\n" +
            "   <Channel>W</Channel>\n" +
            "   <UCAFSupport>true</UCAFSupport>\n" +
            "   <ICA>009674</ICA>\n" +
            "   <ProcessorId>9000000442</ProcessorId>\n" +
            "   <RoutingAndTransitNumber>990442082</RoutingAndTransitNumber>\n" +
            "   <CardAcceptor>\n" +
            "      <Name>My Local Bank</Name>\n" +
            "      <City>Saint Louis</City>\n" +
            "      <State>MO</State>\n" +
            "      <PostalCode>63101</PostalCode>\n" +
            "      <Country>USA</Country>\n" +
            "   </CardAcceptor>\n" +
            "  <TransactionDesc>P2P</TransactionDesc>\n" +
            "  <MerchantId>123456</MerchantId>\n" +
            "</TransferRequest>\n";

    public StringBuffer transfer(Integer amount) throws IOException {
        String xmlToSend = sampleXml.replace("REPLACE_AMOUNT", amount.toString());
        String newTransactionNumber = valueOf("314157" + currentTimeMillis());
        xmlToSend = xmlToSend.replace("TRANSACTION_NUMBER_TO_REPLACE", newTransactionNumber);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost postMethod = new HttpPost(url);
        postMethod.addHeader("Content-Type", "application/xml");

        StringEntity reqEntity = new StringEntity(xmlToSend);
        postMethod.setEntity(reqEntity);

        HttpResponse response = httpClient.execute(postMethod);
        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result);
        if(response.getStatusLine().getStatusCode() != 200){
            throw new RuntimeException("Failed to transfer " + response.getStatusLine().getStatusCode());
        }

        return result;
    }

}
