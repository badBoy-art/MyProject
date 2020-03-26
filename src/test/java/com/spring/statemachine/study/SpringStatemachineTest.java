//package com.spring.statemachine.study;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.statemachine.StateMachine;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * @author xuedui.zhao
// * @create 2019-02-15
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/spring.xml"})
//public class SpringStatemachineTest {
//
//    @Autowired
//    private StateMachine<RegStatusEnum, RegEventEnum> stateMachine;
//
//    @Test
//    public void testStateMachine()
//    {
//        stateMachine.start();
//        stateMachine.sendEvent(RegEventEnum.CONNECT);
//        stateMachine.sendEvent(RegEventEnum.BEGIN_TO_LOGIN);
//        stateMachine.sendEvent(RegEventEnum.LOGIN_SUCCESS);
//        stateMachine.sendEvent(RegEventEnum.LOGOUT);
//    }
//
//}
