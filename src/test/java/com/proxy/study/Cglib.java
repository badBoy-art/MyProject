package com.proxy.study;

/**
 * @author xuedui.zhao
 * @create 2018-11-24
 */
public class Cglib {

  public void gotoHome() {
    System.out.println("============gotoHome============");
  }

  public void gotoSchool() {
    System.out.println("===========gotoSchool============");
  }

  public void oneday() {
    gotoHome();
    gotoSchool();
  }

    public final void onedayFinal() {
        gotoHome();
        gotoSchool();
    }

    public final void finalMethod() {
    System.out.println("finalMethod");
    }

    private void privateMethod() {
    System.out.println("privateMethod");
    }
}
