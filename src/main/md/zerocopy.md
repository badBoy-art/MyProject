https://tech.meituan.com/2019/01/03/spring-boot-native-memory-leak.html
使用Java层面的工具定位内存区域（堆内内存、Code区域或者使用unsafe.allocateMemory和DirectByteBuffer申请的堆外内存）
项目启动 添加JVM参数：-XX:NativeMemoryTracking=detail 使用命令jcmd pid VM.native_memory detail查看到的内存分布，jcmd命令显示的内存包含堆内内存、Code区域、通过unsafe.allocateMemory和DirectByteBuffer申请的内存，但是不包含其他Native Code（C代码）申请的堆外内存。
pmap + pid、pmap -x + pid 查看内存分布
直接使用命令strace -f -e "brk,mmap,munmap" -p pid追踪向OS申请内存请求
因为使用strace没有追踪到可疑内存申请；于是想着看看内存中的情况。就是直接使用命令gdp -pid pid进入GDB之后，然后使用命令dump memory mem.bin startAddress endAddressdump内存，其中startAddress和endAddress可以从/proc/pid/smaps中查找。然后使用strings mem.bin查看dump的内容：
* 堆内的 DirectByteBuffer 对象被 GC 时，它背后的堆外内存也会被回收。
* 这里可以看到一种尴尬的情况，因为 DirectByteBuffer 本身的个头很小，只要熬过了 Young GC，即使已经失效了也能在老生代里舒服的呆着，不容易把老生代撑爆触发 Full GC，如果没有别的大块头进入老生代触发Full GC，就一直在那耗着，占着一大片堆外内存不释放。
* 这时，就只能靠前面提到的申请额度超限时触发的 System.gc()来救场了。