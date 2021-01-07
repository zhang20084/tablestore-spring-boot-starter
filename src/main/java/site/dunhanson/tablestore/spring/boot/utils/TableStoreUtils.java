package site.dunhanson.tablestore.spring.boot.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.alicloud.openservices.tablestore.model.*;
import com.google.gson.Gson;
import site.dunhanson.tablestore.spring.boot.constant.GsonType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TableStore工具类
 * 2020-01-07
 * @author dunhanson
 */
public class TableStoreUtils {
    /**
     * rows转换成beans
     * @param rows 行对象集合
     * @param clazz 需要转换成对象的类
     * @param <T> 泛型
     * @return 对象List集合
     */
    public static <T> List<T> rowsToBeans(List<Row> rows, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if(rows != null) {
            for(Row row : rows) {
                list.add(rowToBean(row, clazz));
            }
        }
        return list;
    }

    /**
     * row转换成bean
     * @param row 行对象
     * @param clazz 需要转换的类
     * @param <T> 泛型
     * @return bean
     */
    public static <T> T rowToBean(Row row, Class<T> clazz) {
        return BeanUtil.mapToBean(rowToMap(row), clazz, true, CopyOptions.create());
    }

    /**
     * row转换成map
     * 第一步获取主键字段
     * 第二步获取非主键字段
     * @param row 行对象
     * @return map对象
     */
    public static Map<String, Object> rowToMap(Row row) {
        Map<String, Object> map = new HashMap<>();
        Gson gson = new Gson();
        // 主键
        PrimaryKey primaryKey = row.getPrimaryKey();
        PrimaryKeyColumn[] primaryKeyColumns = primaryKey.getPrimaryKeyColumns();
        for (PrimaryKeyColumn column : primaryKeyColumns) {
            map.put(column.getName(), column.getValue());
        }
        // 字段
        Column[] columns = row.getColumns();
        for (Column column : columns) {
            // 字段名
            String name = column.getName();
            // 字段值
            ColumnValue columnValue = column.getValue();
            // 如果是字符串判断是否是json，把json转换成List<Map<String, Object>>
            if (columnValue.getType() == ColumnType.STRING) {
                String jsonStr = columnValue.asString();
                if (JSONUtil.isJson(jsonStr)) {
                    map.put(name, gson.fromJson(jsonStr, GsonType.LIST_MAP_WITH_STRING_OBJECT));
                } else {
                    map.put(name, columnValue.getValue());
                }
            } else {
                map.put(name, columnValue.getValue());
            }
        }
        return map;
    }
}
