package com.design.handler;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public class LocationRuleHandler extends RuleHandler {

    public void apply(Context context) {
        if (context.getLocation()) {
            System.out.println("LocationRuleHandler");
            if (this.getSuccessor() != null) {
                this.getSuccessor().apply(context);
            }
        } else {
            throw new RuntimeException("非常抱歉，您所在的地区无法参与本次活动");
        }
    }

}
