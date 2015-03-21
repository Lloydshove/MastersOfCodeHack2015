package getithere;

import com.mastercard.api.payments.v2.*;
import com.mastercard.api.payments.v2.client.PaymentClient;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class TestMastercard {

    public static void main(String[] args) throws Exception {
        PaymentClient client = new PaymentClient(getPrivateKey(), "my-client-id", false);
        PurchaseRequest purchaseRequest = new PurchaseRequest();

        final Amount amount = new Amount();
        amount.setCurrency("GBP");
        amount.setValue(new BigInteger("441250"));
        purchaseRequest.setAmount(amount);

        MerchantIdentity merchantIdentity = new MerchantIdentity();
        merchantIdentity.setClientId("my-merchant-id");
        merchantIdentity.setPassword("my-merchant-password");

        purchaseRequest.setMerchantIdentity(merchantIdentity);
        purchaseRequest.setClientReference(getClientReference());

        Card card = new Card();
        card.setAccountNumber("6XXXXXXXXXXXXXXX");
        card.setExpiryMonth("01");
        card.setExpiryYear("11");
        card.setSecurityCode("111");

        purchaseRequest.setFundingCard(card);

        Purchase purchase = client.createPurchase(purchaseRequest);
    }

    private static String getClientReference() {
        return "NoIdeaWhatThisShouldBe";
    }

    /*
 * Pulls the private key out of a PEM file and loads it into an RSAPrivateKey and returns it.
 */
    private static PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String privKeyFile = "openapi-samplecode-privatekey.pem";
        final String beginPK = "-----BEGIN PRIVATE KEY-----";
        final String endPK = "-----END PRIVATE KEY-----";

        // read private key PEM file
        ClassLoader cl = TestMastercard.class.getClassLoader();
        InputStream stream = cl.getResourceAsStream(privKeyFile);
        java.io.DataInputStream dis = new java.io.DataInputStream(stream);
        byte[] privKeyBytes = new byte[stream.available()];
        dis.readFully(privKeyBytes);
        dis.close();
        String privKeyStr = new String(privKeyBytes, "UTF-8");

        int startIndex = privKeyStr.indexOf(beginPK);
        int endIndex = privKeyStr.indexOf(endPK);

        privKeyStr = privKeyStr.substring(startIndex + beginPK.length(), endIndex);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // decode private key
        PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec((new BASE64Decoder()).decodeBuffer(privKeyStr));
        return (RSAPrivateKey)keyFactory.generatePrivate(privSpec);
    }


}
