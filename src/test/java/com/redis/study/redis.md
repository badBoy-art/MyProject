Redis 单机QPS为几万，实现高可用 用到主从机制(主备切换)、集群(QPS可达到几十万)
Redis 持久化方案：rdb 定时(5分钟) fork一个子线程生成镜像文件，redis挂掉后重新加载时速度快但容易丢数据，aof：存储reids的执行命令，reids挂掉重启时比较慢但是不会丢数据

过期策略：定时删除+多行删除
1. 定时删除：redis默认每隔100毫秒随机抽取一些设置了过期时间的key，检查是否过期，过期就删除
2. 惰性删除：当获取某个key的时候，redis会校验该key是否设置了过期时间，如果过期了就删除，不返回任何东西
3. 内存淘汰机制：            
    * noeviction: 当内存不足以容纳新写入数据时，新写入操作会报错，这个一般没人用吧，实在是太恶心了。
    * allkeys-lru：当内存不足以容纳新写入数据时，在键空间中，移除最近最少使用的 key（这个是最常用的）。
    * allkeys-random：当内存不足以容纳新写入数据时，在键空间中，随机移除某个 key，这个一般没人用吧，为啥要随机，肯定是把最近最少使用的 key 给干掉啊。
    * volatile-lru：当内存不足以容纳新写入数据时，在设置了过期时间的键空间中，移除最近最少使用的 key（这个一般不太合适）。
    * volatile-random：当内存不足以容纳新写入数据时，在设置了过期时间的键空间中，随机移除某个 key。
    * volatile-ttl：当内存不足以容纳新写入数据时，在设置了过期时间的键空间中，有更早过期时间的 key 优先移除。
Redis主从机制：一主多从，主节点负责写，从节点负责读，主节点负责将数据同步到其它从节点
* redis 采用异步方式复制数据到 slave 节点，不过 redis2.8 开始，slave node 会周期性地确认自己每次复制的数据量；
* 一个 master node 是可以配置多个 slave node 的；
* slave node 也可以连接其他的 slave node；
* slave node 做复制的时候，不会 block master node 的正常工作；
* slave node 在做复制的时候，也不会 block 对自己的查询操作，它会用旧的数据集来提供服务；但是复制完成的时候，需要删除旧数据集，加载新数据集，这个时候就会暂停对外服务了；
* slave node 主要用来进行横向扩容，做读写分离，扩容的 slave node 可以提高读的吞吐量。
主从复制的核心：当启动一个 slave node 的时候，它会发送一个 PSYNC 命令给 master node。如果这是 slave node 初次连接到 master node，那么会触发一次 full resynchronization 全量复制。此时 master 会启动一个后台线程，开始生成一份 RDB 快照文件，同时还会将从客户端 client 新收到的所有写命令缓存在内存中。RDB 文件生成完毕后， master 会将这个 RDB 发送给 slave，slave 会先写入本地磁盘，然后再从本地磁盘加载到内存中，接着 master 会将内存中缓存的写命令发送到 slave，slave 也会同步这些数据。slave node 如果跟 master node 有网络故障，断开了连接，会自动重连，连接之后 master node 仅会复制给 slave 部分缺少的数据。
主从复制断点续传：master node 会在内存中维护一个 backlog，master 和 slave 都会保存一个 replica offset 还有一个 master run id，offset 就是保存在 backlog 中的。如果 master 和 slave 网络连接断掉了，slave 会让 master 从上次 replica offset 开始继续复制，如果没有找到对应的 offset，那么就会执行一次 resynchronization
过期key处理：从节点不会处理过期key，主节点过期了一个key，会模拟一条del命令发送给从节点
全量复制：
* master 执行 bgsave ，在本地生成一份 rdb 快照文件。
* master node 将 rdb 快照文件发送给 slave node，如果 rdb 复制时间超过 60秒（repl-timeout），那么 slave node 就会认为复制失败，可以适当调大这个参数(对于千兆网卡的机器，一般每秒传输 100MB，6G 文件，很可能超过 60s)
* master node 在生成 rdb 时，会将所有新的写命令缓存在内存中，在 slave node 保存了 rdb 之后，再将新的写命令复制给 slave node。
* 如果在复制期间，内存缓冲区持续消耗超过 64MB，或者一次性超过 256MB，那么停止复制，复制失败。
* client-output-buffer-limit slave 256MB 64MB 60
* slave node 接收到 rdb 之后，清空自己的旧数据，然后重新加载 rdb 到自己的内存中，同时基于旧的数据版本对外提供服务。
* 如果 slave node 开启了 AOF，那么会立即执行 BGREWRITEAOF，重写 AOF
增量复制：
* 如果全量复制过程中，master-slave 网络连接断掉，那么 slave 重新连接 master 时，会触发增量复制。
* master 直接从自己的 backlog 中获取部分丢失的数据，发送给 slave node，默认 backlog 就是 1MB。
* master 就是根据 slave 发送的 psync 中的 offset 来从 backlog 中获取数据的。
心跳：master每10秒发送一次心跳，slave每1秒发送一次心跳
异步复制：master 每次接收到写命令之后，先在内部写入数据，然后异步发送给 slave node。
sentinel-Redis哨兵：
* 集群监控：负责监控 redis master 和 slave 进程是否正常工作。
* 消息通知：如果某个 redis 实例有故障，那么哨兵负责发送消息作为报警通知给管理员。
* 故障转移：如果 master node 挂掉了，会自动转移到 slave node 上。
* 配置中心：如果故障转移发生了，通知 client 客户端新的 master 地址。
哨兵主备切换数据丢失：
*      异步复制导致数据丢失：master->slave 的复制是异步的，所以可能有部分数据还没复制到 slave，master 就宕机了，此时这部分数据就丢失了
*      脑裂导致的数据丢失：某个 master 所在机器突然脱离了正常的网络，跟其他 slave 机器不能连接，但是实际上 master 还运行着。此时哨兵可能就会认为 master 宕机了，然后开启选举，将其他 slave 切换成了 master。这个时候，集群里就会有两个 master ，也就是所谓的脑裂。旧 master 再次恢复的时候，会被作为一个 slave 挂到新的 master 上去，自己的数据会清空，重新从新的 master 复制数据。而新的 master 并没有后来 client 写入的数据，因此，这部分数据也就丢失了。
sdown 和 odown 转换机制：
* sdown 是主观宕机，就一个哨兵如果自己觉得一个 master 宕机了，那么就是主观宕机
* odown 是客观宕机，如果 quorum 数量的哨兵都觉得一个 master 宕机了，那么就是客观宕机
Slave-master 选举机制：
* 如果一个 slave 跟 master 断开连接的时间已经超过了 down-after-milliseconds 的 10 倍，外加 master 宕机的时长，那么 slave 就被认为不适合选举为 master
* 按照 slave 优先级进行排序，slave priority 越低，优先级就越高
* 如果 slave priority 相同，那么看 replica offset，哪个 slave 复制了越多的数据，offset 越靠后，优先级就越高
* 如果上面两个条件都相同，那么选择一个 run id 比较小的那个 slave。
