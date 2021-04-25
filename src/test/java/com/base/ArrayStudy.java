package com.base;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-09-05
 * <a href="https://dunwu.github.io/javacore/basics/java-array.html"></a>
 */
public class ArrayStudy {


    /**
     * 1.指定数组维度
     * 为数组开辟指定大小的数组维度。
     * 如果数组元素是基础数据类型，会将每个元素设为默认值；如果是引用类型，元素值为 null。
     * 2.不指定数组维度
     * 用花括号中的实际元素初始化数组，数组大小与元素数相同
     */
    @Test
    public void testArray01() {
        /**
         * 指定数组维度
         */
        int[] array1 = new int[2];
        /**
         * 不指定数组维度
         */
        int[] array2 = new int[]{1, 2};
        System.out.println("array1 size is " + array1.length);
        for (int item : array1) {
            System.out.println(item);
        }
        System.out.println("array2 size is " + array1.length);
        for (int item : array2) {
            System.out.println(item);
        }
    }

    /**
     * 创建数组时，指定的数组维度可以有多种形式：
     * <p>
     * 1、数组维度可以是整数、字符。
     * 2、数组维度可以是整数型、字符型变量。
     * 3、数组维度可以是计算结果为整数或字符的表达式
     */
    @Test
    public void testArray02() {
        int length = 3;
        // 放开被注掉的代码，编译器会报错
        // int[] array = new int[4.0];
        // int[] array2 = new int["test"];
        int[] array3 = new int['a'];
        int[] array4 = new int[length];
        int[] array5 = new int[length + 2];
        int[] array6 = new int['a' + 2];
        // int[] array7 = new int[length + 2.1];
        System.out.println("array3.length = [" + array3.length + "]");
        System.out.println("array4.length = [" + array4.length + "]");
        System.out.println("array5.length = [" + array5.length + "]");
        System.out.println("array6.length = [" + array6.length + "]");
    }

    /**
     * Java 中不允许直接创建泛型数组。但是，可以通过创建一个类型擦除的数组，然后转型的方式来创建泛型数组
     */
    @Test
    public void testArray03() {
        GenericArray<Integer> genericArray = new GenericArray<Integer>(4);
        genericArray.put(0, 0);
        genericArray.put(1, 1);
        Object[] array = genericArray.array();
        System.out.println(Arrays.deepToString(array));
    }

}

class GenericArray<T> {
    private T[] array;

    public GenericArray(int num) {
        array = (T[]) new Object[num];
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] array() {
        return array;
    }
}