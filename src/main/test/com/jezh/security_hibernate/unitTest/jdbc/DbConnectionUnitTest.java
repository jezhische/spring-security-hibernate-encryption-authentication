package com.jezh.security_hibernate.unitTest.jdbc;

import com.jezh.security_hibernate.util.CommonLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class DbConnectionUnitTest {
    private Logger log = LogManager.getLogger(CommonLogger.class.getName());
    private Properties properties;
    private Connection connection;
//    jdbc-connection-pool.properties:
    private  String connection_pool_class, c3p0_connection_pool_initialPoolSize, c3p0_connection_pool_minPoolSize,
            c3p0_connection_pool_maxPoolSize, c3p0_connection_pool_maxIdleTime, c3p0_connection_pool_maxStatements,
            c3p0_connection_pool_maxStatementsPerConnection, c3p0_connection_pool_acquireIncrement;
//    jdbc-hibernate.properties:
    private String hibernate_dialect, hibernate_show_sql, hibernate_packagesToScan;
//    jdbc-mysql.properties:
    private String jdbc_driver_mysql, jdbc_url_mysql, jdbc_user_mysql, jdbc_password_mysql;
//    security-jdbc-mysql.properties:
private String security_jdbc_driver_mysql, security_jdbc_url_mysql, security_jdbc_user_mysql, security_jdbc_password_mysql;

    @Before
    public void setUp() throws Exception {
        log.warn("--------------------start test jdbc properties is not null");
        properties = new Properties();
        properties.load(new FileReader("src/main/resources/persistence-connection-pool.properties"));
        properties.load(new FileReader("src/main/resources/persistence-hibernate.properties"));
        properties.load(new FileReader("src/main/resources/persistence-mysql.properties"));
        properties.load(new FileReader("src/main/resources/security-persistence-mysql.properties"));

        connection_pool_class = properties.getProperty("connection.pool.class");
        c3p0_connection_pool_initialPoolSize = properties.getProperty("c3p0.connection.pool.initialPoolSize");
        c3p0_connection_pool_minPoolSize = properties.getProperty("c3p0.connection.pool.minPoolSize");
        c3p0_connection_pool_maxPoolSize = properties.getProperty("c3p0.connection.pool.maxPoolSize");
        c3p0_connection_pool_maxIdleTime = properties.getProperty("c3p0.connection.pool.maxIdleTime");
        c3p0_connection_pool_maxStatements = properties.getProperty("c3p0.connection.pool.maxStatements");
        c3p0_connection_pool_maxStatementsPerConnection = properties.getProperty("c3p0.connection.pool.maxStatementsPerConnection");
        c3p0_connection_pool_acquireIncrement = properties.getProperty("c3p0.connection.pool.acquireIncrement");

        hibernate_dialect = properties.getProperty("hibernate.dialect");
        hibernate_show_sql = properties.getProperty("hibernate.show_sql");
        hibernate_packagesToScan = properties.getProperty("hibernate.packagesToScan");

        jdbc_driver_mysql = properties.getProperty("jdbc.driver");
        jdbc_url_mysql = properties.getProperty("jdbc.url");
        jdbc_user_mysql = properties.getProperty("jdbc.user");
        jdbc_password_mysql = properties.getProperty("jdbc.password");

        security_jdbc_driver_mysql = properties.getProperty("security.jdbc.driver");
        security_jdbc_url_mysql = properties.getProperty("security.jdbc.url");
        security_jdbc_user_mysql = properties.getProperty("security.jdbc.user");
        security_jdbc_password_mysql = properties.getProperty("security.jdbc.password");
        log.info("-----------test passed successfully: jdbc properties is not null ");
    }

    @After
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
            log.warn("connection is closed: " + connection.isClosed());
        }
    }

    @Test
    public void dataSourcePropertiesExist() {
        assertNotNull(connection_pool_class);
        assertNotNull(c3p0_connection_pool_initialPoolSize);
        assertNotNull(c3p0_connection_pool_minPoolSize);
        assertNotNull(c3p0_connection_pool_maxPoolSize);
        assertNotNull(c3p0_connection_pool_maxIdleTime);
        assertNotNull(c3p0_connection_pool_maxStatements);
        assertNotNull(c3p0_connection_pool_maxStatementsPerConnection);
        assertNotNull(c3p0_connection_pool_acquireIncrement);

        assertNotNull(hibernate_dialect);
        assertNotNull(hibernate_show_sql);
        assertNotNull(hibernate_packagesToScan);

        assertNotNull(jdbc_driver_mysql);
        assertNotNull(jdbc_url_mysql);
        assertNotNull(jdbc_user_mysql);
        assertNotNull(jdbc_password_mysql);

        assertNotNull(security_jdbc_driver_mysql);
        assertNotNull(security_jdbc_url_mysql);
        assertNotNull(security_jdbc_user_mysql);
        assertNotNull(security_jdbc_password_mysql);
    }

    @Test
    public void testCustomDbConnection() throws Exception {
//        DriverManager.registerDriver(new com.mysql.jdbc.Driver()); // уже не обязательно, DriverManager сам найдет драйвер
        connection = DriverManager.getConnection(jdbc_url_mysql, jdbc_user_mysql, jdbc_password_mysql);
        log.warn("jdbc: `sshib-customer` db: DriverManager.getConnection().isValid = " + connection.isValid(0));

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT id, first_name, last_name, email  FROM `sshib-customer`.customer WHERE first_name='John'");
        while (resultSet.next()) {
            log.info("id = " + resultSet.getInt("id"));
            log.info("first_name = " + resultSet.getString("first_name"));
            log.info("last_name = " + resultSet.getString("last_name"));
            log.info("email = " + resultSet.getString("email"));
        }
    }

    @Test
    public void testSecurityDbConnection() throws Exception {
//        DriverManager.registerDriver(new com.mysql.jdbc.Driver()); // уже не обязательно, DriverManager сам найдет драйвер
        connection = DriverManager.getConnection(security_jdbc_url_mysql, security_jdbc_user_mysql, security_jdbc_password_mysql);
        log.warn("jdbc: `sshib-security-bcrypt` db: DriverManager.getConnection().isValid = " + connection.isValid(0));

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT username, password, enabled  FROM `sshib-security-bcrypt`.users ");
        while (resultSet.next()) {
            log.info("username = " + resultSet.getString("username"));
            log.info("password = " + resultSet.getString("password"));
            log.info("enabled = " + resultSet.getInt("enabled"));
        }
    }

    @Test
    public void testDataSourceConfig_noExceptions_notNull() {

    }
}
