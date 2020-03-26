package com.Enum;

import com.google.common.base.Strings;
import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2018-09-27
 */
public class TestEnum {
    
    @Test
    public void Test01() {
      System.out.println(ReceiptLocationEnum.personal.name());
    }
    
    @Test
    public void Test02() {
        String receiptTitle = "government";
        if (Strings.isNullOrEmpty(receiptTitle)) {
           System.out.println(ReceiptLocationForUC.company.value);
        }
    
        if (receiptTitle.equals(ReceiptLocationForUC.company.name())) {
            System.out.println(ReceiptLocationForUC.company.value);
        } else if (receiptTitle.equals(ReceiptLocationForUC.person.name())) {
            System.out.println(ReceiptLocationForUC.person.value);
        } else if (receiptTitle.equals(ReceiptLocationForUC.government.name())) {
            System.out.println(ReceiptLocationForUC.government.value);
        }
    
        //System.out.println(ReceiptLocationForUC.company.value);
    }

    @Test
    public void test03() {

        System.out.println(EnumToJsonUtil.toJSONString(ReceiptLocationEnum.personal));
    }
}
