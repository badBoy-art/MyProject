package com.oom;

import com.Set.study.Person;
import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author badBoy
 * @create 2019-11-08
 */
public class OomThreadLocal {

    @Test
    public void testWeakReferenceGc() throws Exception {
        Person person = new Person();
        ReferenceQueue<Person> queue = new ReferenceQueue<>();
        WeakReference<Person> weakReference = new WeakReference<Person>(person, queue);

        person = null;//去掉强引用，new Person("张三")的这个对象就只有软引用了

        System.gc();
        Thread.sleep(1000);
        System.err.println("弱引用的对象 ------->" + weakReference.get());

        Reference weakPollRef = queue.poll();   //poll()方法是有延迟的
        if (weakPollRef != null) {
            System.err.println("WeakReference对象中保存的弱引用对象已经被GC，下一步需要清理该Reference对象");
            //清理softReference
        } else {
            System.err.println("WeakReference对象中保存的软引用对象还没有被GC，或者被GC了但是获得对列中的引用对象出现延迟");
        }

    }

    @Test
    public void testThreadLoaclWeakReferenceGc() throws Exception {
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("----test----");
        System.out.println(threadLocal.get());
        System.gc();
        Thread.sleep(1000);
        System.out.println(threadLocal.get());
        List<byte[]> list = new ArrayList<>();
        while (true) {
            byte[] b = new byte[1024 * 1024 * 1];
            list.add(b);
            System.gc();
            System.out.println(threadLocal.get());
        }
    }

}
