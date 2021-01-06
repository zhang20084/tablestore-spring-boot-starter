package site.dunhanson.tablestore.spring.boot;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanTest {
    @Test
    public void testBean() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "dunhanson");
        map.put("password", "123456");
        map.put("create_time", LocalDateTime.now());
        User user = BeanUtil.mapToBean(map, User.class, false, CopyOptions.create());
        System.out.println(user);
    }

    @Test
    public void testBean2() {
        String jsonStr = "[{\"bidding_budget\":1.800469664E7,\"win_bid_price\":1.73828123E7,\"win_tenderer\":\"云浮市公共资源交易中心\"}]";
        JSONArray jsonArray = JSONUtil.parseArray(jsonStr);
        List<SubDocsJson> subDocsJsons = JSONUtil.toList(jsonStr, SubDocsJson.class);
        System.out.println(subDocsJsons);
    }

    @Test
    public void testBean3() {
        String jsonStr = "[{\"bidding_budget\":1.800469664E7,\"win_bid_price\":1.73828123E7,\"win_tenderer\":\"云浮市公共资源交易中心\"}]";
        Type type = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> list = new Gson().fromJson(jsonStr, type);
        System.out.println(list.size());
    }
}

@Data
class SubDocsJson {
    private String winTenderer;
    private String secondTenderer;
    private String thirdTenderer;
    private Double biddingBudget;
    private Double winBidPrice;
}

@Data
class User {
    private String username;
    private String password;
    private LocalDateTime createTime;
}
