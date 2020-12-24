package site.dunhanson.tablestore.spring.boot.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dunhanson
 * 阿里云配置
 */
@Data
@ConfigurationProperties(prefix = "aliyun")
public class AliyunProperties {
    private TablestoreProperties tableStore;
}
