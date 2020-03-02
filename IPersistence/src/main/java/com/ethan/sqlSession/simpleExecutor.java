package com.ethan.sqlSession;


import com.ethan.config.BoundSql;
import com.ethan.pojo.Configuration;
import com.ethan.pojo.MappedStatement;
import com.ethan.utils.GenericTokenParser;
import com.ethan.utils.ParameterMapping;
import com.ethan.utils.ParameterMappingTokenHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class simpleExecutor implements Executor {

    @Override
    public void dml(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {
        Connection connection = configuration.getDataSource().getConnection();
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        System.out.println(boundSql.getSqlText());

        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        String paramterType = mappedStatement.getParamterType();
        Class<?> paramtertypeClass = getClassType(paramterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();

            //反射
            Field declaredField = paramtertypeClass.getDeclaredField(content);
            //暴力访问
            declaredField.setAccessible(true);
            Object o = declaredField.get(param);

            preparedStatement.setObject(i + 1, o);

            System.out.println(declaredField.getName() + ":" + o);

        }

        // 5. 执行sql
        preparedStatement.execute();

    }


    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType != null) {
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
        return null;

    }


    /**
     * 完成对#{}的解析工作：1.将#{}使用？进行代替，2.解析出#{}里面的值进行存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;

    }


}
