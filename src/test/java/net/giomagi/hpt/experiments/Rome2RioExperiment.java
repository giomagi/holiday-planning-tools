package net.giomagi.hpt.experiments;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Rome2RioExperiment {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        String uri = "http://free.rome2rio.com/api/1.4/json/Search?key=0FbQY4Qf&oName=LHR&dName=MXP&oKind=airport&dKind=airport&currencyCode=GBP&languageCode=EN&noRail&noBus&noFerry&noCar&noBikeshare&noRideshare&noTowncar&noCommuter&noSpecial&noMinorStart&noMinorEnd&noPath&noStop";
        System.out.println(uri);
        HttpGet request = new HttpGet(uri);

        CloseableHttpResponse response = client.execute(request);

        System.out.println(response.getStatusLine());
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header.getName() + " > " + header.getValue());
        }

        System.out.println(IOUtils.toString(response.getEntity().getContent()));
    }
}
