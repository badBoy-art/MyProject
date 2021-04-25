package com.guava.study;

import java.util.HashSet;
import java.util.Set;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

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
            String say = event.getStatus() ? event.getName() + ", 完成了作业.." : event.getName() + ", 没完成作业";
            rs.add(say);
        }

        public Set<String> rs() {
            return rs;
        }
    }

    public class StudentListener {

        @Subscribe
        public void listen(String str) {
            System.out.println("StudentListener = " + str);
        }
    }

    public static void main(String[] args) {
        EventBusStudy ec = new EventBusStudy();
        TeacherListener listener1 = ec.new TeacherListener();//观察者1
        StudentListener listener2 = ec.new StudentListener();//观察者2
        EventBus eventBus = new EventBus();
        //注册监听器
        eventBus.register(listener1);
        eventBus.register(listener2);

        StudentEvent nickEvent = ec.new StudentEvent("nick", true);//受查者
        StudentEvent devidEvent = ec.new StudentEvent("devid", false);//受查者
        StudentEvent kenEvent = ec.new StudentEvent("ken", true);//受查者
        //提交监听事件,并触发监听方法
        eventBus.post(nickEvent);
        eventBus.post(devidEvent);
        eventBus.post(kenEvent);
        eventBus.post("小明写完了作业");
        //遍历返回信息
        for (String say : listener1.rs()) {
            System.out.println(say);
        }

    }

}
