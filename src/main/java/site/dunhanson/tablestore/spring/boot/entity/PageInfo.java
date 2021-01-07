package site.dunhanson.tablestore.spring.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * 分页对象
 * 2020-01-07
 * @author dunhanson
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> {
    /**
     * 分页
     */
    private Integer size = 10;
    /**
     * 现页
     */
    private Integer current = 1;
    /**
     * 记录
     */
    private Integer index = 0;
    /**
     * 页数
     */
    private Long pages = 0L;
    /**
     * 总数
     */
    private Long total = 0L;
    /**
     * 数据
     */
    private List<T> records = Collections.emptyList();

    public PageInfo(Integer current, Integer size) {
        this.size = size;
        this.current = current;
        this.index = (current - 1) * size;
    }
}
