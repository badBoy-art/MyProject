package com.algorithms.study;

import lombok.Data;

/**
 * Created on 2021-03-10
 *
 * @Description
 */
@Data
public class ListNode {
    Integer value;
    ListNode next;

    public ListNode(Integer value) {
        this.value = value;
    }
}
