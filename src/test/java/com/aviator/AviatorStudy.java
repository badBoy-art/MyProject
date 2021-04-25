package com.aviator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2021-04-22
 * @Description 表达式求值引擎开源框架
 */
public class AviatorStudy {

    @Test
    public void test01() {
        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        System.out.println(result);
    }

    @Test
    public void test02() {
        Map<String, Object> env = new HashMap<>();
        env.put("yourname", "badBoy");
        String result = (String) AviatorEvaluator.execute(" 'hello ' + yourname ", env);
        System.out.println(result);
    }

    @Test
    public void test03() {
        System.out.println(AviatorEvaluator.execute(" 'a\"b' "));
        System.out.println(AviatorEvaluator.execute(" \"a\'b\"  "));
        System.out.println(AviatorEvaluator.execute(" 'hello'+3 "));
        System.out.println(AviatorEvaluator.execute(" 'hello '+ unknow "));

        System.out.println(AviatorEvaluator.execute("string.length('hello')"));
        System.out.println(AviatorEvaluator.execute("string.contains(\"test\",string.substring('hello',1,2))"));
    }

    @Test
    public void testFunction() {
        AviatorEvaluator.addFunction(new AddFunction());
        System.out.println(AviatorEvaluator.execute("add(1,2)"));
        System.out.println(AviatorEvaluator.execute("add(add(1,2),100)"));
    }

    @Test
    public void testExpression() {
        String expression = "a-(b-c)>100";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression, true);

        Map<String, Object> env = new HashMap<>();
        env.put("a", 1000.3);
        env.put("b", 45);
        env.put("c", -199.100);

        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);
    }

    @Test
    public void testCollection() {
        final List<String> list = new ArrayList<>();
        list.add("hello");
        list.add(" world");

        final int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 3;

        final Map<String, Date> map = new HashMap<>();
        map.put("date", new Date());

        Map<String, Object> env = new HashMap<>();
        env.put("list", list);
        env.put("array", array);
        env.put("mmap", map);

        System.out.println(AviatorEvaluator.execute(
                "list[0]+list[1]+'/narray[0]+array[1]+array[2]='+(array[0]+array[1]+array[2]) +' /ntoday is '+mmap.date ", env));
    }

    @Test
    public void testTernaryOperator() {
        Map<String, Object> env = new HashMap<>();
        env.put("a", 10);
        //Aviator的三元表达式对于两个分支的结果类型并不要求一致，可以是任何类型，这一点与java不同。
        String result = (String) AviatorEvaluator.execute("a>0? 'yes':'no'", env);
        System.out.println(result);
    }

    @Test
    public void testRegular() {
        //正则表达式 Aviator的正则表达式规则跟Java完全一样，因为内部其实就是使用java.util.regex.Pattern做编译的
        String email = "badBoy2021@gmail.com";
        Map<String, Object> env = new HashMap<>();
        env.put("email", email);
        String username = (String) AviatorEvaluator.execute("email=~/([//w0-8]+@//w+[//.//w+]+)/ ? $1:'unknow'", env);
        System.out.println(username);
    }
}

class AddFunction implements AviatorFunction {

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public AviatorObject call(Map<String, Object> map) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1) {
        Number left = FunctionUtils.getNumberValue(aviatorObject, map);
        Number right = FunctionUtils.getNumberValue(aviatorObject1, map);
        return new AviatorDouble(left.doubleValue() + right.doubleValue());
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11, AviatorObject aviatorObject12) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11, AviatorObject aviatorObject12, AviatorObject aviatorObject13) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11, AviatorObject aviatorObject12, AviatorObject aviatorObject13, AviatorObject aviatorObject14) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11, AviatorObject aviatorObject12, AviatorObject aviatorObject13, AviatorObject aviatorObject14, AviatorObject aviatorObject15) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11, AviatorObject aviatorObject12, AviatorObject aviatorObject13, AviatorObject aviatorObject14, AviatorObject aviatorObject15, AviatorObject aviatorObject16) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11, AviatorObject aviatorObject12, AviatorObject aviatorObject13, AviatorObject aviatorObject14, AviatorObject aviatorObject15, AviatorObject aviatorObject16, AviatorObject aviatorObject17) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11, AviatorObject aviatorObject12, AviatorObject aviatorObject13, AviatorObject aviatorObject14, AviatorObject aviatorObject15, AviatorObject aviatorObject16, AviatorObject aviatorObject17, AviatorObject aviatorObject18) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11, AviatorObject aviatorObject12, AviatorObject aviatorObject13, AviatorObject aviatorObject14, AviatorObject aviatorObject15, AviatorObject aviatorObject16, AviatorObject aviatorObject17, AviatorObject aviatorObject18, AviatorObject aviatorObject19) {
        return null;
    }

    @Override
    public AviatorObject call(Map<String, Object> map, AviatorObject aviatorObject, AviatorObject aviatorObject1, AviatorObject aviatorObject2, AviatorObject aviatorObject3, AviatorObject aviatorObject4, AviatorObject aviatorObject5, AviatorObject aviatorObject6, AviatorObject aviatorObject7, AviatorObject aviatorObject8, AviatorObject aviatorObject9, AviatorObject aviatorObject10, AviatorObject aviatorObject11, AviatorObject aviatorObject12, AviatorObject aviatorObject13, AviatorObject aviatorObject14, AviatorObject aviatorObject15, AviatorObject aviatorObject16, AviatorObject aviatorObject17, AviatorObject aviatorObject18, AviatorObject aviatorObject19, AviatorObject... aviatorObjects) {
        return null;
    }

    @Override
    public void run() {

    }

    @Override
    public AviatorObject call() throws Exception {
        return null;
    }
}


