package com.commonspool2.study;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

/**
 * @author badBoy
 * @create 2019-11-13
 */
public class CommonsPool2Study {

    @Test
    public void test() throws Exception {
        GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
        conf.setMaxTotal(5);
        conf.setTestOnReturn(true);
        GenericObjectPool<BigObj> pool = new GenericObjectPool<BigObj>(new BigObjFactory(), conf);
        for (int i = 0; i < 10; i++) {
            BigObj bigObj = pool.borrowObject();
            System.out.println(i + " time get " + bigObj.getV());
            pool.returnObject(bigObj);
        }
    }

}
