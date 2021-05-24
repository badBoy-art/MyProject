package com.design.state;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-24
 * @Description 补库操作
 */
public class RevertState implements State {

    public void doAction(Context context) {
        System.out.println("给此商品补库存");
        context.setState(this);

        //... 执行加库存的具体操作
    }

    public String toString() {
        return "Revert State";
    }
}
