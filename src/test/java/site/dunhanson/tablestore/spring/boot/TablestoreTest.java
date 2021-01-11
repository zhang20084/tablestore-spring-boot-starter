package site.dunhanson.tablestore.spring.boot;

import com.alicloud.openservices.tablestore.model.search.SearchQuery;
import com.alicloud.openservices.tablestore.model.search.SearchRequest;
import com.alicloud.openservices.tablestore.model.search.query.MatchQuery;
import org.junit.jupiter.api.Test;
import site.dunhanson.tablestore.spring.boot.annotation.Table;
import site.dunhanson.tablestore.spring.boot.test.entity.Document;
import site.dunhanson.tablestore.spring.boot.entity.PageInfo;
import site.dunhanson.tablestore.spring.boot.utils.TableStoreUtils;

import java.lang.annotation.Annotation;

public class TablestoreTest {

    @Test
    public void testMatchQuery() {
        // Test
        TableStoreUtils.syncClient = TestUtils.getSyncClient();

        // Query
        MatchQuery query = new MatchQuery();
        query.setFieldName("docid");
        query.setText("129226674");

        // Search
        PageInfo<Document> pageInfo = TableStoreUtils.search(new PageInfo<Document>(1, 20){}, query);
        for(Document document : pageInfo.getRecords()) {
            System.out.println(document);
        }
    }

    @Test
    public void testAnnotation() {
        Table annotation = Document.class.getAnnotation(Table.class);
        System.out.println(annotation.tableName());
        System.out.println(annotation.indexName());
    }

}
