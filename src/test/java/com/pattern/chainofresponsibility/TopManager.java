package com.pattern.chainofresponsibility;

import java.util.Random;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-16
 * @Description cto
 */
public class TopManager extends Handler {

    public TopManager(String name) {
        super(name);
    }

    @Override
    public boolean process(LeaveRequest leaveRequest) {
        /**
         * 随机数大于3则为批准，否则不批准
         */
        boolean result = (new Random().nextInt(10)) > 3;
        String log = "总经理<%s> 审批 <%s> 的请假申请，请假天数： <%d> ，审批结果：<%s> ";
        System.out.println(String.format(log, this.name, leaveRequest.getName(), leaveRequest.getNumOfDays(), result == true ? "批准" : "不批准"));

        /**
         * 总经理不批准
         */
        if (result == false) {
            return false;
        }
        /**
         * 总经理最后批准
         */
        return true;
    }

}
