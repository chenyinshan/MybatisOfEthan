package com.ethan.sqlSession;

import com.ethan.pojo.Configuration;
import com.ethan.pojo.MappedStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void dml(String statementid, Object param) throws Exception {
        simpleExecutor executor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        executor.dml(configuration, mappedStatement, param);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        return (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();

                String statementId = className + "." + methodName;

                if (methodName.equals("insertOne") || methodName.equals("updateOne") || methodName.equals("deleteOne")) {
                    dml(statementId, args[0]);
                }
                return null;
            }
        });
    }


}
