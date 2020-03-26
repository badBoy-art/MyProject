package com.micrometer.study;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleConfig;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2019-07-26
 */
public class CompositeMeterRegistryStudy {

    @Test
    public void test() {
        CompositeMeterRegistry registry = new CompositeMeterRegistry();
        registry.add(new SimpleMeterRegistry());
        registry.add(new SimpleMeterRegistry(new MyConfig(), Clock.SYSTEM));

        Counter counter = registry.counter("simple");
        counter.increment();
    }

    private static class MyConfig implements SimpleConfig {

        public String get(final String key) {
            return null;
        }

        public String prefix() {
            return "my";
        }
    }
}
