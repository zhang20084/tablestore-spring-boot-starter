package site.dunhanson.tablestore.spring.boot.test;

import cn.hutool.core.bean.BeanUtil;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import site.dunhanson.tablestore.spring.boot.annotation.PrimaryKey;
import site.dunhanson.tablestore.spring.boot.test.entity.Document;
import site.dunhanson.tablestore.spring.boot.util.TableStoreUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(TableStoreUtils.getPrimaryKeyMapFromBean(document));
    }


}
