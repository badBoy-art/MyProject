package com.apachecommon.study;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author xuedui.zhao
 * @create 2019-08-23
 */
public class LoadStudy {

    @Test
    public void test() throws Exception{
        Configurations configs = new Configurations();
        // setDefaultEncoding是个静态方法,用于设置指定类型(class)所有对象的编码方式。
        // 本例中是PropertiesConfiguration,要在PropertiesConfiguration实例创建之前调用。
        FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "UTF-8");
        PropertiesConfiguration propConfig = configs.properties(this.getClass().getClassLoader().getResource("spring.properties"));
        System.out.println(propConfig.getString("jdbc.username"));
    }

    /**
     * https://commons.apache.org/proper/commons-configuration/userguide/howto_reloading.html
     */
    @Test
    public void testReloading() {
        Parameters params = new Parameters();
        // Read data from this file
        File propertiesFile = new File("spring.properties");

        ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.fileBased()
                                .setFile(propertiesFile));
        PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),
                null, 1, TimeUnit.MINUTES);
        trigger.start();
    }
}
