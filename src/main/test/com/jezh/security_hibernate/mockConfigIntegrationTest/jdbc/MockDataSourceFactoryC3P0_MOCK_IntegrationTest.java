package com.jezh.security_hibernate.mockConfigIntegrationTest.jdbc;

import com.jezh.security_hibernate.jdbc.dataSourceFactoryImpl.DataSourceFactoryC3P0;
import com.jezh.security_hibernate.mockConfigIntegrationTest.baseTest.MockBaseIntegrationTestConfig;
import com.jezh.security_hibernate.util.utils.Utils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;

import static org.junit.Assert.assertTrue;

public class MockDataSourceFactoryC3P0_MOCK_IntegrationTest extends MockBaseIntegrationTestConfig {
    @Autowired
    private Environment env;

    private Connection connection;

    private DataSourceFactoryC3P0 MOCKED_commonDataSourceFactoryC3P0;
    private String prefix;

    //    jdbc-connection-pool.properties:
    private  String connection_pool_class, c3p0_connection_pool_initialPoolSize, c3p0_connection_pool_minPoolSize,
            c3p0_connection_pool_maxPoolSize, c3p0_connection_pool_maxIdleTime, c3p0_connection_pool_maxStatements,
            c3p0_connection_pool_maxStatementsPerConnection, c3p0_connection_pool_acquireIncrement;
    //    jdbc-hibernate.properties:
    private String hibernate_dialect, hibernate_show_sql, hibernate_packagesToScan;
    //    jdbc-mysql.properties:
    private String jdbc_driver, jdbc_url, jdbc_user, jdbc_password;
    //    security-jdbc-mysql.properties:
    private String security_jdbc_driver, security_jdbc_url, security_jdbc_user, security_jdbc_password;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        // "manual" properties initialization
        log.warn("---- start retrieving jdbc properties with environment bean");
        connection_pool_class = env.getProperty("connection.pool.class");
        c3p0_connection_pool_initialPoolSize = env.getProperty("c3p0.connection.pool.initialPoolSize");
        c3p0_connection_pool_minPoolSize = env.getProperty("c3p0.connection.pool.minPoolSize");
        c3p0_connection_pool_maxPoolSize = env.getProperty("c3p0.connection.pool.maxPoolSize");
        c3p0_connection_pool_maxIdleTime = env.getProperty("c3p0.connection.pool.maxIdleTime");
        c3p0_connection_pool_maxStatements = env.getProperty("c3p0.connection.pool.maxStatements");
        c3p0_connection_pool_maxStatementsPerConnection = env.getProperty("c3p0.connection.pool.maxStatementsPerConnection");
        c3p0_connection_pool_acquireIncrement = env.getProperty("c3p0.connection.pool.acquireIncrement");

        hibernate_dialect = env.getProperty("hibernate.dialect");
        hibernate_show_sql = env.getProperty("hibernate.show_sql");
        hibernate_packagesToScan = env.getProperty("hibernate.packagesToScan");

        jdbc_driver = env.getProperty("jdbc.driver");
        jdbc_url = env.getProperty("jdbc.url");
        jdbc_user = env.getProperty("jdbc.user");
        jdbc_password = env.getProperty("jdbc.password");

        security_jdbc_driver = env.getProperty("security.jdbc.driver");
        security_jdbc_url = env.getProperty("security.jdbc.url");
        security_jdbc_user = env.getProperty("security.jdbc.user");
        security_jdbc_password = env.getProperty("security.jdbc.password");

        // creating MOCKED DataSourceFactoryC3P0
//        class MOCKEDDataSourceFactoryC3P0 extends DataSourceFactoryC3P0 {}
        MOCKED_commonDataSourceFactoryC3P0 = new DataSourceFactoryC3P0() {
            @Override
            public DataSource newDataSource() {
                ComboPooledDataSource dataSource = new ComboPooledDataSource();
                prefix = null; // common data source
//                                                                              -----------------------logging---------
                log.info("Start common MOCKED DATA_SOURCE initialization with C3P0 connection pool -----------------");
                // ------------------ set the DEFAULT jdbc driver
                try {
                    String jdbcDriver = env.getProperty(prefix + jdbc_driver);
                    dataSource.setDriverClass(jdbcDriver);
//                                                                              -----------------------logging---------
                    log.trace("jdbc: jdbc.driver is set to " + jdbcDriver);
                } catch (PropertyVetoException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
                // ------------------ set database connection props
                try {
                    dataSource.setJdbcUrl(Utils.getPropsNotNull(env, log, prefix + jdbc_url));
                    dataSource.setUser(Utils.getPropsNotNull(env, log, prefix + jdbc_user));
                    dataSource.setPassword(Utils.getPropsNotNull(env, log, prefix + jdbc_password));

                    // ------------------ set connection pool properties
                    dataSource.setInitialPoolSize(Utils.parsePropsToInt(env, log, c3p0_connection_pool_initialPoolSize));
                    dataSource.setMinPoolSize(Utils.parsePropsToInt(env, log, c3p0_connection_pool_minPoolSize));
                    dataSource.setMaxPoolSize(Utils.parsePropsToInt(env, log, c3p0_connection_pool_maxPoolSize));
                    dataSource.setMaxIdleTime(Utils.parsePropsToInt(env, log, c3p0_connection_pool_maxIdleTime));
                    //---from golovach:
                    dataSource.setMaxStatements(Utils.parsePropsToInt(env, log, c3p0_connection_pool_maxStatements));
                    dataSource.setMaxStatementsPerConnection(Utils.parsePropsToInt(env, log, c3p0_connection_pool_maxStatementsPerConnection));
                    dataSource.setAcquireIncrement(Utils.parsePropsToInt(env, log, c3p0_connection_pool_acquireIncrement));
                } catch (Exception e) {
//                                                                              -----------------------logging---------
                    log.error(e.getCause() + ": " + e.getMessage());
                }
//                                                                              -----------------------logging---------
                log.info("common MOCKED DATA_SOURCE initialization finished with C3P0 connection pool --------------");
                return dataSource;
            }
        };


        log.info("---- success: retrieving jdbc properties with environment bean");
    }

    @After
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
            log.info("connection is closed: " + connection.isClosed());
        }
        prefix = null;
        MOCKED_commonDataSourceFactoryC3P0 = null;
    }

// --------------------------------------------------------------------MOCKED DATA SOURCE FACTORY C3P0 main method test
//                                                    MOCKEDdataSourceFactoryC3P0_newDataSource_commonDataSource_return
    @Test
    public void MOCKEDdataSourceFactoryC3P0_newDataSource_commonDataSource_return() throws Exception {
        log.warn("start");
        // ----------------------------------------- common dataSource
        DataSourceFactoryC3P0 dataSourceFactoryC3P0 = new DataSourceFactoryC3P0();
//        DataSource dataSource = dataSourceFactoryC3P0.newDataSource();
        connection = MOCKED_commonDataSourceFactoryC3P0.newDataSource().getConnection();
        assertTrue(connection.isValid(0));
        connection.close();
        assertTrue(connection.isClosed());


        log.info("success");
    }
}
