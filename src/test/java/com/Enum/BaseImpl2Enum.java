package com.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-03-31
 * @Description
 */
@AllArgsConstructor
@Getter
public enum BaseImpl2Enum implements BaseEnum {
    THREE_DAY_LIVE(2500, 500, 0),
    DAY_FIRST(3000, 500, 500),
    DAY_SECOND(1500, 300, 0),
    DAY_THIRD(1000, 200, 0),
    WEEK_FIRST(6000, 1000, 1000),
    WEEK_SECOND(5000, 800, 1000),
    WEEK_THIRD(3500, 500, 1000),
    NONE(0, 0, 0);

    /**
     * 战队奖励
     */
    private long money;
    /**
     * 人均奖励
     */
    private long memberMoney;
    /**
     * 队长奖励
     */
    private long captainMoney;

    @Override
    public String get() {
        return "BaseImpl2Enum";
    }

}
