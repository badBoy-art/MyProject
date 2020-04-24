package com.apachecommon.cli;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-04-22
 * @Description
 */
public class CliTest {

    @Test
    public void test01() throws ParseException {
        String argss[] = {"-t  1000"};
        CliStudy.main(argss);
    }

    @Test
    public void test02() throws ParseException {
        String argss[] = {"-h"};
        CliStudy.main(argss);
    }
}
