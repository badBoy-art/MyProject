package com.Enum;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-31
 * @Description
 */
@Getter
@AllArgsConstructor
public enum BaseTypeEnum {

    THREE_DAY_LIVE(1, "上周连续3天直播", Lists.newArrayList(BaseImpl1Enum.DAY_FIRST, BaseImpl2Enum.DAY_FIRST)),
    DAY_FIRST(2, "日冠军战队", Lists.newArrayList(BaseImpl1Enum.DAY_FIRST, BaseImpl2Enum.DAY_FIRST)),
    DAY_SECOND(3, "日亚军战队", Lists.newArrayList(BaseImpl1Enum.DAY_FIRST, BaseImpl2Enum.DAY_FIRST)),
    DAY_THIRD(4, "日季军战队", Lists.newArrayList(BaseImpl1Enum.DAY_FIRST, BaseImpl2Enum.DAY_FIRST)),
    WEEK_FIRST(5, "周冠军战队", Lists.newArrayList(BaseImpl1Enum.DAY_FIRST, BaseImpl2Enum.DAY_FIRST)),
    WEEK_SECOND(6, "周亚军战队", Lists.newArrayList(BaseImpl1Enum.DAY_FIRST, BaseImpl2Enum.DAY_FIRST)),
    WEEK_THIRD(7, "周季军战队", Lists.newArrayList(BaseImpl1Enum.DAY_FIRST, BaseImpl2Enum.DAY_FIRST)),
    NONE(0, "未获得奖励", Lists.newArrayList());

    private int code;
    private String desc;
    private List<BaseEnum> baseEnums;

}
