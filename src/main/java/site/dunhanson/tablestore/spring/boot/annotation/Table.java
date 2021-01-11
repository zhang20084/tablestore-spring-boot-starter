package site.dunhanson.tablestore.spring.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 表名
     * @return 表名
     */
    String tableName() default "";

    /**
     * 索引名称
     * @return 索引名称
     */
    String indexName() default "";
}
