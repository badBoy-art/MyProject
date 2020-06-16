package com.pattern.chainofresponsibility;

import lombok.Data;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-06-16
 * @Description
 */
@Data
public abstract class Handler {
    /**
     * 处理者姓名
     */
    protected String name;
    /**
     * 下一个处理者
     */
    protected Handler nextHandler;

    public Handler(String name) {
        this.name = name;
    }

    /**
     * 处理请假
     *
     * @param leaveRequest
     * @return
     */
    public abstract boolean process(LeaveRequest leaveRequest);

}
