package com.design.handler;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class NewUserRuleHandler extends RuleHandler {

    public void apply(Context context) {
        if (context.isNewUser()) {
            System.out.println("NewUserRuleHandler");
            // 如果有后继节点的话，传递下去
            if (this.getSuccessor() != null) {
                this.getSuccessor().apply(context);
            }
        } else {
            throw new RuntimeException("该活动仅限新用户参与");
        }
    }

}
