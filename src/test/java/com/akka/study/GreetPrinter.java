package com.akka.study;

import akka.actor.UntypedActor;

/**
 * 打印招呼
 *
 * @author xuedui.zhao
 * @create 2019-06-16
 */
public class GreetPrinter extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Greeting)
            System.out.println(((Greeting) message).message);
    }
}
