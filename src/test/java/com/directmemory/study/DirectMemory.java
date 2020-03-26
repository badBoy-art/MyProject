package com.directmemory.study;

import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

/**
 * 堆外内存
 * 可用 -XX:MaxDirectMemorySize 重新设定
 *
 * @author badBoy
 * @create 2019-12-02
 */
public class DirectMemory {

    @Test
    public void test() {
        ByteBuffer bb = ByteBuffer.allocateDirect(1024);
        bb.putChar('A');
        bb.putInt(123);

        System.out.println("capacity: " + bb.capacity());
        System.out.println("limit: " + bb.limit());
        System.out.println("position: " + bb.position());

        bb.position(0);
        System.out.println(bb.getChar());
        System.out.println(bb.getInt());
        //堆外内存的主动回收
        ((DirectBuffer)bb).cleaner().clean();
    }

}
