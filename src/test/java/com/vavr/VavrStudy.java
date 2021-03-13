package com.vavr;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Function4;
import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Value;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;


/**
 * @author zhaoxuedui <zhaoxuedui>
 * Created on 2020-07-06
 * @Description
 */
public class VavrStudy {

    @Test
    public void testOption() {
        Option<Object> someOption = Option.of("val");

        assertEquals("Some(val)", someOption.toString());
    }

    @Test
    public void testSumList() {
        int sum = List.of(1, 2, 3).sum().intValue();

        assertEquals(6, sum);
    }

    @Test
    public void testLazy() {
        Lazy<Double> lazy = Lazy.of(Math::random);
        assertFalse(lazy.isEvaluated());

        double val1 = lazy.get();
        assertTrue(lazy.isEvaluated());

        double val2 = lazy.get();
        assertEquals(val1, val2, 0.1);
    }

    @Test
    public void testMatch() {
        int input = 2;
        String output = Match(input).of(
                Case($(1), "one"),
                Case($(2), "two"),
                Case($(3), "three"),
                Case($(), "?"));

        assertEquals("two", output);
    }

    @Test
    public void testTuple() {
        Tuple2<String, Integer> tuple2 = Tuple.of("Hello", 100);
        Tuple2<String, Integer> updatedTuple2 = tuple2.map(String::toUpperCase, v -> v * 5);
        String result = updatedTuple2.apply((str, number) -> String.join(",",
                str, number.toString()));
        System.out.println(result);
    }

    @Test
    public void testFunction() {
        Function3<Integer, Integer, Integer, Integer> function3 = (v1, v2, v3)
                -> (v1 + v2) * v3;
        Function3<Integer, Integer, Integer, Integer> composed =
                function3.andThen(v -> v * 100);
        int result = composed.apply(1, 2, 3);
        System.out.println(result);

        Function1<String, String> function1 = String::toUpperCase;
        Function1<Object, String> toUpperCase = function1.compose(Object::toString);
        String str = toUpperCase.apply(List.of("a", "b"));
        System.out.println(str);
    }

    @Test
    public void testFunction2() {
        Function4<Integer, Integer, Integer, Integer, Integer> function4 =
                (v1, v2, v3, v4) -> (v1 + v2) * (v3 + v4);
        Function2<Integer, Integer, Integer> function2 = function4.apply(1, 2);
        int result = function2.apply(4, 5);
        assertTrue(result == 27);
        assertTrue(12 == function2.apply(1).apply(3));
    }

    /**
     * function3, 在第一次的 curried 方法调用得到 Function1 之后, 通过 apply 来为第一个参数应用值.
     * 以此类推, 通过 3 次的 curried 和 apply 调用, 把全部 3 个参数都应用值
     */
    @Test
    public void testCurried() {
        Function3<Integer, Integer, Integer, Integer> function3 = (v1, v2, v3)
                -> (v1 + v2) * v3;
        int result =
                function3.curried().apply(1).curried().apply(2).curried().apply(3);
        assertTrue(result == 9);
    }

    /**
     * memoized 方法可以得到该函数的记忆化版本
     */
    @Test
    public void testMemoized() {
        Function2<BigInteger, Integer, BigInteger> pow = BigInteger::pow;
        Function2<BigInteger, Integer, BigInteger> memoized = pow.memoized();
        long start = System.nanoTime();
        memoized.apply(BigInteger.valueOf(1024), 1024);
        long end1 = System.nanoTime();
        memoized.apply(BigInteger.valueOf(1024), 1024);
        long end2 = System.nanoTime();
        memoized.apply(BigInteger.valueOf(200), 200);
        long end3 = System.nanoTime();
        System.out.printf("%d ms -> %d ms -> %d ms", end1 - start, end2 - end1, end3 - end2);
    }

    private static ThreadLocalRandom random =
            ThreadLocalRandom.current();

    /**
     * Either 表示可能有两种不同类型的值, 分别称为左值或右值. 只能是其中的一种情况. Either 通常用来表示成功或失败两种情况.
     * 惯例是把成功的值作为右值, 而失败的值作为左值. 可以在 Either 上添加应用于左值或右值的计算. 应用于右值的计算只有在 Either 包含右值时才生效, 对左值也是同理
     */
    @Test
    public void testEither() {
        Either<String, String> either = compute()
                .map(str -> str + "World")
                .mapLeft(Throwable::getMessage);
        System.out.println(either);
    }

    private static Either<Throwable, String> compute() {
        return random.nextBoolean()
                ? Either.left(new RuntimeException("Boom!"))
                : Either.right("Hello");
    }

    @Test
    public void testStream() {
        Map<Boolean, List<Integer>> booleanListMap = Stream.ofAll(1, 2, 3, 4, 5)
                .groupBy(v -> v % 2 == 0)
                .mapValues(Value::toList);
        System.out.println(booleanListMap);

        Tuple2<List<Integer>, List<Integer>> listTuple2 = Stream.ofAll(1, 2, 3, 4)
                .partition(v -> v > 2)
                .map(Value::toList, Value::toList);
        System.out.println(listTuple2);

        List<Integer> integers = Stream.ofAll(List.of("Hello", "World", "a"))
                .scanLeft(0, (sum, str) -> sum + str.length())
                .toList();
        System.out.println(integers);

        List<Tuple2<Integer, String>> tuple2List = Stream.ofAll(1, 2, 3)
                .zip(List.of("a", "b"))
                .toList();
        System.out.println(tuple2List);
    }

    @Test
    public void testFuture() {
        System.out.println("当前线程名称：" + Thread.currentThread().getName());
        Integer result = Future.of(() -> {
            System.out.println("future线程名称：" + Thread.currentThread().getName());
            Thread.sleep(2000);
            return 100;
        })
                .map(i -> i * 10)
                .await(3000, TimeUnit.MILLISECONDS) //等待线程执行3000毫秒
                .onFailure(e -> e.printStackTrace())
                .getValue() //返回Optional<Try<Integer>>类型结果
                .getOrElse(Try.of(() -> 100)) //如果Option 为 empty时，则返回Try(100)
                .get();
        System.out.println(result); // 1000
    }

    @Test
    public void testClosed() {
        final java.util.List<String> VISIT_KEYS_PREFIX = Lists.newArrayListWithCapacity(31 + 1);
        IntStream.rangeClosed(0, 31).forEach(number -> VISIT_KEYS_PREFIX.add(Joiner.on("-")
                .join("key", number)));

        VISIT_KEYS_PREFIX.stream().forEach(a -> System.out.println(a));
    }



}
