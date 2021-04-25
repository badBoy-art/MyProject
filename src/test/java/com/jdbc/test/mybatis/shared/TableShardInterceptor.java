//package com.jdbc.test.mybatis.shared;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.util.List;
//import java.util.Properties;
//import java.util.stream.Collectors;
//
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.binding.MapperMethod;
//import org.apache.ibatis.executor.statement.RoutingStatementHandler;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.reflection.DefaultReflectorFactory;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.ReflectorFactory;
//import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
//import org.apache.ibatis.reflection.factory.ObjectFactory;
//import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
//import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.util.StringUtils;
//
//import com.google.common.base.CharMatcher;
//import com.google.common.base.Splitter;
//import com.google.common.collect.Lists;
//
//import cn.hutool.core.util.ReflectUtil;
//
///**
// * @author zhaoxuedui <zhaoxuedui@''.com>
// * Created on 2020-09-29
// * @Description
// */
//@Intercepts({
//        @Signature(
//                type = StatementHandler.class,
//                method = "prepare",
//                args = {Connection.class, Integer.class}
//        )
//})
//public class TableShardInterceptor implements Interceptor {
//    /**
//     * sql语句里面去获取表名的依据（主要，全部是小写的）
//     * 说白了就是哪些字符串后面会跟上表名
//     */
//    private final static String[] SQL_TABLE_NAME_FLAG_PREFIX = {"from", "join", "update", "insert into"};
//
//    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
//    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
//    private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        if (!(invocation.getTarget() instanceof RoutingStatementHandler)) {
//            return invocation.proceed();
//        }
//
//        try {
//            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
//            // MetaObject是mybatis里面提供的一个工具类，类似反射的效果
//            MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY);
//            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");//获取sql语句
//            String originSql = boundSql.getSql();
//
//            if (StringUtils.isEmpty(originSql)) {
//                return invocation.proceed();
//            }
//
//            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
//            // 判断方法上是否添加了 TableShardAnnotation 注解，因为只有添加了TableShard注解的方法我们才会去做分表处理
//            TablePrepare tablePrepare = getTableShardAnnotation(mappedStatement);
//
//            // 没有加@TablePrepare注解则不填家我们自定义的逻辑
//            if (tablePrepare == null) {
//                return invocation.proceed();
//            }
//
//            boolean enableAutoCreateTable = tablePrepare.enableAutoCreateTable(); // 表不存在的是哈,事发创建
//            boolean enableTableShard = tablePrepare.enableTableShard(); // 事发进行分表逻辑处理
//            // 自动建表和分表是否开启，都没有则退出往下走
//            if (!enableAutoCreateTable && !enableTableShard) {
//                invocation.proceed();
//            }
//
//            // 获取到需要处理的表名
//            String[] appointTable = tablePrepare.appointTable();
//            if (appointTable.length == 0) {
//                List<String> tableNameList = getTableNamesFromSql(originSql);
//                if (tableNameList == null || tableNameList.isEmpty()) {
//                    return invocation.proceed();
//                } else {
//                    // 去掉前后空格和/n
//                    tableNameList = tableNameList.stream().map(item -> {
//                        if (item == null) {
//                            return null;
//                        }
//                        return item.trim().replaceAll("[\r\n]", "");
//                    }).collect(Collectors.toList());
//                    appointTable = new String[tableNameList.size()];
//                    tableNameList.toArray(appointTable);
//                }
//            }
//
//
//            // 获取分表表名处理策略
//            Class<? extends ITableNameStrategy> strategyClass = tablePrepare.strategy();
//            ITableNameStrategy tableStrategy = null;
//            if (!strategyClass.equals(TableNameStrategyVoid.class)) {
//                tableStrategy = strategyClass.newInstance();
//            }
//
//            // 分表处理的时候,我们一般是依赖参数里面的某个值来进行的.这里用于获取到参数对应的值.
//            String dependValue = getDependFieldValue(tablePrepare, metaStatementHandler, mappedStatement);
//
//            // 自动建表处理逻辑(表不存在的时候,我们会建表)
//            if (tablePrepare.enableAutoCreateTable()) {
//                SqlSessionTemplate template = SpringContextHolder.getBean(SqlSessionTemplate.class);
//                for (String tableName : appointTable) {
//                    TableCreateConfig classConfig = TableCreateManager.INSTANCE.getClassConfig(tableName);
//                    if (classConfig == null) {
//                        // 没有找到建表语句则跳过
//                        continue;
//                    }
//
//                    String createSqlMethodPath = classConfig.getAutoCreateTableMapperClass().getName() + "." + classConfig.getAutoCreateTableMapperMethodName();
//                    String sql = template.getConfiguration().getMappedStatement(createSqlMethodPath).getBoundSql("delegate.boundSql").getSql();
//                    if (StringUtils.isEmpty(sql)) {
//                        // 建表sql为空时不理，直接跳过
//                        continue;
//                    }
//
//                    if (!StringUtils.isEmpty(dependValue) && strategyClass != TableNameStrategyVoid.class) {
//                        sql = sql.replace(tableName, tableStrategy.tableName(tableName, dependValue));
//                    }
//
//                    Connection conn = (Connection) invocation.getArgs()[0];
//                    boolean preAutoCommitState = conn.getAutoCommit();
//                    conn.setAutoCommit(false);//将自动提交关闭
//                    try (PreparedStatement countStmt = conn.prepareStatement(sql)) {
//                        // 把新语句设置回去
//                        metaStatementHandler.setValue("delegate.boundSql.sql", sql);
//                        countStmt.execute();
//                        conn.commit();//执行完后，手动提交事务
////                        System.out.println(isSuccess);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        conn.setAutoCommit(preAutoCommitState);//在把自动提交打开
//                    }
//                }
//            }
//
//            // 分表处理逻辑
//            if (strategyClass != TableNameStrategyVoid.class) {
//                if (tablePrepare.enableTableShard()) {
//                    String updateSql = originSql;
//                    for (String tableName : appointTable) {
//                        // 策略处理表名
//                        String newTableName = tableStrategy.tableName(tableName, dependValue);
//                        updateSql = updateSql.replaceAll(tableName, newTableName);
//                    }
//
//                    // 把新语句设置回去，替换表名
//                    metaStatementHandler.setValue("delegate.boundSql.sql", updateSql);
//                }
//            } else {
//                // fix 启用了自动建表，但是没有启用分表的时候，sql被替换成建表的sql。没有设置回来的问题
//                metaStatementHandler.setValue("delegate.boundSql.sql", originSql);
//            }
//        } catch (Exception ignored) {
//            // ignore 任何一个地方有异常都去执行原始操作 -- invocation.proceed()
//        }
//        return invocation.proceed();
//    }
//
//    /**
//     * 从参数里面找到指定对象指定字段对应的值
//     */
//    private String getDependFieldValue(TablePrepare tablePrepare, MetaObject metaStatementHandler, MappedStatement mappedStatement) throws Exception {
//
//        // 以上情况下不满足则走@TableShardParam机制
//        String id = mappedStatement.getId();
//        String className = id.substring(0, id.lastIndexOf("."));
//        String methodName = id.substring(id.lastIndexOf(".") + 1);
//        Method[] methods = Class.forName(className).getMethods();
//        Method method = null;
//        for (Method me : methods) {
//            if (me.getName().equals(methodName) && me.isAnnotationPresent(tablePrepare.annotationType())) {
//                method = me;
//            }
//        }
//
//        if (method == null) {
//            return null;
//        }
//
//        Parameter[] parameters = method.getParameters();
//        if (parameters.length == 0) {
//            return null;
//        }
//
//        int flag = 0;
//        Parameter parameter = null;
//        for (Parameter p : parameters) {
//            // TableShardParam和Param需要同时添加
//            if (p.getAnnotation(TableShardParam.class) != null && p.getAnnotation(Param.class) != null) {
//                parameter = p;
//                flag++;
//
//            }
//        }
//
//        // 参数没有注解则退出
//        if (flag == 0) {
//            return null;
//        }
//
//        // 多个则抛异常
//        if (flag > 1) {
//            throw new RuntimeException("存在多个指定@TableShardParam的参数，无法处理");
//        }
//
//        String tableSharedFieldParamKey = parameter.getAnnotation(Param.class).value();
//        TableShardParam annotation = parameter.getAnnotation(TableShardParam.class);
//        Class<?> parameterType = parameter.getType(); // 参数的类型
//        String dependFieldName = StringUtils.isEmpty(annotation.value()) ? annotation.dependFieldName() : annotation.value();
//        if (isPrimitive(parameterType) || StringUtils.isEmpty(dependFieldName)) {
//            return getPrimitiveParamFieldValue(metaStatementHandler, tableSharedFieldParamKey);
//        } else {
//            return getParamObjectFiledValue(metaStatementHandler, tableSharedFieldParamKey, dependFieldName);
//        }
//    }
//
//    /**
//     * 判断是否是基础类型 9大基础类型及其包装类
//     *
//     * @return 是否是基础类型, long, int, Long 等等
//     */
//    private boolean isPrimitive(Class<?> clazz) {
//        if (clazz.isPrimitive()) {
//            return true;
//        }
//
//        try {
//            if (((Class) clazz.getField("TYPE").get(null)).isPrimitive()) {
//                return true;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//
//        return clazz.equals(String.class);
//    }
//
//    /**
//     * 解析sql获取到sql里面所有的表名
//     *
//     * @param sql sql
//     * @return 表名列表
//     */
//    private List<String> getTableNamesFromSql(String sql) {
//        // 对sql语句进行拆分 -- 以'，'、'\n'、'\t'作为分隔符
//        List<String> splitterList = Lists.newArrayList(Splitter.on(new CharMatcher() {
//            @Override
//            public boolean matches(char c) {
//                return Character.isWhitespace(c) || c == '\n' || c == '\t';
//            }
//        }).omitEmptyStrings().trimResults().split(sql))
//                .stream()
//                .filter(s -> !s.equals(","))
//                .filter(s -> !s.equals("?"))
//                .filter(s -> !s.equals("?,"))
//                .filter(s -> !s.equals("("))
//                .filter(s -> !s.equals(")"))
//                .filter(s -> !s.equals("="))
//                .collect(Collectors.toList());
//        List<String> tableNameList = Lists.newArrayList();
//        for (String item : SQL_TABLE_NAME_FLAG_PREFIX) {
//            tableNameList.addAll(getTableName(splitterList, Lists.newArrayList(Splitter.on(' ').split(item))));
//        }
//        return tableNameList;
//    }
//
//    /**
//     * 获取表名
//     */
//    private List<String> getTableName(List<String> splitterList, List<String> list) {
//        List<String> retList = Lists.newArrayList();
//        if (list == null || list.isEmpty() || splitterList == null || splitterList.isEmpty() || splitterList.size() <= list.size()) {
//            return retList;
//        }
//        for (int index = 0; index < splitterList.size(); index = index + list.size()) {
//
//            if (index < splitterList.size() - list.size()) {
//                boolean match = true;
//                for (int innerIndex = 0; innerIndex < list.size(); innerIndex++) {
//                    if (!splitterList.get(index + innerIndex).toLowerCase().equals(list.get(innerIndex).toLowerCase())) {
//                        match = false;
//                        break;
//                    }
//                }
//                if (match) {
//                    if ("update".toLowerCase().equals(list.get(0).toLowerCase())) {
//                        // ON DUPLICATE KEY UPDATE 需要过滤出来
//                        if (index < 3 || !(splitterList.get(index - 1).toLowerCase().equals("key".toLowerCase()) &&
//                                splitterList.get(index - 2).toLowerCase().equals("DUPLICATE".toLowerCase()) &&
//                                splitterList.get(index - 3).toLowerCase().equals("ON".toLowerCase()))) {
//                            retList.add(splitterList.get(index + list.size()));
//                        }
//                    } else {
//                        retList.add(splitterList.get(index + list.size()));
//                    }
//                }
//            }
//
//        }
//        return retList;
//    }
//
//
//    /**
//     * 获取方法上的TableShard注解
//     *
//     * @param mappedStatement MappedStatement
//     * @return TableShard注解
//     */
//    private TablePrepare getTableShardAnnotation(MappedStatement mappedStatement) {
//        TablePrepare tablePrepare = null;
//        try {
//            String id = mappedStatement.getId();
//            String className = id.substring(0, id.lastIndexOf("."));
//            String methodName = id.substring(id.lastIndexOf(".") + 1);
//            final Method[] method = Class.forName(className).getMethods();
//            for (Method me : method) {
//                if (me.getName().equals(methodName) && me.isAnnotationPresent(TablePrepare.class)) {
//                    tablePrepare = me.getAnnotation(TablePrepare.class);
//                    break;
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return tablePrepare;
//    }
//
//    /**
//     * 从参数里面找到指定对象指定字段对应的值--基础类型
//     */
//    private String getPrimitiveParamFieldValue(MetaObject metaStatementHandler, String fieldParamKey) {
//        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
//        Object parameterObject = boundSql.getParameterObject();
//        if (parameterObject == null) {
//            return null;
//        }
//        Object filterFiledObject = ((MapperMethod.ParamMap) parameterObject).get(fieldParamKey);
//        if (filterFiledObject == null) {
//            return null;
//        }
//        Object dependObject = recursiveGetEffectiveObject(filterFiledObject);
//        return dependObject == null ? null : dependObject.toString();
//    }
//
//    /**
//     * 获取参数里面的对象
//     */
//    private Object recursiveGetEffectiveObject(Object srcObject) {
//
//        if (!(srcObject instanceof List)) {
//            return srcObject;
//        }
//        Object listItemObject = ((List) srcObject).get(0);
//        while (listItemObject instanceof List) {
//            listItemObject = ((List) listItemObject).get(0);
//        }
//        return listItemObject;
//    }
//
//
//    /**
//     * 从参数里面找到指定对象指定字段对应的值--对象
//     * 如该参数是List.指定对象为第一个元素
//     */
//    private String getParamObjectFiledValue(MetaObject metaStatementHandler, String fieldParamKey, String dependFieldName) {
//
//        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
//        Object parameterObject = boundSql.getParameterObject();
//        Object filterFiledObject = ((MapperMethod.ParamMap) parameterObject).get(fieldParamKey);
//
//        if (filterFiledObject == null) {
//            return null;
//        }
//        Object dependObject = recursiveGetEffectiveObject(filterFiledObject);
//        try {
//            return (String) ReflectUtil.getFieldValue(dependObject, dependFieldName);
//        } catch (Exception ignored) {
//        }
//
//        return null;
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
//        return (target instanceof RoutingStatementHandler) ? Plugin.wrap(target, this) : target;
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//    }
//}
