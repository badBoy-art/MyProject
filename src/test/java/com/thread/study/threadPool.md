使用线程池的好处：
1. 减少资源创建 => 减少内存开销，创建线程占用内存
2. 降低系统开销 => 创建线程需要时间，会延迟处理的请求
3. 提高稳定稳定性 => 避免无限创建线程引起的OutOfMemoryError【简称OOM】
Executors创建线程池的方式：
1. 创建返回ThreadPoolExecutor对象
2. 创建返回ScheduleThreadPoolExecutor对象
3. 创建返回ForkJoinPool对象
Java通过Executors提供四种线程池，分别为：
1. newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
2. newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
3. newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
4. newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
四种线程池的缺点：
* FixedThreadPool和SingleThreadExecutor => 允许的请求队列长度为Integer.MAX_VALUE，可能会堆积大量的请求，从而引起OOM异常
* CachedThreadPool => 允许创建的线程数为Integer.MAX_VALUE，可能会创建大量的线程，从而引起OOM异常

SynchronousQueue是一个不存储元素的队列

ThreadPoolExecutor类可设置的参数主要有：

1. corePoolSize
在创建了线程池后，默认情况下，线程池中并没有任何线程，而是等待有任务到来才创建线程去执行任务，（除非调用了prestartAllCoreThreads()或者prestartCoreThread()方法，从这2个方法的名字就可以看出，是预创建线程的意思，即在没有任务到来之前就创建corePoolSize个线程或者一个线程）。默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中。核心线程在allowCoreThreadTimeout被设置为true时会超时退出，默认情况下不会退出。
2. maxPoolSize
当线程数大于或等于核心线程，且任务队列已满时，线程池会创建新的线程，直到线程数量达到maxPoolSize。如果线程数已等于maxPoolSize，且任务队列已满，则已超出线程池的处理能力，线程池会拒绝处理任务而抛出异常。
3. keepAliveTime
当线程空闲时间达到keepAliveTime，该线程会退出，直到线程数量等于corePoolSize。如果allowCoreThreadTimeout设置为true，则所有线程均会退出直到线程数量为0。
4. allowCoreThreadTimeout
是否允许核心线程空闲退出，默认值为false。
5. queueCapacity
任务队列容量。从maxPoolSize的描述上可以看出，任务队列的容量会影响到线程的变化，因此任务队列的长度也需要恰当的设置。
6. 还有就是 workQueue：一个阻塞队列，用来存储等待执行的任务，这个参数的选择也很重要，会对线程池的运行过程产生重大影响，一般来说，这里的阻塞队列有以下几种选择：
    * ArrayBlockingQueue;
    * LinkedBlockingQueue;
    * SynchronousQueue;
    * PriorityBlockingQueue 
    * ArrayBlockingQueue和PriorityBlockingQueue使用较少，一般使用LinkedBlockingQueue和Synchronous。线程池的排队策略与BlockingQueue有关。
7. threadFactory：线程工厂，主要用来创建线程；
8. handler：表示当拒绝处理任务时的策略，有以下四种取值：
    * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
    * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
    * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
    * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
    * 自定义拒绝策略，只需要实现RejectedExecutionHandler接口即可
9. 线程池按以下行为执行任务：
    * 当线程数小于核心线程数时，创建线程。
    * 当线程数大于等于核心线程数，且任务队列未满时，将任务放入任务队列。
    * 当线程数大于等于核心线程数，且任务队列已满
        * 若线程数小于最大线程数，创建线程
        * 若线程数等于最大线程数，抛出异常，拒绝任务
