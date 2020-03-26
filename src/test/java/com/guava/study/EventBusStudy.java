package com.guava.study;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;

/**
 * 订阅事件和事件通知
 *
 * @author xuedui.zhao
 * @create 2019-07-25
 */
public class EventBusStudy {

    public class StudentEvent {
        String name;
        boolean isok;

        public StudentEvent(String name, boolean isok) {
            this.name = name;
            this.isok = isok;
        }

        public boolean getStatus() {
            return isok;
        }

        public String getName() {
            return name;
        }
    }

    public class TeacherListener {
        Set<String> rs;

        public TeacherListener() {
            rs = new HashSet<String>();
        }

        @Subscribe
        public void listen(StudentEvent event) {
            String say = event.getStatus() ? event.getName() + ", 完成了作业.." : event.getName() + "没完成作业";
            rs.add(say);
        }

        public Set<String> rs() {
            return rs;
        }
    }

    public static void main(String[] args) {
        EventBusStudy ec = new EventBusStudy();
        TeacherListener listener = ec.new TeacherListener();//观察者
        EventBus eventBus = new EventBus();
        //注册监听器
        eventBus.register(listener);
        StudentEvent nickEvent = ec.new StudentEvent("nick", true);//受查者
        StudentEvent devidEvent = ec.new StudentEvent("devid", false);//受查者
        StudentEvent kenEvent = ec.new StudentEvent("ken", true);//受查者
        //提交监听事件,并触发监听方法
        eventBus.post(nickEvent);
        eventBus.post(devidEvent);
        eventBus.post(kenEvent);
        //遍历返回信息
        for (String say : listener.rs()) {
            System.out.println(say);
        }
    }

}
