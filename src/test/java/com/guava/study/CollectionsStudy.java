package com.guava.study;

import com.Extends.study.WhiteDog;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.Collection;

/**
 * @author xuedui.zhao
 * @create 2019-07-27
 */
public class CollectionsStudy {

    @Test
    public void teststuScoreMultimap() {
        Multimap<String, WhiteDog> scoreMultimap = ArrayListMultimap.create();
        for (int i = 10; i < 20; i++) {
            WhiteDog whiteDog = new WhiteDog();
            whiteDog.setName("name" + i);
            whiteDog.setAge(i);
            scoreMultimap.put("dog", whiteDog);
        }
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.keys());

        Collection<WhiteDog> studentScore = scoreMultimap.get("dog");
        System.out.println(studentScore);
        studentScore.clear();
        WhiteDog studentScoreNew = new WhiteDog();
        studentScoreNew.setName("1024");
        studentScoreNew.setAge(1024);
        studentScore.add(studentScoreNew);

        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.keys());
    }

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

    @Test
    public void testBiMap() {
        BiMap<String, Integer> userId = HashBiMap.create();

        userId.put("str", 1);
        userId.put("str", 2);

        String userForId = userId.inverse().get(2);

        System.out.println(userForId);
        System.out.println(userId.get("str"));
    }
}
