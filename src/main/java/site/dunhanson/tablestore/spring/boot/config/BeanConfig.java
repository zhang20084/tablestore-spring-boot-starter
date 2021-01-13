package site.dunhanson.tablestore.spring.boot.config;

import cn.hutool.core.bean.BeanUtil;
import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.SyncClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.dunhanson.tablestore.spring.boot.config.properties.AliyunProperties;
import site.dunhanson.tablestore.spring.boot.config.properties.ClientConfigurationProperties;
import site.dunhanson.tablestore.spring.boot.config.properties.TablestoreProperties;
import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(
        value = {AliyunProperties.class, TablestoreProperties.class, ClientConfigurationProperties.class}
)
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
        SyncClient syncClient;
        if(tableStore.getClientConfiguration() != null) {
            ClientConfiguration configuration = new ClientConfiguration();
            BeanUtil.copyProperties(tableStore.getClientConfiguration(), configuration);
            syncClient = new SyncClient(tableStore.getEndPoint(), tableStore.getAccessKeyId(),
                    tableStore.getAccessKeySecret(), tableStore.getInstanceName(), configuration);
        } else {
            syncClient = new SyncClient(tableStore.getEndPoint(), tableStore.getAccessKeyId(),
                    tableStore.getAccessKeySecret(), tableStore.getInstanceName());
        }
        return syncClient;
    }

}
