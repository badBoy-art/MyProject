package com.base;

import static java.lang.Double.compare;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import joptsimple.internal.Strings;


/**
 * @author zhaoxuedui <zhaoxuedui>
 * Created on 2020-03-27
 * @Description
 */
public class Base {

    @Test
    public void tsetFilter() {
        List<String> strs = Lists.newArrayList("zhangsan", "lisi", "wangwu");
        System.out.println(strs.toString());
        System.out.println(strs.stream().filter(s -> "ss".equalsIgnoreCase(s)).map(x -> new AbstractMap.SimpleEntry<>(x, x)).filter(s -> "wangwu".equalsIgnoreCase(s.getValue()))
                .collect(toSet()));
    }

    @Test
    public void testListRemove() {
        List<Person> allPerson = Lists.newArrayList(Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("zs")
                        .hobbits(Lists.newArrayList("1", "2"))
                        .build(),
                Person.builder()
                        .address("beijing")
                        .age(11)
                        .name("ls")
                        .hobbits(Lists.newArrayList("3", "4"))
                        .build(),
                Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("ww")
                        .hobbits(Lists.newArrayList("5", "6"))
                        .build());

        List<List<Person>> parts = Lists.partition(allPerson, 5);
        System.out.println("0=====" + parts.get(0 / 5));


        List<Person> fp = allPerson.stream().map(person -> {
            if (person.getName().equals("ww")) {
                person.setName("nl");
            }
            return person;
        }).filter(person -> !person.getName().equals("nl")).collect(toList());

        System.out.println("fp=====" + fp);

        List<Person> persons = Lists.newArrayList(
                Person.builder()
                        .address("beijing")
                        .age(11)
                        .name("ls")
                        .hobbits(Lists.newArrayList("3", "4")).build(),
                Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("ww").build());

        List<String> hobbits = allPerson.stream()
                .flatMap(x -> x.getHobbits().stream())
                .collect(Collectors.toList());

        System.out.println("hobbits=====" + hobbits);

        allPerson.removeAll(persons);
        System.out.println("allPerson=====" + allPerson);
        persons.addAll(allPerson);
        System.out.println("persons=====" + persons);

        List<Integer> brandIds = persons.stream()
                .mapToInt(Person::getAge).boxed().collect(Collectors.toList());
        System.out.println("brandIds=====" + brandIds);

        System.out.println(persons.stream()
                .mapToInt(Person::getAge).summaryStatistics().getSum());

    }

    @Test
    public void test() {
        /**
         * 它就是获取系统属性的
         */
        System.out.println(Long.getLong("12"));
        System.out.println(Long.getLong("idea.test.cyclic.buffer.size"));
        System.out.println(System.getProperty("idea.test.cyclic.buffer.size"));
        System.out.println(System.getProperties());
        System.out.println(Long.valueOf("12"));
        System.out.println(Long.valueOf(1));
        System.out.println(Long.parseLong("12"));
        List<Long> activityIds = Lists.newArrayList(1L, 2L, 3L, 4L, 5L);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select row_num as ranking,id,user_id as userId,poi_id as poiId,");
        stringBuffer.append(" photo_id as photoId,activity_id as activityId ,view_count as viewCount,");
        stringBuffer.append(" status as status,create_time as createTime, update_time as updateTime  from ( ");
        stringBuffer.append(" select @row := @row + 1 as row_num, d.id, d.user_id,d.poi_id,d.activity_id,d.photo_id,");
        stringBuffer.append(" d.view_count,d.create_time,d.update_time,d.status from poi_activity_associated d , ");
        stringBuffer.append(" (SELECT @row := 0) r ");
        stringBuffer.append(" where status = 1 and poi_id = ").append(6724909338866060000L);
        stringBuffer.append(" and create_time >= ").append(1605284400000l);
        stringBuffer.append(" and create_time <= ").append(1606148400000l);
        stringBuffer.append(" and activity_id = ");
        stringBuffer.append(1);
        stringBuffer.append(" ORDER BY view_count desc,id )");
        stringBuffer.append(" a where user_id = ").append(1726664404).append(" order by ranking limit 1");

        System.out.println(stringBuffer.toString());
        System.out.println(stringBuffer.toString());
    }

    @Test
    public void testClassName() {
        System.out.println(getClass().getName());
        System.out.println(getClass().getSimpleName());
    }

    @Test
    public void test0x7fff() {
        System.out.println(0x7fff);
    }

