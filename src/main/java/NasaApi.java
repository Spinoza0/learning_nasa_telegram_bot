import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class NasaApi {
    // NASA_TOKEN - your own telegram token (see https://api.nasa.gov/)
    private static final String URI = "https://api.nasa.gov/planetary/apod?api_key=" + Token.NASA_TOKEN;
    private static CloseableHttpClient httpClient = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(RequestConfig
                    .custom()
                    .setConnectTimeout(5000)
                    .setSocketTimeout(30000)
                    .setRedirectsEnabled(false)
                    .build())
            .build();

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String getPictureUrl() {
        String result;
        NasaObject nasaObject = null;
        try {
            CloseableHttpResponse response = httpClient.execute(new HttpGet(URI));
            nasaObject = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
            result = nasaObject.getUrl();
        } catch (IOException e) {
            result = "";
        }
        return result;
    }

}
