package com.xuliehua;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

/**
 * 若实现的是Serializable接口，则所有的序列化将会自动进行，
 * 若实现的是Externalizable接口，则没有任何东西可以自动序列化，需要在writeExternal方法中进行手工指定所要序列化的变量，这与是否被transient修饰无关。
 * 因此第二个例子输出的是变量content初始化的内容，而不是null
 *
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-13
 * @Description
 */
public class XuLieHuaTest {

    @Test
    public void testSer() {
        WangerSer wanger = new WangerSer();
        wanger.setName("王二");
        wanger.setAge(18);
        wanger.setAdd("海淀区");
        System.out.println(wanger);

        // 把对象写到文件中
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/Users/zhaoxuedui/Desktop/chenmo"));) {
            oos.writeObject(wanger);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WangerSer.add = "昌平";
        // 从文件中读出对象
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("/Users/zhaoxuedui/Desktop/chenmo")));) {
            WangerSer wanger1 = (WangerSer) ois.readObject();
            System.out.println(wanger1);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExt() {
        WangerExt wanger = new WangerExt();
        wanger.setName("王二");
        wanger.setAge(18);
        wanger.setAdd("海淀区");
        System.out.println(wanger);

        // 把对象写到文件中
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/Users/zhaoxuedui/Desktop/chenmo1"));) {
            oos.writeObject(wanger);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 从文件中读出对象
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("/Users/zhaoxuedui/Desktop/chenmo1")));) {
            WangerExt wanger1 = (WangerExt) ois.readObject();
            System.out.println(wanger1);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
