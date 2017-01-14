package net.giomagi.hpt.experiments;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GoogleQpxExperiment {

    public static void main(String[] args) throws IOException {

        String qpxKey = IOUtils.toString(new FileInputStream(".private/qpx.key"));

        HttpPost req = new HttpPost("https://www.googleapis.com/qpxExpress/v1/trips/search?key=" + qpxKey);
        req.setEntity(new StringEntity(IOUtils.toString(new FileInputStream(".private/qpx_test.json"))));
        req.addHeader("Content-Type", "application/json");

        CloseableHttpResponse response = HttpClients.createDefault().execute(req);

        System.out.println(response.getStatusLine());
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header.getName() + " > " + header.getValue());
        }

        System.out.println(IOUtils.toString(response.getEntity().getContent()));
    }
}
