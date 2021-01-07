package site.dunhanson.tablestore.spring.boot.constant;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 *
 * 2020-01-07
 * @author dunhanson
 */
public class GsonType {
    /**
     * List<Map<String, Object>>
     */
    public static final Type LIST_MAP_WITH_STRING_OBJECT = new TypeToken<List<Map<String, Object>>>(){}.getType();

}
