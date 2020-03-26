package com.runtime.study;

import org.junit.Test;

import java.io.IOException;

/**
 * @author xuedui.zhao
 * @create 2018-12-11
 */
public class RunTimeStudy {

    @Test
    public void test() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("java -version");
      System.out.println();
      System.out.println(process.waitFor());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {

        } finally{
            if (process != null) {
                process.destroy();
            }
        }
    }
}
