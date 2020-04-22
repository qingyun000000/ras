package com.zy.zyrasclient.remote;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author wuhailong
 */
public class TestRemoteClassImpl implements TestRemoteClass{

    @Override
    public Map<String, String> test() {
        Map<String, String> map = new HashMap<>();
        map.put("success", "false");
        map.put("message", "服务请求失败降级");
        return map;
    }
    
}
