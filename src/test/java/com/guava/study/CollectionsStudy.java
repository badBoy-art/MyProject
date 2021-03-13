package com.guava.study;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.Extends.study.WhiteDog;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;

/**
 * @author xuedui.zhao
 * @create 2019-07-27
 */
public class CollectionsStudy {

    /**
     * key-value  key可以重复
     */
    @Test
    public void teststuScoreMultimap() {
        Multimap<String, WhiteDog> multimap = ArrayListMultimap.create();
        for (int i = 10; i < 20; i++) {
            WhiteDog whiteDog = new WhiteDog();
            whiteDog.setName("name" + i);
            whiteDog.setAge(i);
            multimap.put("dog", whiteDog);
        }
        System.out.println("scoreMultimap:" + multimap.size());
        System.out.println("scoreMultimap:" + multimap.keys());

        Collection<WhiteDog> studentScore = multimap.get("dog");
        System.out.println(studentScore);
        studentScore.clear();
        WhiteDog studentScoreNew = new WhiteDog();
        studentScoreNew.setName("1024");
        studentScoreNew.setAge(1024);
        studentScore.add(studentScoreNew);

        System.out.println("scoreMultimap:" + multimap.size());
        System.out.println("scoreMultimap:" + multimap.keys());
    }

    /**
     * 无序+可重复   count()方法获取单词的次数  增强了可读性+操作简单
     */
    @Test
    public void testMultiSet() {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("c");
        multiset.add("c");

        for (Multiset.Entry<String> entr :
                multiset.entrySet()) {
            System.out.println(multiset.count(entr.getElement()));
        }
    }

    /**
     * 双向Map(Bidirectional Map) 键与值都不能重复
     */
    @Test
    public void testBiMap() {
        BiMap<String, Integer> userId = HashBiMap.create();

        userId.put("str", 1);
        userId.put("str1", 2);

        String userForId = userId.inverse().get(2);

        System.out.println(userForId);
        System.out.println(userId.get("str"));
    }

    /**
     * 双键的Map Map--> Table-->rowKey+columnKey+value  //和sql中的联合主键有点像
     */
    @Test
    public void testBasedTable() {
        Table<String, String, Integer> tables = HashBasedTable.create();
        tables.put("zhang", "san", 10);
        tables.put("li", "liu", 12);
        tables.put("li", "si", 11);

        System.out.println(tables);
        System.out.println(tables.get("li", "si"));
        System.out.println(tables.get("wang", "wu"));
    }

    @Test
    public void testPredicates() {
        //按照条件过滤
        ImmutableList<String> names = ImmutableList.of("begin", "code", "Guava", "Java");
        Iterable<String> fitered = Iterables.filter(names, Predicates.or(Predicates.equalTo("Guava"), Predicates.equalTo("Java")));
        System.out.println(fitered); // [Guava, Java]

        //自定义过滤条件   使用自定义回调方法对Map的每个Value进行操作
        ImmutableMap<String, Integer> m = ImmutableMap.of("begin", 12, "code", 15);
        // Function<F, T> F表示apply()方法input的类型，T表示apply()方法返回类型
        Map<String, Integer> m2 = Maps.transformValues(m, input -> {
            if (input > 12) {
                return input;
            } else {
                return input + 1;
            }
        });
        System.out.println(m2);
    }

    @Test
    public void testMap() {
        HashMap<String, Integer> mapA = Maps.newHashMap();
        mapA.put("a", 1);
        mapA.put("b", 2);
        mapA.put("c", 3);

        HashMap<String, Integer> mapB = Maps.newHashMap();
        mapB.put("b", 20);
        mapB.put("c", 3);
        mapB.put("d", 4);

        MapDifference differenceMap = Maps.difference(mapA, mapB);
        differenceMap.areEqual();
        Map entriesDiffering = differenceMap.entriesDiffering();
        Map entriesOnlyLeft = differenceMap.entriesOnlyOnLeft();
        Map entriesOnlyRight = differenceMap.entriesOnlyOnRight();
        Map entriesInCommon = differenceMap.entriesInCommon();

        System.out.println(entriesDiffering);   // {b=(2, 20)}
        System.out.println(entriesOnlyLeft);    // {a=1}
        System.out.println(entriesOnlyRight);   // {d=4}
        System.out.println(entriesInCommon);    // {c=3}
    }

    @Test
    public void testStopwatch() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
        }
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(nanos);
    }

    @Test
    public void testUnmodifiable() {
        List<String> list = Lists.newArrayList("1", "2");
        List<String> immutable = ImmutableList.of("a", "b", "c");
        list.add("3");
        List<String> immutable2 = ImmutableList.copyOf(list);

        list.add("4");

        System.out.println(immutable);
        System.out.println(immutable2);
    }

    @Test
    public void testTable() {
        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("zhangsan", "english", 80);
        table.put("zhangsan", "math", 100);
        table.put("zhangsan", "yuwen", 90);
        table.put("lisi", "english", 80);
        table.put("lisi", "math", 97);
        table.put("lisi", "yuwen", 96);

        Set<Table.Cell<String, String, Integer>> set = table.cellSet();
        for (Table.Cell cell : set) {
            System.out.println(cell.getRowKey() + "," + cell.getColumnKey() + "," + cell.getValue());
        }

        Set<String> rowSet = table.rowKeySet();
        System.out.println(rowSet);

        Set columnSet = table.columnKeySet();
        System.out.println(columnSet);

        System.out.println(table.row("zhangsan"));

        System.out.println(table.column("math"));
    }
}
