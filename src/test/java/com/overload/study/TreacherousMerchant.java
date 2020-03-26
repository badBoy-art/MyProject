package com.overload.study;

import java.util.Random;

/**
 * @author xuedui.zhao
 * @create 2018-12-03
 */
public class TreacherousMerchant extends Merchant{
  // javap -v TreacherousMerchant.class ...
  @Override
  public double discountPrice(double originalPrice, Customer customer) {
        if (customer.isVIP()) {                         // invokeinterface
            return originalPrice * discrimination();                    // invokestatic
        } else {
            return super.discountPrice(originalPrice, customer); // invokespecial
        }
    }

    public static double discrimination() {
        // 咱们的杀熟算法太粗暴了，应该将客户城市作为随机数生成器的种子。
        return new Random()                          // invokespecial
                .nextDouble()                         // invokevirtual
                + 0.8d;
    }
}
