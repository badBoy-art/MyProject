package com.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuedui.zhao
 * @create 2018-09-25
 */
public class CheckEmailUtil {

    private static Logger logger = LoggerFactory.getLogger(CheckEmailUtil.class);

    private static String check = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

    @Test
    public void checkEmail() {
        String email = "375975033@qq._com";
        //1983213006@qq．com   782057257 @qq.com   375975033@qq._com  yl@hsjk_bj.com  13804253789@163.co m
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            logger.error("验证邮箱地址错误", e);
            flag = false;
        }

        Assert.assertTrue(flag);
    }
}
