package site.dunhanson.tablestore.spring.boot.test;

import org.junit.Test;
import site.dunhanson.tablestore.spring.boot.test.entity.Document;
import site.dunhanson.tablestore.spring.boot.util.TableStoreUtils;

public class DemoTest {
    @Test
    public void test() {
        Document document = new Document();
        document.setDocid(100L);
        document.setPartitionkey(345L);
        document.setDoctitle("测试标题");
        document.setDochtmlcon("测试内容");
        document.setPublishtime("2021-01-24 00:00:00");
        document.setAgencyPhone("123456789");
        System.out.println(TableStoreUtils.getPrimaryKeyMap(document));
    }


}
