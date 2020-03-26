package com.agent.study;

import java.lang.management.ManagementFactory;

/**
 * @see <a href="https://mp.weixin.qq.com/s/CH9D-E7fxuu462Q2S3t0AA"></a>
 *
 * @author badBoy
 * @create 2019-09-06
 */
public class Base {

    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String s = name.split("@")[0];
        //打印当前Pid
        System.out.println("pid:"+s);
        while (true) {
            try {
                Thread.sleep(5000L);
            } catch (Exception e) {
                break;
            }
            process();
        }
    }

    public static void process(){
        System.out.println("process");
    }

}
