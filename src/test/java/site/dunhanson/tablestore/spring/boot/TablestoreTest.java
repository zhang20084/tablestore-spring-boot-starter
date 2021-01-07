package site.dunhanson.tablestore.spring.boot;

import cn.hutool.json.JSONUtil;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.search.SearchQuery;
import com.alicloud.openservices.tablestore.model.search.SearchRequest;
import com.alicloud.openservices.tablestore.model.search.SearchResponse;
import com.alicloud.openservices.tablestore.model.search.query.MatchQuery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import site.dunhanson.tablestore.spring.boot.constant.GsonType;
import site.dunhanson.tablestore.spring.boot.entity.Document;
import site.dunhanson.tablestore.spring.boot.entity.PageInfo;
import site.dunhanson.tablestore.spring.boot.utils.TableStoreUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablestoreTest {

    @Test
    public void testMatchQuery() {
        // Query
        MatchQuery query = new MatchQuery();
        query.setFieldName("docid");
        query.setText("129226674");

        // SearchQuery
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);
        searchQuery.setGetTotalCount(true);

        // SearchRequest
        SearchRequest request = new SearchRequest("document", "document_index", searchQuery);
        SearchRequest.ColumnsToGet columnsToGet = new SearchRequest.ColumnsToGet();
        columnsToGet.setReturnAll(true);
        request.setColumnsToGet(columnsToGet);

        PageInfo<Document> pageInfo = new PageInfo<>(1, 20);
        search(request, pageInfo, Document.class);
        for(Document document : pageInfo.getRecords()) {
            System.out.println(document);
        }
    }

    public <T> void search(SearchRequest request, PageInfo<T> pageInfo, Class<T> clazz) {
        SearchQuery query = request.getSearchQuery();
        TableStoreUtils.setPageInfoInSearchQuery(query, pageInfo);
        // client
        SyncClient client = TestUtils.getSyncClient();
        // response
        SearchResponse response = client.search(request);
        // 获取结果
        List<T> records = TableStoreUtils.rowsToBeans(response.getRows(), clazz);
        pageInfo.setRecords(records);
        pageInfo.setTotal(response.getTotalCount());
    }

}
