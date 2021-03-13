package com.thread.study;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2021-02-20
 * @Description
 */
public class PoisonThreadMain {
    public static void main(String[] args) {
        File file = new File("/Users/zhaoxuedui/Desktop");
        PoisonThread c = new PoisonThread(file);
        c.start();
        try {
            TimeUnit.MICROSECONDS.sleep(100);// 停止ＸＸ时间，显示出消费速度慢于生产速度
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        c.stop();
    }
}