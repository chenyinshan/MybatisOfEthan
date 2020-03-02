package com.ethan.sqlSession;

public interface SqlSession {


    void dml(String statementid, Object param) throws Exception;


    //为Dao接口生成代理实现类
    <T> T getMapper(Class<?> mapperClass);


}
