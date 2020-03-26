package com.Nacos.study;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import org.junit.Test;

import java.util.Properties;

/**
 * @author xuedui.zhao
 * @create 2018-11-09
 */
public class NacosStudy {

    @Test
    public void test01() throws Exception {

        String serverAddr = "127.0.0.1:8848";
        String dataId = "nacos.cfg.dataId";
        String group = "test";
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
    }
}
