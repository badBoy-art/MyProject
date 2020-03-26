package com.json.study;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

/**
 * @author xuedui.zhao
 * @create 2019-07-11
 */
public class JsonCompareStudy {

    @Test
    public void compare() {
        try {

            String str1 = "{\"age\":12,\"name\":\"小黑\"}";
            String str2 = "{\"name\":\"小黑\",\"age\":12,\"addresss\":\"小黑\"}";
            JSONCompareResult
                    result = JSONCompare.compareJSON(str1, str2, JSONCompareMode.NON_EXTENSIBLE);
            System.out.println(result);
        } catch (
                JSONException e)

        {
            e.printStackTrace();
        }
    }

}
