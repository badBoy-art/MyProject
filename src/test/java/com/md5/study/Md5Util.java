package com.md5.study;

import com.util.MD5Util;
import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2018-09-29
 */
public class Md5Util {
    
    @Test
    public void test01() {
      System.out.println(MD5Util.md5Hex("8286343FEC8B3B4D" + "nhq180920184150108" + "_" + 1));
    
    }
    
}
