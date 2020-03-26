package com.Extends.study;

import com.google.common.collect.Lists;

import javax.lang.model.element.AnnotationMirror;
import java.util.ArrayList;
import java.util.List;

/**
 * ceshi
 *
 * @author xuedui.zhao
 * @create 2018-05-09
 */
public class Test {

    @org.junit.Test
    //下界通配符
    public void testSupper() {
        List<? super Dog> lists = new ArrayList<Animal>();
        lists.add(new Dog());
        lists.add(new WhiteDog());
        //lists.add(new Animal());
        for (Object dog2: lists) {
            Dog dog3 =(Dog)dog2;
            dog3.run();
        }
        System.out.print(8>>2);
    }

    @org.junit.Test
    //上界通配符
    public void testExtends() {
        List<? extends Dog> lists = new ArrayList<WhiteDog>();
        //相当于向上转型
       // Dog dog = lists.get(0);
        //相当于向下转型
        //WhiteDog whiteDog = lists.get(0);
        //不知道实例化对象的具体类型，所以不能add
        //lists.add(new Dog());

    }

    @org.junit.Test
    public void transfer() {
//        Dog dog = new WhiteDog();
//        Dog dog1 = (Dog)new Animal();
    }
}
