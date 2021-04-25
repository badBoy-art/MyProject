package com.pattern.chainofresponsibility;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-16
 * @Description
 */
@Data
@AllArgsConstructor
public class LeaveRequest {
    /**
     * 请假人姓名
     */
    private String name;
    /**
     * 请假天数
     */
    private int numOfDays;

}
