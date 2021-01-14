package site.dunhanson.tablestore.spring.boot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键
 * 2020-01-14
 * @author dunhanson
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
    /**
     * 主键名称
     * @return
     */
    String name() default "";
}
