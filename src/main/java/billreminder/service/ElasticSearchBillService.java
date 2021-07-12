package billreminder.service;

import billreminder.model.Bill;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ElasticSearchBillService {
    @Autowired
    private ESClientProvider provider;

    public String addBill(Bill bill) throws IOException {
        RestHighLevelClient client  = provider.getClient();
        IndexRequest request = new IndexRequest("bill");
        request.id(bill.getBillCode());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> empJson = mapper.convertValue(bill, new TypeReference<Map<String, Object>>() {
        });
        request.source(empJson, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return response.toString();
    }

   public String deleteBillByBillCode(String billCode) throws IOException {
        RestHighLevelClient client = provider.getClient();
        DeleteRequest request = new DeleteRequest("bill", billCode);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        return response.toString();
    }

    /*   public String deleteBillById(String id) throws IOException {
        RestHighLevelClient client = provider.getClient();
        DeleteRequest request = new DeleteRequest("bill", id);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        return response.toString();
    }*/


    public SearchHit[] getBillByBillName(String name) throws IOException {
        RestHighLevelClient client = provider.getClient();
        SearchRequest request = new SearchRequest("bill");
        System.out.println("--Matching query by Bill Name--");

        //SearchSourceBuilder source = new SearchSourceBuilder().query(QueryBuilders.wrapperQuery("electricity"));

        SearchSourceBuilder source = new SearchSourceBuilder();
        source.query(QueryBuilders.matchQuery("billName", name));
        request.source(source);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] searchHits = response.getHits().getHits();
        return searchHits;
    }

    public String updateBillByBillCode(Bill bill) throws IOException {
        RestHighLevelClient client = provider.getClient();
        UpdateRequest request = new UpdateRequest("bill", bill.getBillCode());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonString = mapper.convertValue(bill, new TypeReference<Map<String, Object>>() {
        });
        request.doc(jsonString, XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        return response.toString();
    }
}
