package com.design.handler;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class LimitRuleHandler extends RuleHandler {

    public void apply(Context context) {
        if (context.remainedTimes() > 0) {
            System.out.println("LimitRuleHandler");
            if (this.getSuccessor() != null) {
                this.getSuccessor().apply(context);
            }
        } else {
            throw new RuntimeException("您来得太晚了，奖品被领完了");
        }
    }
}