    @Test
    public void test_() {
        int a = 10;
        /**
         * a++ 分为三个阶段
         * 1、内存到寄存器
         * 2、寄存器自增
         * 3、写回内存
         * 这三个阶段中间都可以被中断分离开
         */
        int b = a++;
        int c = a + 1;
        System.out.println("a = " + a + " b = " + b + " c = " + c);

        List<String> list = new ArrayList<>();
        list.add("abs");
        list.addAll(Collections.emptyList());
        System.out.println(list);
    }

    @Test
    public void testCollection() {
        List<Person> list = Lists.newArrayList(Person.builder()
                        .address("beijing")
                        .age(12)
                        .name("zs")
                        .sex(0)
                        .salary(200D)
                        .hobbits(Lists.newArrayList("1", "2"))
                        .build(),
                Person.builder()
                        .address("beijing")
                        .age(12)
                        .name("ls")
                        .sex(1)
                        .salary(300D)
                        .hobbits(Lists.newArrayList("3", "4"))
                        .build(),
                Person.builder()
                        .address("beijing")
                        .age(10)
                        .name("ww")
                        .salary(400D)
                        .hobbits(Lists.newArrayList("5", "6"))
                        .build());

        Map<String, Person> map = list.stream().collect(Collectors.toMap(Person::getName, a -> a, (k1, k2) -> k1))
                .entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.comparing(Person::getAge)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new));

        Map<String, String> map2 = map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue().getName()));

        System.out.println("map2 ===== " + map2);
        System.out.println(map.containsKey("ls"));
        System.out.println(map);

        List<Person> list2 = Lists.newArrayList();
        list2.add(Person.builder().age(13).name("test").build());
        list2.addAll(list);
        System.out.println(list2);
        System.out.println(list.stream().sorted(Comparator.comparing(Person::getAge).thenComparing((o1, o2) -> compare(o2.getSalary(), o1.getSalary()))).collect(Collectors.toList()));
        System.out.println(list.stream().max(Comparator.comparing(Person::getAge).thenComparing((o1, o2) -> o2.getSex() - o1.getSex())).get());

        System.out.println(list.stream().mapToLong(Person::getAge).boxed().collect(Collectors.toList()));

    }

    @Test
    public void testComparator() {
        List<Person> list = Lists.newArrayList();
        Person p1 = Person.builder().salary(0.0).build();
        Person p2 = Person.builder().salary(-0.0).build();
        Person p3 = Person.builder().salary(-(0.0d / 0.0)).build();
        Person p4 = Person.builder().salary(0x7ff8000000000000L).build();

        list.add(p1);
        list.add(p2);
        //list.add(p3);
        //list.add(p4);
        System.out.println(list.subList(0, list.size()));
        List<Person> listSorted = list.stream().sorted(Comparator.comparingDouble(Person::getSalary)).collect(toList());
        System.out.println(listSorted);
    }

    @Test
    public void testDoubleToLongBits() {
        System.out.println(Double.NaN == Double.NaN);
        System.out.println(compare(Double.NaN, Double.NaN));
        System.out.println(Double.doubleToLongBits(Double.NaN));
        System.out.println(Double.doubleToLongBits(-0.0));
        System.out.println(Float.intBitsToFloat(0x7fc00000));
        System.out.println(Double.longBitsToDouble(0x7ff8000000000000L));
        System.out.println(Double.doubleToLongBits(0x7fc00000));
    }

    @Test
    public void testGeneric() {
        OptionManager manager = new OptionManager();
    }

    @Test
    public void testSet() {
        int a = 3;

        int b = a = 4;

        System.out.println("a = " + a + ", b = " + b);
    }

    @Test
    public void test1() {
        System.out.println(1708374563 & 31);
        System.out.println(~4 + 1);
    }

    @Test
    public void test07() {
        System.out.println((1 << 5) - 1);
        System.out.println((int) Math.ceil(13 / 200));
        System.out.println(764 % 1000);
        System.out.println(Math.floorDiv(6500, 1000) + 1);
    }

    @Test
    public void testIntegerNullPointer() {
        Person person = Person.builder().name("zhangsan").build();
        Person1 person1 = Person1.builder().build();

        person1.setName(person.getName());
        person1.setAge(person.getAge());
    }

    @Test
    public void testHashCode() {
        System.out.println(Hashing.sha256().hashBytes("".getBytes()).toString());
        System.out.println(Hashing.sha256().hashBytes("cba".getBytes()).toString());
        System.out.println(Hashing.sha256().hashBytes("abc".getBytes()).toString());
        System.out.println("1724520".hashCode());
    }

    @Test
    public void testList() {
        String frontImage = "111";
        String image = "[\"//static.yximgs.com/bs2/op-vc-merchant/PIC-3c62c32e-b0a8-450a-bf8e-986cef6adb82.txt\"]";
        frontImage = StringUtils.isBlank(frontImage) ? Strings.EMPTY : frontImage;

        List<String> list = Lists.newArrayList(frontImage);
        if (StringUtils.isNotBlank(image)) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            JsonParser parser = new JsonParser();
            JsonArray jsonarray = parser.parse(image).getAsJsonArray();
            for (JsonElement element : jsonarray) {
                list.add(gson.fromJson(element, String.class));
            }
        }
        System.out.println(list);
        list.addAll(list);
        System.out.println(Joiner.on(";").join(list));
        System.out.println(Splitter.on(";").splitToList(Joiner.on(";").join(list)).get(0));
    }

    @Test
    public void testMathRound() {
        System.out.println(Math.round(318769L * 0.85));
        System.out.println(Math.round(270954 / (1 - 0.15D)));
        System.out.println(270954 / 5);
        System.out.println(270954 % 5);
    }

    /**
     * 如果流中的数据量足够大，并行流可以加快处速度
     */
    @Test
    public void testParallelStream() {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
        // 匹配任意（适用于并行流）
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x < 6);
        System.out.println("匹配第一个值：" + findFirst.get());
        System.out.println("匹配任意一个值：" + findAny.get());
        System.out.println("是否存在大于6的值：" + anyMatch);
    }

    @Test
    public void stream() {
        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(5);
        stream2.forEach(System.out::println);
        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
        stream3.forEach(System.out::println);

        Random random = new Random();
        Stream<Integer> stream4 = Stream.generate((Supplier<Integer>) () -> random.nextInt(5)).limit(3);
        stream4.forEach(System.out::println);
    }

    @Test
    public void testFlatMap() {
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> listNew = list.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split(",");
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        }).collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + listNew);
    }

    @Test
    public void testReduce() {
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        // 求和方式1
        Optional<Integer> sum = list.stream().reduce((x, y) -> x + y);
        // 求和方式2
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        // 求和方式3
        Integer sum3 = list.stream().reduce(0, Integer::sum);
        // 求乘积
        Optional<Integer> product = list.stream().reduce((x, y) -> x * y);
        // 求最大值方式1
        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);
        // 求最大值写法2
        Integer max2 = list.stream().reduce(1, Integer::max);
        System.out.println("list求和：" + sum.get() + "," + sum2.get() + "," + sum3);
        System.out.println("list求积：" + product.get());
        System.out.println("list求和：" + max.get() + "," + max2);
    }

    @Test
    public void testCollect() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Tom", 8900, 23, "male"));
        personList.add(new Person("Jack", 7000, 25, "male"));
        personList.add(new Person("Lily", 7800, 21, "female"));
        // 求总数
        Long count = personList.stream().collect(Collectors.counting());
        // 求平均年龄
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getAge));
        // 求最高年龄
        Optional<Integer> max = personList.stream().map(Person::getAge).collect(Collectors.maxBy(Integer::compare));
        // 求年龄之和
        Integer sum = personList.stream().collect(Collectors.summingInt(Person::getAge));
        // 一次性统计所有信息
        IntSummaryStatistics collect = personList.stream().collect(Collectors.summarizingInt(Person::getAge));
        System.out.println("员工总数：" + count);
        System.out.println("员工平均年龄：" + average);
        System.out.println("员工年龄总和：" + sum);
        System.out.println("员工年龄所有统计：" + collect);
    }

    @Test
    public void testPartitioningByGroupingBy() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Tom", 89, 0, "male"));
        personList.add(new Person("Jack", 70, 1, "male"));
        personList.add(new Person("Lily", 70, 0, "female"));
        personList.add(new Person("Lily", 80, 0, "female"));

        Map<Boolean, List<Person>> part = personList.stream().collect(Collectors.partitioningBy(x -> x.getAge() > 8000));
        // 将员工按性别分组
        Map<Integer, List<Person>> group = personList.stream().collect(Collectors.groupingBy(Person::getSex));
        Map<String, Map<String, List<Person>>> collect = personList.stream()
                .collect(Collectors
                        .groupingBy(Person::getName, Collectors.groupingBy(Person::getAddress)));
        System.out.println(collect);
        // 将员工先按性别分组，再按地区分组
        Map<Integer, Map<Integer, List<Person>>> group2 = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getAge)));
        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);
    }

    @Test
    public void testList2Map() {
        List<Long> list = Lists.newArrayList(1L, 2L, 3L, 4L);
        Map<Long, Integer> map = list.stream().collect(Collectors.toMap(new Function<Long, Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable Long t) {
                return t;
            }
        }, new Function<Long, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable Long aLong) {
                return 0;
            }
        }));
        System.out.println(map);
    }

    @Test
    public void testSort() {

        String json = "{\"330100\": \n" +
                "    \"HZS#杭州市\"\n" +
                "  ,\n" +
                "  \"330200\": \n" +
                "    \"NBS#宁波市\",\n" +
                "  \"330300\": \n" +
                "    \"WZS#温州市\"\n" +
                "  ,\n" +
                "  \"330400\": \n" +
                "    \"JXS#嘉兴市\"\n" +
                "  ,\n" +
                "  \"330500\": \n" +
                "    \"HZS#湖州市\"\n" +
                "  ,\n" +
                "  \"330600\": \n" +
                "    \"SXS#绍兴市\",\n" +
                "  \"330700\": \n" +
                "    \"JHS#金华市\"\n" +
                "  ,\n" +
                "  \"330800\": \n" +
                "    \"ZZS#衢州市\"\n" +
                "  ,\n" +
                "  \"330900\": \n" +
                "    \"ZSS#舟山市\"\n" +
                "  ,\n" +
                "  \"331000\": \n" +
                "    \"TZS#台州市\"\n" +
                "  ,\n" +
                "  \"331100\": \n" +
                "    \"LSS#丽水市\"\n" +
                "  \n" +
                "}";

        Map<String, String> maps = (Map) JSON.parse(json);
        List<Address> addresses = Lists.newArrayList();
        for (Map.Entry<String, String> map : maps.entrySet()) {
            Address address = new Address();
            address.setCode(map.getKey());
            address.setName(map.getValue());
            address.setChildren(Collections.emptyList());
            addresses.add(address);
        }
        List<Address> sorted = addresses.stream().sorted(Comparator.comparing(Address::getName)).collect(toList());
        sorted.stream().forEach(x -> x.setName(Splitter.on("#").splitToList(x.getName()).get(1)));
        System.out.println(new Gson().toJson(sorted));
    }

    @Test
    public void testPic() {
        /**
         * 校验图片文件格式是否合法
         */
        final String[] FORMATS = new String[]{"jpg", "jpeg", "png", "gif"};

        String[] params = "11.jpg".split("\\.");
        String format = params[params.length - 1];
        System.out.println(Arrays.asList(FORMATS).contains(format));
    }

    @Test
    public void testpin() {
        List<Integer> sources = Lists.newArrayList(1, 2, 3);
        String source = Joiner.on(",").skipNulls().join(sources);
        String sql = String.format(" delete from a where id > 0 and source in (%s)", source);
        System.out.println(sql);
    }

    @Test
    public void testSql() {
        String str = "6724909544116701482,\n" +
                "6724909355514082062,\n";

        List<String> poiIds = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(str);

        String sql = "update _%s set image_url = \"1620891911016.png\" where poi_id = %s;";

        poiIds.forEach(x -> System.out.println(String.format(sql, (Long.parseLong(x) % 10) + "", x)));

    }

    @Test
    public void testMapToInt() {
        List<Integer> list = Lists.newArrayList(10, 11, 12);
        Integer sum = list.stream().mapToInt(x -> x).sum();
        System.out.println("sum == " + sum);
    }

    @Test
    public void testUnmodifiableMap() {
        Map<String, String> parsedArgs = new HashMap<>();
        Map<String, String> map = Collections.unmodifiableMap(parsedArgs);
        System.out.println(map.get("1"));
    }

    @Test
    public void testComputeIfAbsent() {
        Map<String, String> map = new HashMap<>();
        map.computeIfAbsent("Hello", new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return input + " World";
            }
        });

        System.out.println(map);

    }

    @Test
    public void testInstanceofAndIsAssignableFrom() {
        Person person = new Person();
        person.setName("test");
        person.setAge(15);

        System.out.println(person instanceof Person);
        System.out.println(person.getClass().isAssignableFrom(Person.class));
    }

}

