package com.jezh.security_hibernate.jdbc.impl;

//import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryDbcp /*implements ConnectionFactory*/ {

//    // эта хреновина создает пул соедиений:
//    private final BasicDataSource dataSource;
//
//    public ConnectionFactoryDbcp() throws SQLException {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setDriverClassName(DRIVER_CLASS_NAME);
//        ds.setUrl(JDBC_URL);
//        ds.setUsername(USER);
//        ds.setPassword(PASSWORD);
//        dataSource = ds;
//    }
//
//    @Override
//    public Connection newConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
//
//    @Override
//    public void close() throws SQLException {
//        dataSource.close(); // этот ConnectionPool содержит метод close(), так что им и воспользуемся. Когда вызывается
//        // close(), JVM сообщает базе данных (т.е. посылает соответствующий пакет): я закрываю свой сокет, закрой свой.
//    }
}
