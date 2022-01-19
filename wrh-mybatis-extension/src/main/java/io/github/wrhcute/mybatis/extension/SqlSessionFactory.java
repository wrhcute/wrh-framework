package io.github.wrhcute.mybatis.extension;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;

import java.sql.Connection;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName SqlSessionFactory.java
 * @Description TODO
 * @createTime 2022年01月19日 19:21:00
 */
public class SqlSessionFactory implements org.apache.ibatis.session.SqlSessionFactory {
    @Override
    public SqlSession openSession() {
        return null;
    }

    @Override
    public SqlSession openSession(boolean b) {
        return null;
    }

    @Override
    public SqlSession openSession(Connection connection) {
        return null;
    }

    @Override
    public SqlSession openSession(TransactionIsolationLevel transactionIsolationLevel) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType executorType) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType executorType, boolean b) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType executorType, TransactionIsolationLevel transactionIsolationLevel) {
        return null;
    }

    @Override
    public SqlSession openSession(ExecutorType executorType, Connection connection) {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }
}
