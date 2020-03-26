package com.guice.study;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * @author xuedui.zhao
 * @create 2019-08-19
 */
public class MyAppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Application.class).to(MyApp.class);
        bind(LogService.class).to(LogServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(LogService.class)
                .annotatedWith(Names.named("myLog"))
                .to(MyLogServiceImpl.class);
        bind(LogService.class)
                .annotatedWith(AnnoLog.class)
                .to(AnnotateLogServiceImpl.class);
        bind(Integer.class)
                .annotatedWith(Names.named("login timeout seconds"))
                .toInstance(10);
    }
}
