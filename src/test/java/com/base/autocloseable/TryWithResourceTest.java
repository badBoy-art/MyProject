package com.base.autocloseable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import com.Extends.study.Dog;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-05-29
 * @Description <a href="https://juejin.im/entry/57f73e81bf22ec00647dacd0"></a>
 */
public class TryWithResourceTest {

    @Test
    public void test() {
        //try-with-resource 语法糖
        try (Connection conn = new Connection()) {
            conn.sendData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDe() {
        SerializeUtil.deserialize(null, Dog.class);
    }

    static class SerializeUtil {

        /**
         * @param object
         * @return
         */
        public static byte[] serialize(Object object) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(object);
                byte[] bytes = baos.toByteArray();
                return bytes;
            } catch (Exception e) {
            }
            return null;
        }

        /**
         * @param bytes
         * @param type
         * @param <T>
         * @return
         */
        public static <T> T deserialize(byte[] bytes, Class<T> type) {

            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                return type.cast(ois.readObject());
            } catch (Exception e) {
                System.out.println("deserialize_exception" + " " + e.getMessage());
            }
            return null;
        }

    }

}
