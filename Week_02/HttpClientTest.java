import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpClientTest {

    public static void main(String[] args) throws IOException
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet("http://localhost:8088/api/hello");
//        HttpPost httpPost = new HttpPost("http://localhost:8088/api/hello/");
        CloseableHttpResponse response = httpclient.execute(httpget);
        System.out.println(response);
    }
}
