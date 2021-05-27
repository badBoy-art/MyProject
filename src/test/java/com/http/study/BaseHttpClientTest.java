package com.http.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * request.getRequestURL() 返回全路径
 * request.getRequestURI() 返回除去host（域名或者ip）部分的路径
 * request.getContextPath() 返回工程名部分，如果工程映射为/，此处返回则为空
 * request.getServletPath() 返回除去host和工程名部分的路径
 * <p>
 * request.getRequestURL() http://localhost:8080/jqueryLearn/resources/request.jsp
 * request.getRequestURI() /jqueryLearn/resources/request.jsp
 * request.getContextPath() /jqueryLearn
 * request.getServletPath() /resources/request.jsp
 * <p>
 * <p>
 * 注： resources为WebContext下的目录名
 *     jqueryLearn 为工程名
 *
 * @author xuedui.zhao
 * @create 2019-08-08
 */
public class BaseHttpClientTest {
    protected static final int REQUEST_COUNT = 5;

    protected static final String SEPERATOR = "   ";

    protected static final AtomicInteger NOW_COUNT = new AtomicInteger(0);

    protected static final StringBuilder EVERY_REQ_COST = new StringBuilder(200);

    /**
     * 获取待运行的线程
     */
    protected List<Thread> getRunThreads(Runnable runnable) {
        List<Thread> tList = new ArrayList<Thread>(REQUEST_COUNT);

        for (int i = 0; i < REQUEST_COUNT; i++) {
            tList.add(new Thread(runnable));
        }

        return tList;
    }

    /**
     * 启动所有线程
     */
    protected void startUpAllThreads(List<Thread> tList) {
        for (Thread t : tList) {
            t.start();
            // 这里需要加一点延迟，保证请求按顺序发出去
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected synchronized void addCost(long cost) {
        EVERY_REQ_COST.append(cost);
        EVERY_REQ_COST.append("ms");
        EVERY_REQ_COST.append(SEPERATOR);
    }
}
