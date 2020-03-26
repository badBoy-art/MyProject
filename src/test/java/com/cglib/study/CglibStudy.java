package com.cglib.study;

import org.junit.Test;
import org.mockito.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuedui.zhao
 * @create 2019-06-14
 */
public class CglibStudy {

    @Test
    public void testBeanCopier() {
        Map hobbys = new HashMap<String, String>();
        hobbys.put("eat", "apple");
        hobbys.put("travel", "lavida");
        User user = User.builder().address("西小口").name("灵光哥").hobbys(hobbys).build();
        User1 user1 = new User1();
        final BeanCopier copier = BeanCopier.create(User.class, User1.class, false);
        copier.copy(user, user1, null);
        System.out.println(user1);
    }
}
