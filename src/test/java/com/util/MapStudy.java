package com.util;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuedui.zhao
 * @create 2018-09-19
 */
public class MapStudy {
    
    @Test
    public void testCurrentHashMap() {
        Map<String,Object> map = new ConcurrentHashMap<>(24);
    }
}
