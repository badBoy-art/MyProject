package com.regular.study;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuedui.zhao
 * @create 2018-07-16
 */
public class Email {

  private static final Pattern emailPattern =
      Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");

  @Test
  public void test01() {
    String emailAddress = "Lsj1156＠126.Com";
    Matcher emailMatcher = emailPattern.matcher(emailAddress);
    if (!emailMatcher.find()) {

      System.out.println("email wrong");
    }
  }
}
