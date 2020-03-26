package com.Enum;

/**
 * @author xuedui.zhao
 * @create 2018-09-27
 */
public enum ReceiptLocationEnum {
    
    company(1, "单位"),
    personal(2, "个人"),
    enterprise(3, "企业"),
    government(4, "政府机关单位");
    
    public final int value;
    public String name;
    
    public static ReceiptLocationEnum fromValue(int value) {
        ReceiptLocationEnum[] arr$ = values();
        int len$ = arr$.length;
        
        for(int i$ = 0; i$ < len$; ++i$) {
            ReceiptLocationEnum s = arr$[i$];
            if (value == s.value) {
                return s;
            }
        }
        
        return null;
    }
    
    private ReceiptLocationEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public String getName() {
        return this.name;
    }
}
