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
        SearchRequest searchRequest = new SearchRequest("document", "document_index", searchQuery);
        SearchRequest.ColumnsToGet columnsToGet = new SearchRequest.ColumnsToGet();
        columnsToGet.setReturnAll(true);
        searchRequest.setColumnsToGet(columnsToGet);

        // search
        SyncClient client = TestUtils.getSyncClient();
        SearchResponse response = client.search(searchRequest);
        System.out.println("totalCount:" + response.getTotalCount());
        List<Row> rows = response.getRows();
        List<Map<String, Object>> list = new ArrayList<>();
        for(Row row : rows) {
            // 主键
            PrimaryKey primaryKey = row.getPrimaryKey();
            PrimaryKeyColumn[] primaryKeyColumns = primaryKey.getPrimaryKeyColumns();
            for(int i = 0; i < primaryKeyColumns.length; i++) {
                PrimaryKeyColumn primaryKeyColumn = primaryKeyColumns[i];
                System.out.println(primaryKeyColumn.getName());
            }
            System.out.println();
            // 字段
            Column[] columns = row.getColumns();
            Map<String, Object> map = new HashMap<>();
            for(int i = 0; i < columns.length; i++) {
                Column column = columns[i];
                // 字段名
                String name = column.getName();
                // 字段值
                ColumnValue columnValue = column.getValue();
                // 如果是字符串判断是否是json，把json转换成List<Map<String, Object>>
                if(columnValue.getType() == ColumnType.STRING) {
                    String jsonStr = columnValue.asString();
                    if(JSONUtil.isJson(jsonStr)) {
                        Type type = new TypeToken<List<Map<String, Object>>>(){}.getType();
                        map.put(name, new Gson().fromJson(jsonStr, type));
                    }
                } else {
                    map.put(name, columnValue.getValue());
                }
            }
            System.out.println();
        }
    }
}
