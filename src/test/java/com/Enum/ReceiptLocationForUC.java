package com.Enum;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xuedui.zhao
 * @create 2018-09-27
 */
public enum ReceiptLocationForUC {
    //逐步去掉 替换为 3 和4 两种
    person(2, "个人"),
    company(3, "企业"),
    government(4, "政府机关单位");
    
    public final int value;
    public String desc;
    
    ReceiptLocationForUC(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    
    public static ReceiptLocationForUC fromValue(int value) {
        for (ReceiptLocationForUC s : ReceiptLocationForUC.values()) {
            if (value == s.value) {
                return s;
            }
        }
        return null;
    }
    
    public static ReceiptLocationForUC fromName(String  name) {
        for (ReceiptLocationForUC s : ReceiptLocationForUC.values()) {
            if (StringUtils.equals(name,s.name())) {
                return s;
            }
        }
        return null;
    }
    
    public int getValue() {
        return value;
    }
    
    public String getDesc() {
        return desc;
    }
}
