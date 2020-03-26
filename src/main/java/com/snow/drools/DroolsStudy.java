package com.snow.drools;

import com.google.common.collect.Lists;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.List;

/**
 * @author xuedui.zhao
 * @create 2018-12-17
 */
public class DroolsStudy {

    public static void main(final String[] args) throws Exception{
        KieServices kieServices = KieServices.Factory.get();
        //默认自动加载 META-INF/kmodule.xml
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        execute(kieContainer);
    }

    public static void execute( KieContainer kc ) throws Exception{
        //kmodule.xml 中定义的 ksession name
        KieSession ksession = kc.newKieSession("point-rulesKS");
        List<Order> orderList = getInitData();
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            ksession.insert(order);
            int count = ksession.fireAllRules();
            System.out.println(count);
            addScore(order);
        }
        ksession.dispose();
    }

    private static void addScore(Order order) {
    System.out.println(order.getScore());
    }

    private static List<Order> getInitData() {
        List<Order> orders = Lists.newArrayListWithCapacity(3);
        Order order = new Order();
        order.setAmout(100);
        orders.add(order);
        Order order1 = new Order();
        order1.setAmout(500);
        orders.add(order1);
        Order order2 = new Order();
        order2.setAmout(1000);
        orders.add(order2);

        return orders;
    }


}
