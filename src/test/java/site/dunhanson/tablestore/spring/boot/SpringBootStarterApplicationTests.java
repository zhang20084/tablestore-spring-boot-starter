package site.dunhanson.tablestore.spring.boot;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.search.query.MatchQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.dunhanson.tablestore.spring.boot.core.TableStoreTemplate;
import site.dunhanson.tablestore.spring.boot.entity.PageInfo;
import site.dunhanson.tablestore.spring.boot.test.entity.Document;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootStarterApplicationTests {
    @Resource
    private TableStoreTemplate tableStoreTemplate;

    @Test
    public void start() {
        // Query
        MatchQuery query = new MatchQuery();
        query.setFieldName("docid");
        query.setText("129226674");
        // Search
        PageInfo<Document> pageInfo = tableStoreTemplate.search(new PageInfo<Document>(){}, query);
        for(Document document : pageInfo.getRecords()) {
            System.out.println(document);
        }
    }

}
