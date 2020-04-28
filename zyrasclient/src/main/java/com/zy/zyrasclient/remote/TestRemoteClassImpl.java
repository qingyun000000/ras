package com.zy.zyrasclient.remote;

import java.util.HashMap;
import java.util.Map;
import com.zy.zyrasclient.TestRequest;
import com.zy.zyrasclient.Test2Request;

/**
 *
 * @author wuhailong
 */
public class TestRemoteClassImpl implements TestRemoteClass{

    @Override
    public Map<String, String> test(TestRequest test, Test2Request test2) {
        Map<String, String> map = new HashMap<>();
        map.put("success", "false");
        map.put("message", "服务请求失败降级");
        return map;
    }
    
}
