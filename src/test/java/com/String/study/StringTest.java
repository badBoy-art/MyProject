package com.String.study;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.Extends.study.Animal;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

/**
 * @author xuedui.zhao
 * @create 2018-06-25
 */
public class StringTest {

    private static String tips = "您有%s单报销凭证可以一起补开报销凭证";

    @Test
    public void test01() {
        System.out.println(String.format(tips, 15));
    }

    @Test
    public void test02() {
        String regex = "(^[A-Z0-9]{15}+$)|(^[A-Z0-9]{18}+$)|(^[A-Z0-9]{20}+$)";
        Pattern pattern = Pattern.compile(regex);
        boolean re = pattern.matcher("A0123456789123456789").find();
        System.out.println(re);

        String regex1 = "^[0-9]{19}";
        Pattern pattern1 = Pattern.compile(regex1);
        System.out.print(pattern1.matcher("672490938470225263").find());
    }

    @Test
    public void test03() {
        String jsonArray = "[{\"name\":\"zhangsan\",\"age\":11},{\"name\":\"lisi\",\"age\":2}]";
        List<Animal> brands = JSONObject.parseArray(jsonArray, Animal.class);
        System.out.println(brands);
    }

    @Test
    public void test04() {
        System.out.println(DateUtils.addDays(new Date(), -120).getTime());
    }

    @Test
    public void test05() {
        String jsonArray = "[{\"name\":\"zhangsan\",\"age\":11},{\"name\":\"lisi\",\"age\":2}]";
        List<Animal> brands = JSONObject.parseArray(jsonArray, Animal.class);
        List<String> orderNos =
                Lists.transform(
                        new ArrayList<>(),
                        new Function<Animal, String>() {
                            @Nullable
                            @Override
                            public String apply(@Nullable Animal input) {
                                return input.getName();
                            }
                        });
        List<String> orderNoList = new ArrayList<>(orderNos);
        if (!orderNos.contains("1111")) {
            orderNoList.add("1111");
        }

        System.out.println(orderNoList);

        System.out.println("http://pavo.elongstatic.com/i/Mobile640_960/000duav5.jpg".contains("//pav4o.elongstatic.com/i/Mobile640_960/000duav5.jpg"));
    }

