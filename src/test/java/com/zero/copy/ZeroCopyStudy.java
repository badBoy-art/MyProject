package com.zero.copy;

import org.junit.Test;

/**
 * @author badBoy <badBoy>
 * Created on 2021-04-27
 * @Description <a href="https://mp.weixin.qq.com/s/dXYQTofcP6lGR8SSctixBQ"></a>
 */
public class ZeroCopyStudy {

    /**
     * 4次拷贝
     *         用户空间      内核空间      硬件(磁盘、网卡)
     *
     * read    cpu拷贝  <-  读缓冲区  <-   DMA拷贝
     *
     * write   cpu拷贝  -> socket缓冲区 -> DMA拷贝
     *
     *
     * DMA拷贝：因为对于一个 IO 操作而言，都是通过 CPU 发出对应的指令来完成。但是，相比 CPU 来说，IO 的速度太慢了，CPU 有大量的时间处于等待 IO 的状态。
     *         因此就产生了 DMA（Direct Memory Access）直接内存访问技术，本质上来说他就是一块主板上独立的芯片，通过它来进行内存和 IO 设备的数据传输，从而减少 CPU 的等待时间
     *
     * 1、用户进程通过 read() 方法向操作系统发起调用，此时上下文从用户态转向内核态；
     * 2、DMA 控制器把数据从硬盘中拷贝到读缓冲区；
     * 3、CPU 把读缓冲区数据拷贝到应用缓冲区，上下文从内核态转为用户态，read() 返回；
     * 4、用户进程通过 write() 方法发起调用，上下文从用户态转为内核态；
     * 5、CPU 将应用缓冲区中数据拷贝到 socket 缓冲区；
     * 6、DMA 控制器把数据从 socket 缓冲区拷贝到网卡，上下文从内核态切换回用户态，write() 返回。
     */


    /**
     * mmap+write mmap:内存文件映射
     * mmap+write 简单来说就是使用 mmap 替换了 read+write 中的 read 操作，减少了一次 CPU 的拷贝。
     * mmap 主要实现方式是将读缓冲区的地址和用户缓冲区的地址进行映射，内核缓冲区和应用缓冲区共享，从而减少了从读缓冲区到用户缓冲区的一次CPU拷贝
     *
     * 1、用户进程通过 mmap() 方法向操作系统发起调用，上下文从用户态转向内核态；
     * 2、DMA 控制器把数据从硬盘中拷贝到读缓冲区；
     * 3、上下文从内核态转为用户态，mmap 调用返回；
     * 4、用户进程通过 write() 方法发起调用，上下文从用户态转为内核态;
     * 5、CPU 将读缓冲区中数据拷贝到 socket 缓冲区；
     * 6、DMA 控制器把数据从 socket 缓冲区拷贝到网卡，上下文从内核态切换回用户态，write() 返回。
     *
     * mmap 的方式节省了一次 CPU 拷贝，同时由于用户进程中的内存是虚拟的，只是映射到内核的读缓冲区，所以可以节省一半的内存空间，比较适合大文件的传输
     */

    /**
     * mmap
     *       用户空间     内核空间        硬件(磁盘、网卡)
     * <p>
     * read    映射       读缓冲区   <-   DMA拷贝
     *                   |cpuCopy
     * write             socket缓冲区 ->  DMA拷贝
     */
    @Test
    public void test01() {

    }


    /**
     * sendfile : 是 Linux2.1 内核版本后引入的一个系统调用函数。通过使用 sendfile 数据可以直接在内核空间进行传输，因此避免了用户空间和内核空间的拷贝，
     * 同时由于使用 sendfile 替代了 read+write 从而节省了一次系统调用，也就是 2 次上下文切换
     * <p>
     * 1、用户进程通过 sendfile() 方法向操作系统发起调用，上下文从用户态转向内核态；
     * 2、DMA 控制器把数据从硬盘中拷贝到读缓冲区；
     * 3、CPU 将读缓冲区中数据拷贝到 socket 缓冲区；
     * 4、DMA 控制器把数据从 socket 缓冲区拷贝到网卡，上下文从内核态切换回用户态，sendfile 调用返回。
     * <p>
     * sendfile 方法 IO 数据对用户空间完全不可见，所以只能适用于完全不需要用户空间处理的情况，比如静态文件服务器。
     */
    public void test02() {

    }


    /**
     * sendfile+DMA Scatter/Gather：Linux2.4 内核版本之后对 sendfile 做了进一步优化，通过引入新的硬件支持，这个方式叫做 DMA Scatter/Gather 分散/收集功能。
     * 它将读缓冲区中的数据描述信息——内存地址和偏移量记录到 socket 缓冲区，由  DMA 根据这些将数据从读缓冲区拷贝到网卡，相比之前版本减少了一次 CPU 拷贝的过程
     * <p>
     * 1、用户进程通过 sendfile() 方法向操作系统发起调用，上下文从用户态转向内核态；
     * 2、DMA 控制器利用 scatter 把数据从硬盘中拷贝到读缓冲区离散存储；
     * 3、CPU 把读缓冲区中的文件描述符和数据长度发送到 socket 缓冲区；
     * 4、DMA 控制器根据文件描述符和数据长度，使用 scatter/gather 把数据从内核缓冲区拷贝到网卡；
     * 5、sendfile() 调用返回，上下文从内核态切换回用户态。
     * <p>
     * DMA gather 和 sendfile 一样数据对用户空间不可见，而且需要硬件支持，同时输入文件描述符只能是文件，但是过程中完全没有 CPU 拷贝过程，极大提升了性能。
     */
    @Test
    public void test03() {

    }

}
