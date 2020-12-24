package site.dunhanson.tablestore.spring.boot.config;

import com.alicloud.openservices.tablestore.AsyncClient;
import com.alicloud.openservices.tablestore.SyncClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.dunhanson.tablestore.spring.boot.config.properties.AliyunProperties;
import site.dunhanson.tablestore.spring.boot.config.properties.TablestoreProperties;
import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(value = {AliyunProperties.class, TablestoreProperties.class})
public class BeanConfig {
    @Resource
    private AliyunProperties aliyunProperties;

    /**
     * 同步实例
     * @return
     */
    @Bean
    public SyncClient syncClient() {
        TablestoreProperties tableStore = aliyunProperties.getTableStore();
        return new SyncClient(tableStore.getEndPoint(),
                tableStore.getAccessKeyId(),
                tableStore.getAccessKeySecret(),
                tableStore.getInstanceName());
    }

    /**
     * 异步实例
     * @return
     */
    @Bean
    public AsyncClient asyncClient() {
        TablestoreProperties tableStore = aliyunProperties.getTableStore();
        return new AsyncClient(tableStore.getEndPoint(),
                tableStore.getAccessKeyId(),
                tableStore.getAccessKeySecret(),
                tableStore.getInstanceName());
    }
}
