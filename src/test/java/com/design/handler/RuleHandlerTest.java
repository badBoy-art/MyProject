package com.design.handler;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description 责任链模式
 */
public class RuleHandlerTest {

    public static void main(String[] args) {
        Context context = new Context();
        RuleHandler newUserHandler = new NewUserRuleHandler();
        RuleHandler locationHandler = new LocationRuleHandler();
        RuleHandler limitHandler = new LimitRuleHandler();

        // 假设本次活动仅校验地区和奖品数量，不校验新老用户
        locationHandler.setSuccessor(limitHandler);

        locationHandler.apply(context);
    }

}
