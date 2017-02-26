package net.giomagi.hpt.experiments;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SkyscannerLiveExperiment {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost sessionReq = new HttpPost("http://partners.api.skyscanner.net/apiservices/pricing/v1.0");

        sessionReq.addHeader("Content-Type", "application/x-www-form-urlencoded");
        sessionReq.addHeader("X-Forwarded-For", IOUtils.toString(new FileInputStream(".private/public.ip.address")));
        sessionReq.addHeader("Accept", "application/json");

        List<NameValuePair> formParams = new ArrayList<>();

        formParams.add(new BasicNameValuePair("country", "UK"));
        formParams.add(new BasicNameValuePair("currency", "GBP"));
        formParams.add(new BasicNameValuePair("locale", "en-GB"));
        formParams.add(new BasicNameValuePair("originPlace", "LHR"));
        formParams.add(new BasicNameValuePair("destinationPlace", "LIN"));
        formParams.add(new BasicNameValuePair("outboundDate", "2017-03-18"));
        formParams.add(new BasicNameValuePair("adults", "1"));
        formParams.add(new BasicNameValuePair("apiKey", IOUtils.toString(new FileInputStream(".private/skyscanner.key"))));

        sessionReq.setEntity(new UrlEncodedFormEntity(formParams));
        CloseableHttpResponse response = client.execute(sessionReq);

        System.out.println(response.getStatusLine());
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header.getName() + " > " + header.getValue());
        }

        System.out.println(IOUtils.toString(response.getEntity().getContent()));
    }
}
