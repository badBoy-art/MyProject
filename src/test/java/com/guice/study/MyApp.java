package com.guice.study;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author xuedui.zhao
 * @create 2019-08-19
 */
public class MyApp implements Application {

    private UserService userService;
    private LogService logService;
    private LogService myLogService;
    private LogService annoLogService;

    @Inject
    public MyApp(UserService userService, LogService logService, @Named("myLog") LogService myLogService,
                 @AnnoLog LogService annoLogService) {
        this.userService = userService;
        this.logService = logService;
        this.myLogService = myLogService;
        this.annoLogService = annoLogService;
    }

    @Override
    public void work() {
        userService.process();
        logService.log("程序正常运行");
        myLogService.log("MyLog程序正常运行");
        annoLogService.log("Anno程序正常运行");
    }

}