    @Test
    public void test06() {
        System.out.println(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(new Date()));

        System.out.println(DateFormatUtils.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd"));
        System.out.println(DateFormatUtils.format(DateUtils.addDays(new Date(), 1), "yyyy-MM-dd"));

        System.out.println(
                LocalDate.of(2020, 8, 20).toEpochDay());
    }

    @Test
    public void test07() {
        List<String> list = Lists.newArrayList();
        list.add("zhangsan");
        Iterables.find(
                list,
                new Predicate<String>() {
                    @Override
                    public boolean apply(String input) {
                        return input.equals("lisi");
                    }
                },
                null);
    }

    @Test
    public void test08() {
        List<String> list = new ArrayList<>();
        // 然后通过比较器来实现排序
        Collections.sort(
                list,
                new Comparator<String>() {
                    // 升序排序
                    @Override
                    public int compare(String o1, String o2) {
                        return o2.compareTo(o1);
                    }
                });
        System.out.println(list);
    }

    @Test
    public void test09() {
        System.out.println(DateUtils.addDays(new Date(), 10));
    }

    @Test
    public void test10() {
        String str = "PN6388";
        System.out.println(str.substring(0, 2));
    }

    @Test
    public void test11() {
        List<Date> dates = Lists.newArrayList();
        dates.add(new Date());
        dates.add(DateUtils.addDays(new Date(), 1));
        List<Date> dateList = Ordering.natural().sortedCopy(dates);
        System.out.println(dateList.get(dateList.size() - 1));
        System.out.println(dateList.get(0));
    }

    @Test
    public void test12() {
        try {
            String str = "FFFF8C98-91C8-4894-A13C-BE48CA2BA57E\t上海到西安的飞机票\tflight\tPV\t阿里\t阿里\t2018-06-14";
            System.out.println(str.getBytes("iso-8859-1").length);
            System.out.println(str.getBytes("utf-8").length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test13() {
        String email = "xuedui.zhao@qunar.com";
        int index = email.lastIndexOf("@");
        StringBuilder stringBuilder = new StringBuilder(20);
        if (index < 3) {
            stringBuilder.append(email.substring(0, 1));
        } else {
            stringBuilder.append(email.substring(0, 2));
        }
        stringBuilder.append("*****");
        stringBuilder.append(email.substring(index));
        System.out.println(stringBuilder);
    }

    @Test
    public void test14() {
        System.out.println(!"true".equalsIgnoreCase(null));
    }

    @Test
    public void test15() {
        System.out.println("url==" + null);
    }

    @Test
    public void test16() {
        String companyCodes = "yaccbx,tpysz,hxbj,zybxsc,zybxgd,zybxah,tpyxm,jlrs，fbcx，";

        String[] codes = StringUtils.split(companyCodes, ",，");
        Set<String> codeSet = Sets.newHashSet(codes);
        if (!codeSet.contains("JLRS")) {
            System.out.println("true");
        }

        System.out.println(StringUtils.stripEnd(companyCodes, "，"));

    }

    @Test
    public void test17() {
        String str = "11111";
        try {
            JSONObject.parseObject(str);
            System.out.println("true");
        } catch (Exception e) {
            System.out.println("false");
        }

    }

    @Test
    public void test18() {
        String str = "  1 2 3 ";
        str = StringUtils.stripStart(str, " ");
        str = StringUtils.stripEnd(str, " ");
        System.out.println(str);
    }

    @Test
    public void test19() {
        System.out.println("XCD" + DateFormatUtils.format(new Date(), "MMddHHmm") + String.format("%09d", 5277509));
    }

    @Test
    public void test20() {
        String str = "1;2;3;4;5;6;7;8;9";
        System.out.println(str.endsWith(";"));
        System.out.println(str.endsWith(";") ? StringUtils.stripEnd(str, ";") : str);
    }

    @Test
    public void test21() {
        List<String> swiftPdfUrls = Lists.newArrayList(Splitter.on(";")
                .trimResults()
                .omitEmptyStrings()
                .split("a;b; ; ; d;"));
        System.out.println(swiftPdfUrls);

        List<String> swiftPdfs = Lists.newArrayList(Splitter.on(CharMatcher.anyOf(";，"))
                .trimResults()
                .omitEmptyStrings()
                .split("a;b; ， ; d;"));
        System.out.println(swiftPdfs);
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            Thread.sleep(3000L);
            System.out.println("currentTimeMillis = " + System.currentTimeMillis());
        }
    }

    @Test
    public void test22() {
        System.out.println(StringUtils.isBlank(null));
    }

    @Test
    public void test23() {
        //1&1=1 , 1&0=0 , 0&1=0 , 0&0=0
        //负数二进制
        /**1. 先将 - 5 的绝对值转换成二进制，即为0000 0101；
         2. 然后求该二进制的反码，即为 1111 1010；
         3. 最后将反码加1，即为：1111 1011
         */
        System.out.println(2 & -2);
    }

    @Test
    public void test24() {
        String Address = Joiner.on(" ").join("hebei", "tangshan", "duh", Strings.nullToEmpty(null));
        System.out.println("Address == " + Address);
        System.out.println(StringUtils.deleteWhitespace(Address));
        System.out.println(Joiner.on(";").skipNulls().join("hebei", "shang;hai", "tangshan", "duh"));
    }

    @Test
    public void test25() {
        String str = "{张三，131124198908042810， }";
        str = StringUtils.stripStart(str, "{");
        str = StringUtils.stripEnd(str, "}");
        List<String> strs = Splitter.onPattern(",|，").trimResults().omitEmptyStrings().splitToList(str);
        System.out.println(strs.size());
        System.out.println(strs.get(0));
        System.out.println(strs.get(1));
    }

    @Test
    public void testItern() {
        String strHeap = new String("abc");
        String str = "abc";

        System.out.println(str.equals(strHeap));
        System.out.println(str == strHeap);
        System.out.println(str.equals(strHeap.intern()));
        System.out.println(str == strHeap.intern());
        System.out.println("--------------------");

        String s = new String("1");
        s.intern();
        String s2 = "1";
        System.out.println(s == s2);
        System.out.println(s.intern() == s2);

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);
        System.out.println(s3.intern() == s4);
        System.out.println("--------------------");

        String ss = new String("1");
        String ss2 = "1";
        ss.intern();
        System.out.println(ss == ss2);

        String ss3 = new String("1") + new String("1");
        String ss4 = "11";
        ss3.intern();
        System.out.println(ss3 == ss4);
    }

    @Test
    public void test27() {
        System.out.println(StringUtils.substringAfter("86-15181977137", "-"));
    }

    @Test
    public void test28() {
        System.out.println(StringUtils.length("R201906151350044367822"));
        System.out.println(StringUtils.stripStart("R201906151350044367822", "R"));
    }

    @Test
    public void test29() {
        String str = "";
        System.out.println(str.isEmpty());
    }

    /**
     * 获取最长子串
     */
    @Test
    public void testLongestSubstring() {
        String str = "abcdefabcbb";
        String maxSubStr = "";
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>(str.length());

        char[] charArra = str.toCharArray();

        for (int i = 0; i < charArra.length; i++) {
            if (list.contains(charArra[i] + "")) {
                for (String subStr : list) {
                    sb.append(subStr);
                }
                list = list.subList(list.indexOf(charArra[i] + ""), list.size());
                if (sb.length() > maxSubStr.length()) {
                    maxSubStr = sb.toString();
                }
                sb.delete(0, sb.length());
            } else {
                list.add("" + charArra[i]);
            }
        }

        System.out.println(maxSubStr.length());
    }

    /**
     * 公共前缀
     */
    @Test
    public void testCommonPrefix() {
        String[] strs = new String[]{"flower", "flow", "flight"};

        //按照字符串长度排序
        Set<String> srtSet = new TreeSet<>(new MyCom());

        //获得长度最小的字符串
        for (int i = 0; i < strs.length; i++) {
            srtSet.add(strs[i]);
        }
        List<String> list = new ArrayList<String>(srtSet);
        String mixStr = list.get(0);
        boolean flag = true;
        for (String s : list) {
            if (!s.startsWith(mixStr)) {
                flag = false;
                break;
            }
        }


        if (flag) {
            System.out.println(mixStr);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (strs.length > 0) {
            stringBuilder.append(mixStr.charAt(0) + "");
        }

        for (int i = 1; i < mixStr.length(); i++) {
            flag = true;
            for (String s : list) {
                if (!s.startsWith(stringBuilder.toString())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                stringBuilder.append(mixStr.charAt(i) + "");
            }
        }
        System.out.println(stringBuilder.substring(0, stringBuilder.length() - 1));
    }

    @Test
    public void testStringJoiner() {
        List<String> list = Lists.newArrayList("a", "B", "C");
        StringJoiner stringJoiner = new StringJoiner(";");
        stringJoiner.add("");
        stringJoiner.add("https://static.yximgs.com/bs2/op-vc-merchant/PIC-86a3e1f5-1285-474f-995a-b30f4869cf23.jpg");

        list.subList(1, list.size()).forEach(stringJoiner::add);

        System.out.println(stringJoiner.toString());
    }

    @Test
    public void testRestoreIpAddresses() {
        List<String> res = new ArrayList<>();
        String str = "25525511135";
        helper(str, "", res, 0);
        System.out.println(res);
    }

    public void helper(String s, String tmp,
                       List<String> res, int n) {
        // 剪枝，因ip不会超过三位
        if (s.length() > 3 * (4 - n)) {
            return;
        }
        if (n == 4) {
            if (s.length() == 0) {
                // substring here to get rid of last '.'
                // 去掉最后一个'.'
                res.add(tmp.substring(0, tmp.length() - 1));
            }
            return;
        }
        for (int k = 1; k <= 3; k++) {
            // 如果剩余的长度还不够k那么说明不能排列成ip
            if (s.length() < k) {
                break;
            }
            int val = Integer.parseInt(s.substring(0, k));
            // in the case 010 the parseInt will return len=2
            // where val=10, but k=3, skip this.
            // k != String.valueOf(val).length(),为了避免前导0情况
            if (val > 255 || k != String.valueOf(val).length()) {
                continue;
            }
            helper(s.substring(k), tmp + s.substring(0, k)
                    + ".", res, n + 1);
        }
    }

    @Test
    public void test30() {
        String test1 = "abc";
        String test2 = "ab" + "c";
        System.out.println(test1 == test2);
        System.out.println(test1.equals(test2));

        String test4 = "ab";
        String test3 = test4 + "c";

        System.out.println(test1 == test3);
        System.out.println(test1.equals(test3));
    }

    @Test
    public void test31() {
        String SEPARATOR = "_";
        String WHOLE_NATION_PREFIX = Joiner.on(SEPARATOR).join(Collections.singleton("nation"));
        String WHOLE_NATION = Joiner.on(SEPARATOR).join(WHOLE_NATION_PREFIX, "%s");
        System.out.println(WHOLE_NATION_PREFIX);
        System.out.println(WHOLE_NATION);
    }

    @Test
    public void test32() {
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void test33() {
        int num = 32;
        int length = String.valueOf(num).length();
        String shard = String.format("%0" + length + "d", 32);
        String shard1 = String.format("%0" + length + "d", 2);
        System.out.println(shard);
        System.out.println(shard1);
    }
}

class MyCom implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.length() > o2.length() ? 1 : -1;
    }
}
