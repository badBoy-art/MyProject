package com.quasar.study;

import co.paralleluniverse.fibers.Suspendable;
import co.paralleluniverse.strands.Strand;

import java.util.Random;

/**
 * @author badBoy
 * @create 2019-08-27
 */
public class OtherService {

    public static int DEFAULT_TIMEOUT = 3000; //默认超时时间 3s
    public static int DEFAULT_DELAY = 300; //默认延迟300ms
    public static int DEFAULT_DELAY_UPPER = 5000; //默认延迟上限 10s
    public static boolean IS_RANDOM_DELAY = Boolean.FALSE; //延迟随机

    // 这个注解是Quasar提供的，表明这个方法发生阻塞的时候是可以被挂起的
    @Suspendable
    public boolean dispose() {
        boolean result = false;

        int delay = DEFAULT_DELAY;

        if (IS_RANDOM_DELAY) {
            Random random = new Random();
            delay = random.nextInt(DEFAULT_DELAY_UPPER);

        }

        if (delay > DEFAULT_TIMEOUT) {
            result = false;
        } else {
            result = true;
        }

        try {
            Strand.sleep(delay);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }
}
