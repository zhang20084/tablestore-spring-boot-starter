package site.dunhanson.tablestore.spring.boot;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.Row;
import com.alicloud.openservices.tablestore.model.search.SearchQuery;
import com.alicloud.openservices.tablestore.model.search.SearchRequest;
import com.alicloud.openservices.tablestore.model.search.SearchResponse;
import com.alicloud.openservices.tablestore.model.search.query.MatchQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TablestoreTest {

    @Test
    public void testMatchQuery() {
        // Query
        MatchQuery query = new MatchQuery();
        query.setFieldName("doctitle");
        query.setText("档案");
        // SearchQuery
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);
        searchQuery.setGetTotalCount(true);
        // SearchRequest
        SearchRequest searchRequest = new SearchRequest("document", "document_index", searchQuery);
        // search
        SyncClient client = TestUtils.getSyncClient();
        SearchResponse response = client.search(searchRequest);
        System.out.println("totalCount:" + response.getTotalCount());
        List<Row> rows = response.getRows();
        rows.forEach(obj->{
            System.out.println(obj.getColumnsMap());
        });
    }
}
