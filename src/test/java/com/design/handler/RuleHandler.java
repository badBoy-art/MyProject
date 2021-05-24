package com.design.handler;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description
 */
public abstract class RuleHandler {

    // 后继节点
    protected RuleHandler successor;

    public abstract void apply(Context context);

    public void setSuccessor(RuleHandler successor) {
        this.successor = successor;
    }

    public RuleHandler getSuccessor() {
        return successor;
    }

}
