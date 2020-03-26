//package com.hystrix.study;
//
//import com.google.common.collect.Maps;
//import com.netflix.config.ConfigurationManager;
//import com.netflix.config.DynamicPropertyFactory;
//import com.netflix.config.DynamicWatchedConfiguration;
//import com.netflix.config.WatchedConfigurationSource;
//import com.netflix.config.WatchedUpdateListener;
//import com.netflix.config.WatchedUpdateResult;
//import com.netflix.hystrix.strategy.HystrixPlugins;
//import com.netflix.hystrix.strategy.properties.HystrixDynamicProperties;
//import com.netflix.hystrix.strategy.properties.HystrixDynamicProperty;
//import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//
///**
// * Hystrix 动态配置
// * 1、是结合配置中心 qconfig 来处理的。比如，可以动态 线程的数量等等。
// */
//
//public class HystrixDynamicConfig implements WatchedConfigurationSource, InitializingBean {
//    private static final Logger logger = LoggerFactory.getLogger(HystrixDynamicConfig.class);
//
//    private static String HystrixDynamicConfigName = "hystrix_dynamic_config.properties";
//
//    private static List<WatchedUpdateListener> listeners = new CopyOnWriteArrayList<>();
//    private static Map<String, String> defaultConfig = Maps.newConcurrentMap();
//
//    /**
//     * 目前不做配置的删除
//     */
//    static {
//        try {
//
//            MapConfig config = MapConfig.get(HystrixDynamicConfigName);
//            defaultConfig.putAll(config.asMap());
//
//            // PropertiesChangeListener只有在properties文件内容有变化时才触发。如果文件里面的property为空，那么添加listener的操作并不会触发调用。
//            config.addPropertiesListener(new MapConfig.PropertiesChangeListener() {
//                @Override
//                public void onChange(PropertiesChange change) {
//                    fireEvent(change);
//                }
//            });
//
//        } catch (Throwable ex) {
//            logger.error("QConfig startup error All config use default ", ex);
//        }
//    }
//
//    private static void fireEvent(PropertiesChange change) {
//        WatchedUpdateResult result = getWatchedUpdateResult(change);
//        for (WatchedUpdateListener watchedUpdateListener : listeners) {
//            watchedUpdateListener.updateConfiguration(result);
//        }
//        logger.info("Rest before hystrix.exampleGroupKey.HystrixThreadPoolKey.coreSize value:{}", getHystrixCurrentData(" hystrix.exampleGroupKey.HystrixThreadPoolKey.coreSize"));
//
//        HystrixPropertiesFactory.reset();
//        logger.info("Rest after hystrix.exampleGroupKey.HystrixThreadPoolKey.coreSize value:{}", getHystrixCurrentData(" hystrix.exampleGroupKey.HystrixThreadPoolKey.coreSize"));
//    }
//
//    private static WatchedUpdateResult getWatchedUpdateResult(PropertiesChange change) {
//        Map<String, Object> added = new HashMap<>();
//        Map<String, Object> changed = new HashMap<>();
//        Map<String, Object> deleted = new HashMap<>();
//
//        Map<String, PropertyItem> changeItems = change.getItems();
//        for (String key : changeItems.keySet()) {
//            PropertyItem propertyItem = changeItems.get(key);
//            switch (propertyItem.getType()) {
//                case Add:
//                    added.put(key, propertyItem.getNewValue());
//                    logger.info("Add Event key:{} ,oldValue:{} ,currentValue:{}", propertyItem.getKey(), propertyItem.getOldValue(), propertyItem.getNewValue());
//                case Delete:
//                    deleted.put(key, propertyItem.getNewValue());
//                    logger.info("Delete Event key:{} ,oldValue:{} ,currentValue:{}", propertyItem.getKey(), propertyItem.getOldValue(), propertyItem.getNewValue());
//                case Modify:
//                    changed.put(key, propertyItem.getNewValue());
//                    logger.info("Modify Event key:{} ,oldValue:{} ,currentValue:{}", propertyItem.getKey(), propertyItem.getOldValue(), propertyItem.getNewValue());
//
//            }
//        }
//        return WatchedUpdateResult.createIncremental(added, changed, deleted);
//    }
//
//
//    @Override
//    public void addUpdateListener(WatchedUpdateListener watchedUpdateListener) {
//        if (watchedUpdateListener != null) {
//            listeners.add(watchedUpdateListener);
//        }
//    }
//
//    @Override
//    public void removeUpdateListener(WatchedUpdateListener watchedUpdateListener) {
//        listeners.remove(watchedUpdateListener);
//    }
//
//    @Override
//    public Map<String, Object> getCurrentData() throws Exception {
//        Map<String, Object> configMap = new HashMap<>();
//
//        Map<String, String> config = MapConfig.get(HystrixDynamicConfigName).asMap();
//
//        for (String key : config.keySet()) {
//            configMap.put(key, config.get(key));
//        }
//
//        return configMap;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // 安装动态配置
//        installDynamicConfiguration();
//    }
//
//
//    /**
//     *
//     */
//    private void installDynamicConfiguration() {
//        System.setProperty(DynamicPropertyFactory.ENABLE_JMX, "true");
//        ConfigurationManager.install(new DynamicWatchedConfiguration(this));
//    }
//
//    /**
//     * 获取动态配置
//     *
//     * @param propName
//     * @return
//     */
//    private static HystrixDynamicProperty<String> getHystrixCurrentData(String propName) {
//        HystrixDynamicProperties properties = HystrixPlugins.getInstance().getDynamicProperties();
//        HystrixDynamicProperty<String> p = HystrixDynamicProperties.Util.getProperty(properties, propName, "ERROR", String.class);
//        return p;
//
//    }
//
//}
