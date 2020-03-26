package com.guava.study;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;

/**
 * 监听多个事件
 *
 * @author xuedui.zhao
 * @create 2019-07-25
 */
public class EventBusStudy2 {

    public class ParentEvent {
        protected String name;
        protected boolean isok;

        public ParentEvent(String name, boolean isok) {
            this.name = name;
            this.isok = isok;
        }

        public boolean getStatus() {
            return isok;
        }

        public String getName() {
            return name;
        }

        public String parentName() {
            return name;
        }
    }

    public class StudentEvent extends ParentEvent {
        public StudentEvent(String name, boolean isok) {
            super(name, isok);
        }

        @Override
        public String getName() {
            return super.getName() + "的儿子";
        }
    }

    public class TeacherListener {
        Set<String> rs;

        public TeacherListener() {
            rs = new HashSet<String>();
        }

        @Subscribe
        public void listenStudent(StudentEvent event) {
            String say = event.getStatus() ? event.getName() + ", 完成了作业.." : event.getName() + " ,没完成作业";
            rs.add(say);
        }

        @Subscribe
        public void listenParent(StudentEvent event) {
            String say = event.getStatus() ? event.parentName() + ", 你儿子没完成作业.." : event.parentName() + " ,你儿子没完成作业";
            rs.add(say);
        }

        public Set<String> rs() {
            return rs;
        }
    }

    public class TeacherListenerParent {
        Set<String> rs;

        public TeacherListenerParent() {
            rs = new HashSet<String>();
        }

        @Subscribe
        public void listenParent(StudentEvent event) {
            String say = event.getStatus() ? event.parentName() + ", 你儿子完成了作业" : event.parentName() + " ,你儿子没完成作业，来学校一趟";
            rs.add(say);
        }

        public Set<String> rs() {
            return rs;
        }
    }

    public static void main(String[] args) {
        EventBusStudy2 ec = new EventBusStudy2();
        TeacherListener listener = ec.new TeacherListener();
//		TeacherListenerParent listenerParent = ec.new TeacherListenerParent();
        EventBus eventBus = new EventBus("test");
        eventBus.register(listener);
//		eventBus.register(listenerParent);
        StudentEvent nickEvent = ec.new StudentEvent("nick", true);
        StudentEvent devidEvent = ec.new StudentEvent("devid", false);
        StudentEvent kenEvent = ec.new StudentEvent("ken", true);
        eventBus.post(nickEvent);
        eventBus.post(devidEvent);
        eventBus.post(kenEvent);
        for (String say : listener.rs()) {
            System.out.println(say);
        }
//		System.out.println("send parent.......");
//		for (String say : listenerParent.rs()) {
//			System.out.println(say);
//		}
    }

}
