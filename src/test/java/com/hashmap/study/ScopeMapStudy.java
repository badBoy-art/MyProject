package com.hashmap.study;

import com.Extends.study.WhiteDog;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * 得到key和entry
 *
 * @author xuedui.zhao
 * @create 2019-08-09
 */
public class ScopeMapStudy {

    @Test
    public void test01() {
        List<WhiteDog> dogs = Lists.newArrayList();
        for (int i = 1; i < 3; i++) {
            WhiteDog whiteDog = new WhiteDog();
            whiteDog.setName("name" + i);
            whiteDog.setAge(i);
            dogs.add(whiteDog);
        }

        Map<String, WhiteDog> hashMap = new HashMap<>();
        for (WhiteDog dog: dogs) {
            hashMap.put(dog.getName(),dog);
        }

        System.out.println(hashMap);

        Map<String, WhiteDog> map = new ScopeMap<WhiteDog>() {
            @Override
            protected Enumeration<String> getAttributeNames() {

                Vector<String> v = new Vector<String>();

                for (WhiteDog dog : dogs) {
                    v.add(dog.getName());
                }
                return v.elements();
            }

            @Override
            protected WhiteDog getAttribute(String name) {

                for (WhiteDog dog : dogs) {
                    if (name.equals(dog.getName())) {
                        return dog;
                    }
                }
                return null;
            }

        };

        System.out.println(map);
    }

    private abstract static class ScopeMap<V> extends AbstractMap<String, V> {

        protected abstract Enumeration<String> getAttributeNames();

        protected abstract V getAttribute(String name);

        @SuppressWarnings("unused")
        protected void removeAttribute(String name) {
            throw new UnsupportedOperationException();
        }

        @SuppressWarnings("unused")
        protected void setAttribute(String name, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public final Set<Entry<String, V>> entrySet() {
            Enumeration<String> e = getAttributeNames();
            Set<Map.Entry<String, V>> set = new HashSet<Entry<String, V>>();
            if (e != null) {
                while (e.hasMoreElements()) {
                    set.add(new ScopeEntry(e.nextElement()));
                }
            }
            return set;
        }

        @Override
        public final int size() {
            int size = 0;
            Enumeration<String> e = getAttributeNames();
            if (e != null) {
                while (e.hasMoreElements()) {
                    e.nextElement();
                    size++;
                }
            }
            return size;
        }

        @Override
        public final boolean containsKey(Object key) {
            if (key == null) {
                return false;
            }
            Enumeration<String> e = getAttributeNames();
            if (e != null) {
                while (e.hasMoreElements()) {
                    if (key.equals(e.nextElement())) {
                        return true;
                    }
                }
            }
            return false;
        }

        private class ScopeEntry implements Map.Entry<String, V> {

            private final String key;

            public ScopeEntry(String key) {
                this.key = key;
            }

            @Override
            public String getKey() {
                return this.key;
            }

            @Override
            public V getValue() {
                return getAttribute(this.key);
            }

            @Override
            public V setValue(Object value) {
                if (value == null) {
                    removeAttribute(this.key);
                } else {
                    setAttribute(this.key, value);
                }
                return null;
            }

            @Override
            public boolean equals(Object obj) {
                return (obj != null && this.hashCode() == obj.hashCode());
            }

            @Override
            public int hashCode() {
                return this.key.hashCode();
            }

        }

        @Override
        public final V get(Object key) {
            if (key != null) {
                return getAttribute((String) key);
            }
            return null;
        }

        @Override
        public final V put(String key, V value) {
            if (key == null) {
                throw new NullPointerException();
            }
            if (value == null) {
                this.removeAttribute(key);
            } else {
                this.setAttribute(key, value);
            }
            return null;
        }

        @Override
        public final V remove(Object key) {
            if (key == null) {
                throw new NullPointerException();
            }
            this.removeAttribute((String) key);
            return null;
        }

    }

}
