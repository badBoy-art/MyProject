package com.guice.study;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * google 注入容器
 *
 * @author xuedui.zhao
 * @create 2019-08-19
 * @see <a href= "https://www.jianshu.com/p/a648322dc680"/>
 */
public class GuiceStudy {

    private static Injector injector;

    @BeforeClass
    public static void init() {
        injector = Guice.createInjector(new MyAppModule());
    }

    @Test
    public void testMyApp() {
        Application myApp = injector.getInstance(Application.class);
        myApp.work();
    }

}
