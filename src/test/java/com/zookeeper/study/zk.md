ZAB 协议是为分布式协调服务ZooKeeper专门设计的一种支持崩溃恢复的一致性协议。基于该协议，ZooKeeper 实现了一种主从模式的系统架构来保持集群中各个副本之间的数据一致性。
选主时机：
节点启动时：
    每个节点启动的时候状态都是LOOKING，处于观望状态，接下来就是要进行选主了。
Leader节点异常：
    Leader节点运行后会周期性地向Follower发送心跳信息（称之为ping），如果一个Follower未收到Leader节点的心跳信息，Follower节点的状态会从FOLLOWING转变为LOOKING
多数Follower节点异常：
* Leader节点也会检测Follower节点的状态，如果多数Follower节点不再响应Leader节点（可能是Leader节点与Follower节点之间产生了网络分区），那么Leader节点可能此时也不再是合法的Leader了，也必须要进行一次新的选主。
* Leader节点启动时会接收Follower的主动连接请求，对于每一个Follower的新连接，Leader会创建一个LearnerHandler对象来处理与该Follower的消息通信。
* LearnerHandler创建一个独立线程，在主循环内不停地接受Follower的消息并根据消息类型决定如何处理。除此以外，每收到Follower的消息时，便更新下一次消息的过期时间

一些概念在描述详细的选主过程之前，有必要交代一些概念：
* election epoch
    * 这是分布式系统中极其重要的概念，由于分布式系统的特点，无法使用精准的时钟来维护事件的先后顺序，因此，Lampert提出的Logical Clock就成为了界定事件顺序的最主要方式。
    * 分布式系统中以消息标记事件，所谓的Logical Clock就是为每个消息加上一个逻辑的时间戳。在ZAB协议中，每个消息都被赋予了一个zxid，zxid全局唯一。zxid有两部分组成：高32位是epoch，低32位是epoch内的自增id，由0开始。每次选出新的Leader，epoch会递增，同时zxid的低32位清0。这其实像极了咱们古代封建王朝的君主更替，每一次的江山易主，君王更替。
* zxid
    * 每个消息的编号，在分布式系统中，事件以消息来表示，事件发生的顺序以消息的编号来标记。在ZAB协议中，这就是zxid。ZAB协议中，消息的编号只能由Leader节点来分配，这样的好处是我们就可以通过zxid来准确判断事件发生的先后，记住，是任意事件，这也是分布式系统中，由全局唯一的主节点来处理更新事件带来的极大好处。
    * 分布式系统运行的过程中，Leader节点必然会发生改变，一致性协议必须能够正确处理这种情况，保证在Leader发生变化的时候，新的Leader期间，产生的zxid必须要大于老的Leader时生成的zxid。这就得通过上面说的epoch机制了，具体实现会在下面的选主过程中详细描述。

LOOKING/FOLLOWING/LEADING
这三者描述了系统中节点的状态：
1. LOOKING: 节点正处于选主状态，不对外提供服务，直至选主结束；
2. FOLLOWING: 作为系统的从节点，接受主节点的更新并写入本地日志；
3. LEADING: 作为系统主节点，接受客户端更新，写入本地日志并复制到从节点

选主过程参与方有：
* 发起选主的节点
* 集群其他节点，这些节点会为发起选主的节点进行投票
节点B判断确定A可以成为主，那么节点B就投票给节点A，判断的依据是：
1. election epoch(A) > election epoch (B)
2. zxid(A) > zxid(B)
3. sid(A) > sid(B)

选主流程：
1. 候选节点A初始化自身的zxid和epoch：updateProposal()；
2. 向其他所有节点发送选主通知：sendNotifications()；
3. 等待其他节点的回复：recvqueue.poll()；
4. 如果来自B节点的回复不为空，且B是一个有效节点，判断B此时的运行状态是LOOKING（也在发起选主）还是LEADING/FOLLOWING（正常请求处理过程）

