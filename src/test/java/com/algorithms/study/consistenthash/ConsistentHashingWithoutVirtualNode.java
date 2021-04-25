package com.algorithms.study.consistenthash;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-04-10
 * @Description
 */
public class ConsistentHashingWithoutVirtualNode {

    /**
     * 集群地址列表
     */
    private static String[] groups = {
            "192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111",
            "192.168.0.3:111", "192.168.0.4:111"
    };

    /**
     * 用于保存Hash环上的节点
     */
    private static SortedMap<Integer, String> sortedMap = new TreeMap<>();

    /**
     * 初始化，将所有的服务器加入Hash环中
     */
    static {
        // 使用红黑树实现，插入效率比较差，但是查找效率极高
        for (String group : groups) {
            int hash = HashUtil.getHash(group);
            System.out.println("[" + group + "] launched @ " + hash);
            sortedMap.put(hash, group);
        }
    }

    /**
     * 计算对应的widget加载在哪个group上
     *
     * @param widgetKey
     * @return
     */
    public static String getServer(String widgetKey) {
        int hash = HashUtil.getHash(widgetKey);
        // 只取出所有大于该hash值的部分而不必遍历整个Tree
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        if (subMap == null || subMap.isEmpty()) {
            // hash值在最尾部，应该映射到第一个group上
            return sortedMap.get(sortedMap.firstKey());
        }
        return subMap.get(subMap.firstKey());
    }

}
