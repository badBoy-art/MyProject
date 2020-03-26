package com.guava.study;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Map;

/**
 * @author xuedui.zhao
 * @create 2019-07-27
 */
public class TypeTokenStudy {

    static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
        return new TypeToken<Map<K, V>>() {
        }
                .where(new TypeParameter<K>() {
                }, keyToken)
                .where(new TypeParameter<V>() {
                }, valueToken);
    }

    @Test
    public void testTypeToken() {
        TypeToken<Map<String, BigInteger>> mapToken = mapToken(
                TypeToken.of(String.class),
                TypeToken.of(BigInteger.class)
        );
        System.out.println(mapToken.getRawType());
    }
}