情况1：投票节点是LOOKING状态

STEP 1：处于LOOKING状态的A发起一次选主请求，并将请求广播至B、C节点，而此时B、C也恰好处于LOOKING状态：

STEP 2：B、C节点处理A的选主消息，其中，B接受A的提议，C拒绝A的提议：

说明：伴随着A的选主消息的一个额外收获是B和C此时都获得了A节点选主的结果（A投票给，记录为<A, A>），记录该信息，作为后续判断大家是否达成一致的标准。
STEP 3：B将处理结果通知A、C

说明：
* 因为B更新了自己的投票，从投票给自己变成投票给A，因此根据协议的定义，需要将该消息扩散出去。而C由于拒绝了A的提议，因此，无需扩散消息；
* B将消息扩散给A和C的同时，A和C也就了解了B的投票信息，可以更新本地的投票信息表，例如上面经过B的扩散后，A知道了B节点的投票信息，C知道了A和B节点的投票信息。
STEP 4：C同时也发起选主

STEP 5：A、B分别处理C的选主请求

说明：这里A和B判断得出C是最合适的Leader，因此A和B都更新自己的候选Leader为C，同时由于C的消息，A和B都更新自身维护的投票信息，增加C的投票信息。
STEP 6：A、B将更新后的信息扩散到其他节点

说明：因为在第五步中A和B分别将自己的候选Leader变成了C，因此需要将该信息通知到其他节点，其他节点在收到新的投票信息后会更新本地的投票信息列表，如上图。
STEP 7: 选主结束
此时此刻，所有的节点都已经达成了一致：每个节点都同意节点C作为新的Leader。

情形2：投票节点是FOLLOWING/LEADING状态
以下原因可能导致出现这种情况：
* 节点A（Follower）与Leader出现网络问题而触发一次选主，但是其他Follower与Leader正常;
* 新节点加入集群也会有同样的情况发生。
如果一个正常服务状态(LEADING/FOLLOWING)的节点收到一个节点的选主请求，处理流程是怎么样的呢？
在QuorumPeer对象中存在一个WorkerReceiver线程，该线程的主要作用是接受其他节点发送过来的选主消息变更的通知。这个线程中收到其他节点发来的选主消息通知时会判断当前节点的状态：

此时处理过程是怎样的：
* 如果Logical Clock相同，将数据保存在recvset，如果Sender宣称自己是Leader，那么判断是不是半数以上的服务器都选举它，如果是设置角色并退出选举。
* 否则，这是一条与当前LogicalClock不符合的消息，说明在另一个选举过程中已经有了选举结果(另一个选举过程指的是什么)，于是将该选举结果加入到OutOfElection集合中，根据OutOfElection来判断是否可以结束选举，如果可以也是保存LogicalClock，更新角色，退出选举。出现这种情况可能是由于原集群中有一个新的服务器上线/重新启动，但是原来的已有集群的机器已经选主成功，因此，别无他法，只有加入原来的集群成为Follower。但这里的Logical Clock不符合，可能大也可能小，怎么理解？
说明：
1. logical clock相同可能是因为出现这种情况：A、B同时发起选主，此时他们的election epoch可能相同，如果B率先完成了选主过程（B可能变成了Leader，也有可能B选择了其他节点为Leader），但是A还在选主过程中，此时如果B收到了A的选主消息，那么B就将自己的选主结果和自己的状态（LEADING/FOLLOWING）连同自己的election epoch回复给A，对于A来说，它收到了一个来自选主完成的节点B的election epoch相同的回复，便有了上面的第一种情况；

上图的10表示选主的Logical Clock
2. logical clock不相同可能是因为新增了一个节点或者某个节点出现了网络隔离导致其触发一次新的选主，然后系统中其他节点状态依然正常，此时发起选主的节点由于要递增其logical clock，必然会导致其logical clock要大于其他正常节点的logical clock（当然也可能小于，考虑一个新上线节点触发选主，其logical clock从1开始计算）。因此就出现了上面的第二种情况

