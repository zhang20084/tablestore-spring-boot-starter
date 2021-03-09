package site.dunhanson.tablestore.spring.boot.util;

import cn.hutool.core.annotation.AnnotationUtil;
import site.dunhanson.tablestore.spring.boot.annotation.Table;

/**
 * 注解工具类
 * 2020-01-14
 * @author dunhanson
 */
public class AnnotationUtils {
    private String INDEX_NAME_INDEX = "indexNameIndex";

    /**
     * 更换索引名称
     * @param clazz
     * @param index
     */
    public void changeIndexName(Class<?> clazz, int index) {
        Table table = clazz.getAnnotation(Table.class);
        AnnotationUtil.setValue(table, INDEX_NAME_INDEX, index);
    }
}
