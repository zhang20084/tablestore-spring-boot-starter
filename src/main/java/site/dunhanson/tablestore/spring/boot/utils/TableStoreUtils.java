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
     * @param rows
     * @param clazz
     * @param <T>
     * @return
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
     * @param row
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T rowToBean(Row row, Class<T> clazz) {
        return BeanUtil.mapToBean(rowToMap(row), clazz, true, CopyOptions.create());
    }

    /**
     * rows转换成list map
     * @param rows
     * @return
     */
    public static List<Map<String, Object>> rowsToListMap(List<Row> rows) {
        List<Map<String, Object>> list = new ArrayList<>();
        if(rows != null) {
            for(Row row : rows) {
                list.add(rowToMap(row));
            }
        }
        return list;
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
        for(int i = 0; i < primaryKeyColumns.length; i++) {
            PrimaryKeyColumn column = primaryKeyColumns[i];
            map.put(column.getName(), column.getValue());
        }
        // 字段
        Column[] columns = row.getColumns();
        for(int i = 0; i < columns.length; i++) {
            Column column = columns[i];
            // 字段名
            String name = column.getName();
            // 字段值
            ColumnValue columnValue = column.getValue();
            // 如果是字符串判断是否是json，把json转换成List<Map<String, Object>>
            if(columnValue.getType() == ColumnType.STRING) {
                String jsonStr = columnValue.asString();
                if(JSONUtil.isJson(jsonStr)) {
                    map.put(name, gson.fromJson(jsonStr, GsonType.LIST_MAP_WITH_STRING_OBJECT));
                }
            } else {
                map.put(name, columnValue.getValue());
            }
        }
        return map;
    }
}
