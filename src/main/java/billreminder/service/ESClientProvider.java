package billreminder.service;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

@Service
public class ESClientProvider {
    private static RestHighLevelClient client;

    public RestHighLevelClient getClient() {
        if(client==null) {
            // creating new client if does not already exist
            client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        }
        return client;
    }
}
