package site.dunhanson.tablestore.spring.boot;

import com.alicloud.openservices.tablestore.SyncClient;

public class TestUtils {
    public static SyncClient getSyncClient() {
        final String endPoint = "";
        final String accessKeyId = "";
        final String accessKeySecret = "";
        final String instanceName = "";
        return new SyncClient(endPoint, accessKeyId, accessKeySecret, instanceName);
    }
}
