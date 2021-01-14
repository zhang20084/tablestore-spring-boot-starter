package site.dunhanson.tablestore.spring.boot.test;

import cn.hutool.core.annotation.AnnotationUtil;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import site.dunhanson.tablestore.spring.boot.annotation.PrimaryKey;
import site.dunhanson.tablestore.spring.boot.test.entity.Document;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.FileHandler;

public class AnnotationTest {
    @Test
    public void test() {
        Document document = new Document();
        /*Field[] fields = document.getClass().getDeclaredFields();
        for(Field field : fields) {
            System.out.println(field.getName());
            field.setAccessible(true);
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if(primaryKey != null) {
                System.out.println(primaryKey.name());
            }
        }*/
        Map<String, Object> annotationValueMap = AnnotationUtil.getAnnotationValueMap(Document.class, PrimaryKey.class);
        System.out.println(annotationValueMap.size());
    }
}
