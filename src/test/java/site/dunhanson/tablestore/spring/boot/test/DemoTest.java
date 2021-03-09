package site.dunhanson.tablestore.spring.boot.test;

import cn.hutool.core.annotation.AnnotationUtil;
import org.apache.commons.lang3.AnnotationUtils;
import org.junit.Test;
import site.dunhanson.tablestore.spring.boot.annotation.Table;
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

    @Test
    public void test2() {
        Document document = new Document();
        document.setDocid(100L);
        document.setPartitionkey(345L);
        document.setDoctitle("测试标题");
        document.setDochtmlcon("测试内容");
        document.setPublishtime("2021-01-24 00:00:00");
        document.setAgencyPhone("123456789");
        System.out.println(TableStoreUtils.getPrimaryKeyMap(document));
    }

    @Test
    public void test3() {
        Class<Document> clazz = Document.class;
        Table table = clazz.getAnnotation(Table.class);
        System.out.println(table.indexNameIndex());
        AnnotationUtil.setValue(table, "indexNameIndex", 1);
        System.out.println(table.indexNameIndex());
    }





}
