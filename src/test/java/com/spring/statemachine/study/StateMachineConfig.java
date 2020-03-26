//package com.spring.statemachine.study;
//
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.statemachine.config.EnableStateMachine;
//import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
//import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
//import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
//
//import java.util.EnumSet;
//
//import static com.spring.statemachine.study.RegEventEnum.BEGIN_TO_LOGIN;
//import static com.spring.statemachine.study.RegEventEnum.CONNECT;
//import static com.spring.statemachine.study.RegEventEnum.LOGIN_FAILURE;
//import static com.spring.statemachine.study.RegEventEnum.LOGIN_SUCCESS;
//import static com.spring.statemachine.study.RegEventEnum.LOGOUT;
//import static com.spring.statemachine.study.RegStatusEnum.CONNECTED;
//import static com.spring.statemachine.study.RegStatusEnum.LOGINING;
//import static com.spring.statemachine.study.RegStatusEnum.LOGIN_INTO_SYSTEM;
//import static com.spring.statemachine.study.RegStatusEnum.UNCONNECTED;
//
///**
// * @author xuedui.zhao
// * @create 2019-02-15
// */
//
//
//@Configurable
//@EnableStateMachine
//public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<RegStatusEnum, RegEventEnum> {
//
//    /**
//     * 配置状态机状态
//     */
//    @Override
//    public void configure(StateMachineStateConfigurer<RegStatusEnum, RegEventEnum> states) throws Exception {
//        states.withStates()
//                // 初始化状态机状态
//                .initial(UNCONNECTED)
//                // 指定状态机的所有状态
//                .states(EnumSet.allOf(RegStatusEnum.class));
//
//    }
//
//    /**
//     * 配置状态机状态转换
//     */
//    @Override
//    public void configure(StateMachineTransitionConfigurer<RegStatusEnum, RegEventEnum> transitions) throws Exception {
//        // 1. connect UNCONNECTED -> CONNECTED
//        transitions.withExternal()
//                .source(UNCONNECTED)
//                .target(CONNECTED)
//                .event(CONNECT)
//                // 2. beginToLogin CONNECTED -> LOGINING
//                .and().withExternal()
//                .source(CONNECTED)
//                .target(LOGINING)
//                .event(BEGIN_TO_LOGIN)
//                // 3. login failure LOGINING -> UNCONNECTED
//                .and().withExternal()
//                .source(LOGINING)
//                .target(UNCONNECTED)
//                .event(LOGIN_FAILURE)
//                // 4. login success LOGINING -> LOGIN_INTO_SYSTEM
//                .and().withExternal()
//                .source(LOGINING)
//                .target(LOGIN_INTO_SYSTEM)
//                .event(LOGIN_SUCCESS)
//                // 5. logout LOGIN_INTO_SYSTEM -> UNCONNECTED
//                .and().withExternal()
//                .source(LOGIN_INTO_SYSTEM)
//                .target(UNCONNECTED)
//                .event(LOGOUT);
//    }
//}
