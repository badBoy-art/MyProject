package com.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaoxuedui <zhaoxuedui>
 * Created on 2020-04-15
 * @Description
 */
@AllArgsConstructor
@Getter
public enum CodeEnum {
    KUAI_CURRENCY(1, "用户自己"),
    EXPOSURE_TICKET(2, "非同事"),
    RMB(3, "同事"),
    ;
    private int code;
    private String desc;

//    public static CodeEnum valueOf(int code) {
//        for (CodeEnum typе : CodeEnum.values()) {
//            if (typе.code == code) {
//                return typе;
//            }
//        }
//        return null;
//    }
}
