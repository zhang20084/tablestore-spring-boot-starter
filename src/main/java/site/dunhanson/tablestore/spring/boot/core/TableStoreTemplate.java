package site.dunhanson.tablestore.spring.boot.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.search.SearchQuery;
import com.alicloud.openservices.tablestore.model.search.SearchRequest;
import com.alicloud.openservices.tablestore.model.search.SearchResponse;
import com.alicloud.openservices.tablestore.model.search.query.Query;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import site.dunhanson.tablestore.spring.boot.annotation.Table;
import site.dunhanson.tablestore.spring.boot.constant.GsonType;
import site.dunhanson.tablestore.spring.boot.entity.PageInfo;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TableStoreTemplate
 * 2020-01-13
 * @author dunhanson
 */
@Component
public class TableStoreTemplate {
    @Resource
    private SyncClient syncClient;

    /**
     * 查询
     * @param pageInfo PageInfo
     * @param searchQuery SearchQuery
     * @param columnsToGet SearchRequest.ColumnsToGet
     * @param <T> 泛型
     * @return PageInfo
     */
    public <T> PageInfo<T> search(PageInfo<T> pageInfo, SearchQuery searchQuery, SearchRequest.ColumnsToGet columnsToGet) {
        // Class
        Class<T> clazz = getClass(pageInfo);
        // SearchRequest
        SearchRequest searchRequest = (columnsToGet == null ? getSearchRequest(clazz, searchQuery) : getSearchRequest(clazz, searchQuery, columnsToGet));
        // 设置分页信息
        setPageInfoInSearchQuery(searchRequest.getSearchQuery(), pageInfo);
        // response
        SearchResponse response = syncClient.search(searchRequest);
        // 获取结果
        List<T> records = rowsToBeans(response.getRows(), clazz);
        // 设置返回记录
        pageInfo.setRecords(records);
        // 设置总记录数
        pageInfo.setTotal(response.getTotalCount());
        // 返回PageInfo
        return pageInfo;
    }

    /**
     * 查询
     * @param pageInfo PageInfo
     * @param searchQuery SearchQuery
     * @param <T> 泛型
     * @return PageInfo
     */
    public <T> PageInfo<T> search(PageInfo<T> pageInfo, SearchQuery searchQuery) {
        return search(pageInfo, searchQuery, null);
    }

    /**
     * 搜索
     * @param pageInfo PageInfo
     * @param query Query
     * @param columnsToGet SearchRequest.ColumnsToGet
     * @param <T> 泛型
     * @return PageInfo
     */
    public <T> PageInfo<T> search(PageInfo<T> pageInfo, Query query, SearchRequest.ColumnsToGet columnsToGet) {
        SearchQuery searchQuery = getSearchQuery(query);
        return search(pageInfo, searchQuery, columnsToGet);
    }

    /**
     * 搜索
     * @param pageInfo 分页信息
     * @param query 查询对象
     * @param <T> 泛型
     * @return PageInfo
     */
    public <T> PageInfo<T> search(PageInfo<T> pageInfo, Query query) {
        return search(pageInfo, getSearchQuery(query));
    }

    /**
     * 获取SearchRequest
     * @param clazz 类
     * @param searchQuery SearchQuery
     * @param columnsToGet SearchRequest.ColumnsToGet
     * @param <T> 泛型
     * @return SearchRequest
     */
    public <T> SearchRequest getSearchRequest(Class<T> clazz, SearchQuery searchQuery, SearchRequest.ColumnsToGet columnsToGet) {
        Table table = clazz.getAnnotation(Table.class);
        if(table == null) {
            throw new NullPointerException("can't found Table annotation");
        }
        // 索引名称下标
        int indexNameIndex = table.indexNameIndex();
        // SearchRequest
        SearchRequest searchRequest = new SearchRequest(table.tableName(), table.indexName()[indexNameIndex], searchQuery);
        // 设置返回列
        searchRequest.setColumnsToGet(columnsToGet);
        return searchRequest;
    }

    /**
     * 获取默认的SearchRequest
     * @param clazz Class
     * @param searchQuery SearchQuery
     * @param <T> 泛型
     * @return SearchRequest
     */
    public <T> SearchRequest getSearchRequest(Class<T> clazz, SearchQuery searchQuery) {
        // 设置返回所有列
        SearchRequest.ColumnsToGet columnsToGet = new SearchRequest.ColumnsToGet();
        columnsToGet.setReturnAll(true);
        return getSearchRequest(clazz, searchQuery, columnsToGet);
    }

    /**
     * 获取默认的SearchQuery
     * @param query Query
     * @return SearchQuery
     */
    public SearchQuery getSearchQuery(Query query) {
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setQuery(query);
        searchQuery.setGetTotalCount(true);
        return searchQuery;
    }

    /**
     * 获取PageInfo泛型Class
     * @param pageInfo PageInfo
     * @param <T> 泛型
     * @return 泛型Class
     */
    public <T> Class<T> getClass(PageInfo<T> pageInfo) {
        Type type = ((ParameterizedType)pageInfo.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            return (Class<T>) Class.forName(type.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 设置分页信息
     * @param query SearchQuery
     * @param pageInfo PageInfo分页信息对象
     * @param <T> 泛型
     */
    public <T> void setPageInfoInSearchQuery(SearchQuery query, PageInfo<T> pageInfo) {
        query.setLimit(pageInfo.getSize());
        query.setOffset(pageInfo.getIndex());
    }

    /**
     * rows转换成beans
     * @param rows 行对象集合
     * @param clazz 需要转换成对象的类
     * @param <T> 泛型
     * @return 对象List集合
     */
    public <T> List<T> rowsToBeans(List<Row> rows, Class<T> clazz) {
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
    public <T> T rowToBean(Row row, Class<T> clazz) {
        return BeanUtil.mapToBean(rowToMap(row), clazz, true, CopyOptions.create());
    }

    /**
     * row转换成map
     * 第一步获取主键字段
     * 第二步获取非主键字段
     * @param row 行对象
     * @return map对象
     */
    public Map<String, Object> rowToMap(Row row) {
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