如果对方节点处于FOLLOWING/LEADING状态，除检查是否过半外，同时还要检查leader是否给自己发送过投票信息，从投票信息中确认该leader是不是LEADING状态。这个解释如下：
    因为目前leader和follower都是各自检测是否进入leader选举过程。leader检测到未过半的server的ping回复，则leader会进入LOOKING状态，但是follower有自己的检测，感知这一事件，还需要一定时间，在此期间，如果其他server加入到该集群，可能会收到其他follower的过半的对之前leader的投票，但是此时该leader已经不处于LEADING状态了，所以需要这么一个检查来排除这种情况。

Leader/Follower信息同步：
选出了Leader还不算完，根据ZAB协议定义，在真正对外提供服务之前还需要一个信息同步的过程。具体来说，Leader和Follower之间需要同步以下信息：
* 下一次zxid：这是因为选出新的Leader后，epoch势必发生改变，因此，需要经过多方协商后选择出当前最大的epoch，然后再拼凑出下一轮提供服务的zxid
* 日志内容：ZAB使用日志同步来维护多个节点的一致性状态，同步过程是由Leader发往Follower，因此可能会存在大家步调不一致的情况，表现出的现象就是节点日志内容不同，可能某些节点领先，而某些节点落后。
Epoch协商
选主过程结束后，接下来就是多数派节点协商出一个最大的epoch（但如果是采用FastLeaderElection算法的话，选出来的Leader其实就拥有了最大的epoch）。
这个过程涉及到Leader和Follower节点的通信，具体流程：
1. Leader节点启动时调用getEpochToPropose()，并将自己的zxid解析出来的epoch作为参数；
2. Follower节点启动时也会连接Leader，并从自己的最后一条zxid解析出epoch发送给Leader，leader中处理该Follower消息的线程同样调用getEpochToPropose()，只是此时传入的参数是该Follower的epoch；
3. getEpochToPropose()中会判断参数中传入的epoch和当前最大的epoch，选择两者中最大的，并且判断该选择是否已经获得了多数派的认可，如果没有得到，则阻塞调用getEpochToPropose()的线程；如果获得认可，那就唤醒那些等待epoch协商结果的线程，于是，Follower就得到了多数派认可的全新的epoch，大家就从这个epoch开始生成新的zxid；
4. Leader的发起epoch更新过程在函数Leader::lead()中，Follower的发起epoch更新过程在函数Follower::followLeader()中，Leader处理Follower的epoch更新请求在函数LearnerHandler::run()中。
日志同步
选主结束后，接下来需要在Leader和Follower之间同步日志，根据ZAB协议定义，这个同步过程可能是Leader流向Follower。
对比的原理是将Follower的最新的日志zxid和Leader的已经提交的日志zxid对比，会有以下几种可能：
1. 如果Leader的最新提交的日志zxid比Follower的最新日志的zxid大，那就将多的日志发送给Follower，让他补齐；
2. 如果Leader的最新提交的日志zxid比Follower的最新日志的zxid小，那就发送命令给Follower，将其多余的日志截断；
3. 如果两者恰好一样，那什么都不用做。

即使是一个日志同步过程也要经历以下几个同步过程：
1. Leader发送同步日志给Follower，该过程传输的主要是日志数据流或者Leader给Follower的各种命令；
2. Leader发送NEWLEADER命令给Follower，该命令的作用应该是告诉Follower日志同步已经完成，Follower对该NEWLEADER作出ACK，而Leader会等待该ACK消息；
3. Leader最后发送UPTODATE命令至Follower，这个命令的作用应该是告诉Follower，我已经收到了你的ACK，而Follower这边收到该消息的时候说明一切与Leader同步的初始化工作都已经完成，可以进入正常的处理流程了，而Leader这边发完该命令后也可以进入正常的请求处理流程了。

