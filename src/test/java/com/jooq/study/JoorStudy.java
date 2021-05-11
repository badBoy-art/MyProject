package com.jooq.study;

import org.joor.Reflect;
import org.junit.Test;

import com.classLoader.study.Panda;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-10
 * @Description
 */
public class JoorStudy {

    @Test
    public void testReflectOn() {
        Panda bean = Reflect.on("com.classLoader.study.Panda").create().get();
        bean.setName("panda");
        System.out.println(bean.toString());
    }

}
