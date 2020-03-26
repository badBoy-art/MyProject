package com.guava.study;

import com.Extends.study.WhiteDog;
import com.google.common.base.Optional;
import org.junit.Test;

/**
 * Optional学习
 *
 * @author xuedui.zhao
 * @create 2019-07-26
 */
public class OptionalStudy {

    @Test
    public void testOptional() {
        Optional<WhiteDog> optional = Optional.fromNullable(null);
        System.out.println(optional.isPresent());
        Optional<Integer> op = Optional.of(5);
        System.out.println(op.isPresent());
        System.out.println(op.get());
    }
}
