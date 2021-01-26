package site.dunhanson.tablestore.spring.boot.util;

import cn.hutool.core.bean.BeanUtil;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;
import com.alicloud.openservices.tablestore.model.RowPutChange;
import org.apache.commons.lang3.StringUtils;
import site.dunhanson.tablestore.spring.boot.annotation.PrimaryKey;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * TableStore工具类
 * 2020-01-24
 * @author dunhanson
 */
public class TableStoreUtils {

    /**
     * 获取主键map
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> getPrimaryKeyMap(T entity) {
        Map<String, Object> map = new HashMap<>();
        for(Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if(primaryKey == null || StringUtils.isBlank(primaryKey.name())) {
                continue;
            }
            try {
                map.put(primaryKey.name(), field.get(entity));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * bean转换成
     * @param entity
     * @param <T>
     * @return
     */
    public <T> RowPutChange beanToRow(T entity) {
        Map<String, Object> primaryKeyMap = getPrimaryKeyMap(entity);
        Map<String, Object> map = BeanUtil.beanToMap(entity, true, true);
        // 主键添加
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        for(String key : primaryKeyMap.keySet()) {
            map.remove(key);
            primaryKeyBuilder.addPrimaryKeyColumn(key, PrimaryKeyValue.fromColumn(ColumnValue))
        }
        return null;
    }

}
