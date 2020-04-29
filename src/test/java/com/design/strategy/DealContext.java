package com.design.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-04-27
 * @Description
 */
@Data
@AllArgsConstructor
public class DealContext {
    private String type;
    private DealStrategy deal;

    public boolean options(String type) {
        return this.type.equals(type);
    }
}
