package com.base;

import java.util.List;

import lombok.Data;

/**
 * Created on 2021-01-15
 *
 * @Description
 */
@Data
public class Address {
    private String code;
    private String name;
    private List<Address> children;
}
