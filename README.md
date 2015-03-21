# MastersOfCodeHack2015

We need to use MoneySend to send money between people.
2 methods - 1 to register a credit card, 2 to send cash between the accounts
https://developer.mastercard.com/portal/display/api/MoneySend
https://developer.mastercard.com/portal/display/api/MoneySend+-+Sample+Code
https://developer.mastercard.com/portal/display/api/Transfer+-+Real+Time+v2

proxy address
http:/dmartin.org:8021


Testing Transfer
http://dmartin.org:8021/moneysend/v2/transfer
<?xml version="1.0" encoding="UTF-8"?>
<TransferRequest>
    <LocalDate>0612</LocalDate>
    <LocalTime>161222</LocalTime>
    <TransactionReference>4000000001010106666</TransactionReference>
    <SenderName>John Doe</SenderName>
    <SenderAddress>
        <Line1>123 Main Street</Line1>
        <Line2>#5A</Line2>
        <City>Arlington</City>
        <CountrySubdivision>VA</CountrySubdivision>
        <PostalCode>22207</PostalCode>
        <Country>USA</Country>
    </SenderAddress>
    <FundingCard>
        <AccountNumber>5555444433332222</AccountNumber>
        <ExpiryMonth>11</ExpiryMonth>
        <ExpiryYear>12</ExpiryYear>
    </FundingCard>
    <FundingUCAF>MjBjaGFyYWN0ZXJqdW5rVUNBRjU=1111</FundingUCAF>
    <FundingMasterCardAssignedId>123456</FundingMasterCardAssignedId>
    <FundingAmount>
         <Value>15000</Value>
        <Currency>840</Currency>
    </FundingAmount>
    <ReceiverName>Jose Lopez</ReceiverName>
    <ReceiverAddress>
        <Line1>Pueblo Street</Line1>
        <Line2>PO BOX 12</Line2>
        <City>El PASO</City>
        <CountrySubdivision>TX</CountrySubdivision>
        <PostalCode>79906</PostalCode>
        <Country>USA</Country>
    </ReceiverAddress>
    <ReceiverPhone>1800639426</ReceiverPhone>
    <ReceivingCard>
        <AccountNumber>5555444433332223</AccountNumber>
    </ReceivingCard>
    <ReceivingAmount>
        <Value>182206</Value>
        <Currency>484</Currency>
    </ReceivingAmount>
    <Channel>W</Channel>
    <UCAFSupport>true</UCAFSupport>
    <ICA>009674</ICA>
    <ProcessorId>9000000442</ProcessorId>
    <RoutingAndTransitNumber>990442082</RoutingAndTransitNumber>
    <CardAcceptor>
        <Name>My Local Bank</Name>
        <City>Saint Louis</City>
        <State>MO</State>
        <PostalCode>63101</PostalCode>
        <Country>USA</Country>
    </CardAcceptor>
    <TransactionDesc>P2P</TransactionDesc>
    <MerchantId>123456</MerchantId>
</TransferRequest>



Mastercard RePower is almost useful - it allows people to repower (add money to) your card from points of deposit : POS, ATM, Mobile
https://developer.mastercard.com/portal/display/api/MasterCard+rePower