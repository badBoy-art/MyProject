package com.guava.study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.Set.study.Person;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.reflect.TypeToken;

public class GuavaStudy {

    @Test
    public void testTypeToken() {
        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {
        };
    }

    @Test
    public void testCheckArgument() {
        Preconditions.checkArgument(3 < 2, "Except i > j ,but i = %s, j = %s", 2, 3);
    }

    @Test
    public void testOrderingSort() {
        List<Date> dptTimes = Lists.newArrayList();
        Date date0 = new Date();

        dptTimes.add(date0);

        Ordering<Date> descOrdering = Ordering.natural().reverse();
        dptTimes = descOrdering.sortedCopy(dptTimes);
        Date maxDate = dptTimes.get(0);
        Date minDate = dptTimes.get(dptTimes.size() - 1);
        Date date1 = DateUtils.addDays(maxDate, 1);
        Date date2 = DateUtils.addDays(minDate, -1);

        List<Date> dates = Lists.newArrayList();
        dates.add(maxDate);
        dates.add(minDate);
        dates.add(date1);
        dates.add(date2);
        dates = descOrdering.sortedCopy(dates);
        System.out.println(dates.get(2));
        System.out.println(dates.get(1));
        System.out.println(maxDate.after(minDate));
    }

    @Test
    public void test03() {
        String str =
                Iterables.find(
                        new ArrayList<>(),
                        input -> false,
                        null);
        System.out.println(str);
    }

    @Test
    public void test04() {
        List<String> list = Lists.newArrayList();
        // list.add("a");
        list.add("b");
        list.add("ac");
        String str =
                Iterables.find(
                        list,
                        input -> true,
                        null);
        System.out.println(str);
    }

    @Test
    public void test06() {
        List<String> list = Lists.newArrayList();
        System.out.println(list.contains("a"));

        list.add("a");
        list.add("b");
        list.add("ac");
        Iterable<String> iterable =
                Iterables.filter(
                        list,
                        input -> input.equalsIgnoreCase("ac"));
        System.out.println(iterable);
        AtomicInteger i = new AtomicInteger(1);
        System.out.println(
                list.stream().map(s -> i.incrementAndGet() + ":" + s).collect(Collectors.toList()));

        System.out.println(iterable.iterator().hasNext());
        System.out.println(list.stream().filter(f -> f.equalsIgnoreCase("a")).collect(Collectors.toList()));

        String[] s = list.stream().filter(f -> f.equalsIgnoreCase("a")).toArray(String[]::new);

        System.out.println(s);
    }

    @Test
    public void test05() {
        List<Date> dates = Lists.newArrayList();

        dates.add(new Date());
        dates.add(DateUtils.addDays(new Date(), 2));
        System.out.println(dates);
        Collections.sort(
                dates,
                Comparator.reverseOrder());
        System.out.println(dates);
        System.out.println(dates.subList(0, 1));
        System.out.println(dates.subList(0, 2));
    }

    @Test
    public void testCollectionSort() {
        List<Date> dptTimes = Lists.newArrayList();
        Date date0 = new Date();

        dptTimes.add(date0);
        dptTimes.add(DateUtils.addDays(date0, 2));

        Collections.sort(dptTimes);
        System.out.println(dptTimes);
    }

    @Test
    public void testOptional() {
        Optional<String> optional = Optional.of("abc");
        System.out.println(optional);
    }

    @Test
    public void testSing() {
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        List<List<String>> cList = Collections.singletonList(list);
        System.out.println(cList.size());
        System.out.println(cList);

        list = cList.get(0);
        System.out.println(list);
    }

    @Test
    public void test() {
        int[][] m = {{1, 1, 0}, {1, 1, 1}, {0, 1, 1}};
        System.out.println(findCircleNum(m));
    }

    @Test
    public void testRemove() {
        final List<Person> persons = Lists.newArrayListWithCapacity(5);
        Person person = new Person();
        person.setName("zhangsan");
        Person person1 = new Person();
        person1.setName("list");
        Person person2 = new Person();
        person2.setName("wangwu");
        persons.add(person);
        persons.add(person1);
        persons.add(person2);

        System.out.println(persons);

        persons.remove(person);
        System.out.println(persons);
        persons.add(person);
        System.out.println(persons);
    }

    @Test
    public void testImmutable() {
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("4");
        list.add("4");
        System.out.println(list);
        list = ImmutableSet.copyOf(list.stream().filter(Predicates.not(Predicates.isNull())::apply).collect(Collectors.toList())).asList();
        System.out.println(list);
    }

    public int findCircleNum(int[][] M) {
        List<Integer> max = new ArrayList<Integer>();
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                int num = deepSearch(M, i, j);
                if (num != 0) {
                    max.add(1);
                }
            }
        }
        return max.size();
    }

    private int deepSearch(int[][] grid, int i, int j) {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid[i].length && grid[i][j] == 1) {
            grid[i][j] = 0;
            return 1 + deepSearch(grid, i - 1, j) + deepSearch(grid, i + 1, j) + deepSearch(grid, i, j - 1) + deepSearch(grid, i, j + 1);
        } else {
            return 0;
        }
    }

    @Test
    public void testDistinct() {
        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3, 3, 4, 5, 6, 6, 6, 7, 8));
        System.out.println(numbersList);
        List<Integer> listWithoutDuplicates = numbersList.stream().distinct().collect(Collectors.toList());
        System.out.println(listWithoutDuplicates);
    }

}
