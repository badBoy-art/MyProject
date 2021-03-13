package com.slf4jmdc.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-31
 * @Description <a href="https://www.jianshu.com/p/1dea7479eb07"></a>
 */
public class MdcStudy {

    public static void main(String[] args) {
        MDC.put("first", "bad");
        Logger logger = LoggerFactory.getLogger(MdcStudy.class);

        MDC.put("last", "boy");
        logger.info("check close");
        logger.debug("i am debug");

        MDC.put("first", "BAD");
        MDC.put("last", "BOY");

        logger.info("check changed");
        logger.info("i am changed");
    }

}
