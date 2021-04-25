package com.algorithms.study.consistenthash;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-04-10
 * @Description 数据倾斜
 * 如果节点的数量很少，而hash环空间很大（一般是 0 ~ 2^32），直接进行一致性hash上去，大部分情况下节点在环上的位置会很不均匀，
 * 挤在某个很小的区域。最终对分布式缓存造成的影响就是，集群的每个实例上储存的缓存数据量不一致，会发生严重的数据倾斜。
 * <p>
 * 缓存雪崩
 * 如果每个节点在环上只有一个节点，那么可以想象，当某一集群从环中消失时，它原本所负责的任务将全部交由顺时针方向的下一个集群处理。
 * 例如，当group0退出时，它原本所负责的缓存将全部交给group1处理。这就意味着group1的访问压力会瞬间增大。
 * 设想一下，如果group1因为压力过大而崩溃，那么更大的压力又会向group2压过去，最终服务压力就像滚雪球一样越滚越大，最终导致雪崩。
 * <p>
 * 引入虚拟节点
 * 解决上述两个问题最好的办法就是扩展整个环上的节点数量，因此我们引入了虚拟节点的概念。
 * 一个实际节点将会映射多个虚拟节点，这样Hash环上的空间分割就会变得均匀
 * <p>
 * 优雅缩扩容
 * <p>
 * <p>
 * 缓存服务器对于性能有着较高的要求，因此我们希望在扩容时新的集群能够较快的填充好数据并工作。
 * 但是从一个集群启动，到真正加入并可以提供服务之间还存在着不小的时间延迟，要实现更优雅的扩容，我们可以从两个方面出发：
 * 1：高频Key预热
 * 2：历史Hash环保留
 * <p>
 * 1.熔断机制
 * <p>
 * 缩容后，剩余各个节点上的访问压力都会有所增加，此时如果某个节点因为压力过大而宕机，就可能会引发连锁反应。因此作为兜底方案，应当给每个集群设立对应熔断机制来保护服务的稳定性。
 * <p>
 * 2.多集群LB的更新延迟
 * <p>
 * 这个问题在缩容时比较严重，如果你使用一个集群来作为负载均衡，并使用一个配置服务器比如ConfigServer来推送集群状态以构建Hash环，
 * 那么在某个集群退出时这个状态并不一定会被立刻同步到所有的LB上，这就可能会导致一个暂时的调度不一致
 * <p>
 * 1.使用HashSlot :Redis Cluster采用HashSlot来实现Key值的均匀分布和实例的增删管理
 * 2.P2P节点寻找 :
 * 现在我们考虑如何实现去中心化的访问，也就是说无论访问集群中的哪个节点，你都能够拿到想要的数据。其实这有点类似于路由器的路由表，具体说来就是：
 * <p>
 * * 每个节点都保存有完整的HashSlot - 节点映射表，也就是说，每个节点都知道自己拥有哪些Slot，以及某个确定的Slot究竟对应着哪个节点。
 * <p>
 * * 无论向哪个节点发出寻找Key的请求，该节点都会通过CRC(Key) % 16384计算该Key究竟存在于哪个Slot，并将请求转发至该Slot所在的节点。
 * <p>
 * 总结一下就是两个要点：映射表和内部转发，这是通过著名的**Gossip协议**来实现的
 */
public class ConsistentHash {

    @Test
    public void testWithoutVirNode() {
        // 生成随机数进行测试
        Map<String, Integer> resMap = new HashMap<>();

        for (int i = 0; i < 100000; i++) {
            Integer widgetId = (int) (Math.random() * 10000);
            String server = ConsistentHashingWithoutVirtualNode.getServer(widgetId.toString());
            if (resMap.containsKey(server)) {
                resMap.put(server, resMap.get(server) + 1);
            } else {
                resMap.put(server, 1);
            }
        }

        resMap.forEach(
                (k, v) -> {
                    System.out.println("group " + k + ": " + v + "(" + v / 1000.0D + "%)");
                }
        );
    }

    @Test
    public void testWithVirNode() {
        // 生成随机数进行测试
        Map<String, Integer> resMap = new HashMap<>();

        for (int i = 0; i < 100000; i++) {
            Integer widgetId = i;
            String group = ConsistentHashingWithVirtualNode.getServer(widgetId.toString());
            if (resMap.containsKey(group)) {
                resMap.put(group, resMap.get(group) + 1);
            } else {
                resMap.put(group, 1);
            }
        }

        resMap.forEach(
                (k, v) -> {
                    System.out.println("group " + k + ": " + v + "(" + v / 100000.0D + "%)");
                }
        );

    }

    @Test
    public void testRefreshHash() {
        testWithVirNode();
        ConsistentHashingWithVirtualNode.removeGroup("192.168.0.4:111");
        System.out.println("after--------remove");
        testWithVirNode();
    }
}
