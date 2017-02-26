package net.giomagi.hpt.experiments;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileInputStream;
import java.io.IOException;

public class SkyscannerBrowseExperiment {

    public static void main(String[] args) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();

        String skyscannerKey = IOUtils.toString(new FileInputStream(".private/skyscanner.key"));
        HttpGet request = new HttpGet("http://partners.api.skyscanner.net/apiservices/browsequotes/v1.0/" +
                "GB/GBP/en-GB/LON/SVQ/2017-05-22/?apiKey=" + skyscannerKey);

        request.addHeader("Accept", "application/json");

        CloseableHttpResponse response = client.execute(request);

        System.out.println(response.getStatusLine());
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header.getName() + " > " + header.getValue());
        }

        System.out.println(IOUtils.toString(response.getEntity().getContent()));
    }
}
