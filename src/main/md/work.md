Mac 没声音 sudo killall coreaudiod

greattest002   Test123456

CPU：
  top 查看进程占用的cpu，系统内存，swap等资源
  top -p pid -H -n 1 -b 查看线程占用的cpu
  sudo -u tomcat jstack pid 查看运行的线程 
  slow_stack.sh pid(可以在wiki里搜) 将线程按耗费cpu百分比排序
内存：
  sudo -u tomcat jmap -histo:live pid 查看内存中各个对象的实例数
  sudo -u tomcat /home/q/java/default/bin/jmap -dump:format=b,file=/tmp/dump.jprof  dump内存
磁盘：
  sudo iotop 查看所有线程的磁盘io使用率
网络
  netstat -nal 查看系统中所有网络连接的状态
  tcpdump 网络抓包
 netstat -n | awk '/^tcp/ {++S[$NF]} END {for(a in S) print a, S[a]}'
  wireshark 网络包分析工具，用于分析tcpdump抓取的网络包文件

Synchronized、Volatile、AtomicOperations
java -verbose:class -version

Free -m 查看liunx内存
sudo -u tomcat jstat -gcutil <pid> 1000 查看gc

Show variables like "sql_mode”;

Sql_mode：是做什么的

nslookup www.163.com