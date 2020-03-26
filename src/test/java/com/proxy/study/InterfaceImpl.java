package com.proxy.study;

/**
 * @author xuedui.zhao
 * @create 2018-11-24
 */
public class InterfaceImpl implements Interface {

    @Override
    public void gotoSchool() {
        System.out.println("gotoSchool");
    }
    @Override
    public void gotoWork() {
        System.out.println("gotoWork");
    }
    @Override
    public void oneDay() {
        gotoSchool();
        gotoWork();
    }
    @Override
    public final void oneDayFinal() {
        gotoSchool();
        gotoWork();
    }
}
