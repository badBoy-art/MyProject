package com.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.Test;
import org.roaringbitmap.RoaringBitmap;
import org.roaringbitmap.buffer.ImmutableRoaringBitmap;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-12-04
 * @Description RoaringBitmap  位图 <a href="https://www.jianshu.com/p/818ac4e90daf"></a>
 */
public class RoaringBitmapStudy {

    @Test
    public void test() {
        RoaringBitmap rr = RoaringBitmap.bitmapOf(1, 2, 3, 1000);    //带初始化操作的实例
        RoaringBitmap rr2 = new RoaringBitmap();
        rr2.add(4000L, 4153L);//范围添加
        // 还回bitmap中index为3的值，这里是1000
        System.out.println(rr.select(3));
        // 还回2在bitmap中的index,这是1
        System.out.println(rr.rank(3));
        // 1000在其中
        System.out.println(rr.contains(1000));
        // 7不在其中
        System.out.println(rr.contains(7));
        long cardinality1 = rr.getLongCardinality();//bitmap中value的个数
        System.out.println(cardinality1);

        RoaringBitmap rror = RoaringBitmap.or(rr, rr2);// 逻辑or操作
        rr.or(rr2); //逻辑or操作，rr=rr.or(rr2);
        boolean equals = rror.equals(rr);// true
        if (!equals) throw new RuntimeException("bug");
        long cardinality = rr.getLongCardinality();//bitmap中value的个数
        System.out.println(cardinality);
    }

    private static String  path = "/Users/zhaoxuedui/Desktop/out.txt";

    @Test
    public void testSeriali() throws IOException {
        RoaringBitmap rr = RoaringBitmap.bitmapOf(9,3,4,6,7);
        rr.add(158);
        System.out.println(rr.select(3));
        System.out.println(rr.rank(3));
        System.out.println(rr.contains(6));
        System.out.println(rr.getLongCardinality());
        //序列化
        FileOutputStream fos = new FileOutputStream(path);
        DataOutputStream dos = new DataOutputStream(fos);
        rr.serialize(dos);
        //数据刷新到磁盘
        dos.flush();
        dos.close();
    }

    @Test
    public void testDeseriali() throws IOException {
//读取二进制流并包装成ByteBuf
        FileInputStream fi = new FileInputStream(path);
        DataInputStream di = new DataInputStream(fi);
        byte[] bytes = new byte[di.available()];
        di.read(bytes,0,di.available());
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        //ByteBuf还原数据结构
        ImmutableRoaringBitmap immutableRoaringBitmap = new ImmutableRoaringBitmap(bb);
        RoaringBitmap rr = new RoaringBitmap(immutableRoaringBitmap);
        System.out.println(rr.select(3));
        System.out.println(rr.rank(3));
        System.out.println(rr.contains(6));
    }
}
