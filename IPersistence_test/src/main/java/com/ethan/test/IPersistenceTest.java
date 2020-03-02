package com.ethan.test;

import com.ethan.dao.IUserDao;
import com.ethan.io.Resources;
import com.ethan.pojo.User;
import com.ethan.sqlSession.SqlSession;
import com.ethan.sqlSession.SqlSessionFactory;
import com.ethan.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class IPersistenceTest {

    private SqlSession sqlSession;

    @Before
    public void before() throws PropertyVetoException, DocumentException {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void testAdd() throws Exception {

        IUserDao dao = (IUserDao) sqlSession.getMapper(IUserDao.class);

        User user = new User();
        user.setId(3);
        user.setUsername("cc");

        dao.insertOne(user);

    }

    @Test
    public void testUpdate() throws Exception {

        //调用
        IUserDao dao = (IUserDao) sqlSession.getMapper(IUserDao.class);

        User user = new User();
        user.setId(3);
        user.setUsername("bb");

        dao.updateOne(user);

    }

    @Test
    public void testDelete() throws Exception {

        IUserDao dao = (IUserDao) sqlSession.getMapper(IUserDao.class);

        User user = new User();
        user.setId(3);

        dao.deleteOne(user);
    }


}
