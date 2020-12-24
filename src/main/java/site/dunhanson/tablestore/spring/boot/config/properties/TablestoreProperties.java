package site.dunhanson.tablestore.spring.boot.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dunhanson
 * Tablestore配置
 */
@Data
@ConfigurationProperties(prefix = "tablestore")
public class TablestoreProperties {
    /**
     * TableStore服务的endpoint
     */
    private String endPoint;
    /**
     * 访问TableStore服务的Access ID
     */
    private String accessKeyId;
    /**
     * 访问TableStore服务的Access Key
     */
    private String accessKeySecret;
    /**
     * 访问TableStore服务的实例名称
     */
    private String instanceName;
}
