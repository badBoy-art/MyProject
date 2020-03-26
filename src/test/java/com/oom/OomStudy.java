package com.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个线程发生oom其他线程是否还能正常运行  ：答案是可运行的
 * 发生oom的线程一般会死掉，会被终结掉，该线程持有的对象占用的heap会被gc掉，释放内存
 *
 * @author xuedui.zhao
 * @create 2019-06-22
 */
public class OomStudy {

    public static void main(String[] args) {
        new Thread(() -> {
            int i = 0;
            List<byte[]> list = new ArrayList<>();
            while (true) {
                byte[] b = new byte[1024 * 1024 * 1];
                list.add(b);
                i++;
                System.out.print("success" + (i) + "\n");
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    System.out.println("just running");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    List<Byte[]> list = new ArrayList<>();
                    list.add(new Byte[1024 * 1024]);
                    System.out.print("sleep\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

}
