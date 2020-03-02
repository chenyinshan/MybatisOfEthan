package com.ethan.sqlSession;

import com.ethan.pojo.Configuration;
import com.ethan.pojo.MappedStatement;

public interface Executor {

    void dml(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;

}
