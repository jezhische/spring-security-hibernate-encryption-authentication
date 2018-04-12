package com.jezh.security_hibernate.mockConfigIntegrationTest.jdbc;

import com.jezh.security_hibernate.jdbc.dataSourceFactoryFactory.DataSourceFactoryFactory;
import com.jezh.security_hibernate.jdbc.dataSourceFactoryImpl.DataSourceFactoryC3P0;
import com.jezh.security_hibernate.mockConfigIntegrationTest.baseTest.MockBaseIntegrationTestConfig;
import com.jezh.security_hibernate.util.utils.Utils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import java.sql.Connection;

import static org.junit.Assert.*;

public class MockDataSourceFactoryC3P0IntegrationTest extends MockBaseIntegrationTestConfig {
    @Autowired
    private Environment env;
    @Autowired
    private Logger log;

    private Connection connection;

    @After
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
            log.info("connection is closed: " + connection.isClosed());
        }
    }

    @Test
    @Ignore
    public void testNewDataSource() {
        DataSource dataSource = new DataSourceFactoryC3P0("security").newDataSource();
        assertNotNull(dataSource);
    }

    @Test
    public void testJdbc_driver_property() {
        log.warn("");
        assertEquals("com.mysql.jdbc.Driver",
                env.getProperty("jdbc.driver"));
        assertEquals("com.mysql.jdbc.Driver",
                env.getProperty(DataSourceFactoryFactory.DataSourceClassType.SECURITY + "jdbc.driver.mysql"));
        log.info("");
    }

    @Test
    public void comboPooledDataSource_setDriverClass_ok() throws Exception {
        log.warn("");
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        String jdbcDriver = env.getProperty("jdbc.driver");
        dataSource.setDriverClass(jdbcDriver);
        assertEquals("com.mysql.jdbc.Driver", dataSource.getDriverClass());
//        System.out.println(Class.forName("com.mysql.jdbc.Driver").toString());

        jdbcDriver = env.getProperty(DataSourceFactoryFactory.DataSourceClassType.SECURITY + "jdbc.driver");
        dataSource.setDriverClass(jdbcDriver);
        assertEquals("com.mysql.jdbc.Driver", dataSource.getDriverClass());
        log.info("");
    }

// ----------------------------------------------------------------------------------------------C3P0: TEST DATA SOURCE
//                                                                            testDataSource_DEFAULT_SETTING_Connection
    @Test
    public void testDataSourceDefaultSettingsConnection() throws Exception {
        log.warn("");
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        String jdbcDriver = env.getProperty("jdbc.driver");
        dataSource.setDriverClass(jdbcDriver);

// ---------------------------------------------------- test Custom DB:

        String prefix = "";

        dataSource.setJdbcUrl(Utils.getPropsNotNull(env, log, prefix + "jdbc.url"));
        dataSource.setUser(Utils.getPropsNotNull(env, log, prefix + "jdbc.user"));
        dataSource.setPassword(Utils.getPropsNotNull(env, log, prefix + "jdbc.password"));

        Connection conn = dataSource.getConnection();
        assertTrue(conn.isValid(0));
        conn.close();
        assertTrue(conn.isClosed());

// ---------------------------------------------------- test Security DB:

        prefix = DataSourceFactoryFactory.DataSourceClassType.SECURITY;
        System.out.println(prefix);
        assertEquals("security.jdbc.url", prefix + "jdbc.url");
        assertEquals("jdbc:mysql://localhost:3306/sshib-security-bcrypt?useSSL=false",
                Utils.getPropsNotNull(env, log, prefix + "jdbc.url"));

        dataSource.setJdbcUrl(Utils.getPropsNotNull(env, log, prefix + "jdbc.url"));
        dataSource.setUser(Utils.getPropsNotNull(env, log, prefix + "jdbc.user"));
        dataSource.setPassword(Utils.getPropsNotNull(env, log, prefix + "jdbc.password"));

        conn = dataSource.getConnection();
        assertTrue(conn.isValid(0));
        conn.close();
        assertTrue(conn.isClosed());

        log.info("");
    }

// ----------------------------------------------------------------------------------------------C3P0: TEST DATA SOURCE
//                                                                               testDataSource_FULL_SETTING_Connection
    @Test
    public void testDataSourceFullSettingsConnection() throws Exception {
        log.warn("");
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        String jdbcDriver = env.getProperty("jdbc.driver");
        dataSource.setDriverClass(jdbcDriver);

// ---------------------------------------------------- test Custom DB:

        String prefix = "";

        dataSource.setJdbcUrl(Utils.getPropsNotNull(env, log, prefix + "jdbc.url"));
        dataSource.setUser(Utils.getPropsNotNull(env, log, prefix + "jdbc.user"));
        dataSource.setPassword(Utils.getPropsNotNull(env, log, prefix + "jdbc.password"));

        dataSource.setInitialPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.initialPoolSize"));
        dataSource.setMinPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.minPoolSize"));
        dataSource.setMaxPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxPoolSize"));
        dataSource.setMaxIdleTime(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxIdleTime"));
        //---from golovach:
        dataSource.setMaxStatements(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxStatements"));
        dataSource.setMaxStatementsPerConnection(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxStatementsPerConnection"));
        dataSource.setAcquireIncrement(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.acquireIncrement"));

        Connection conn = dataSource.getConnection();
        assertTrue(conn.isValid(0));
        conn.close();
        assertTrue(conn.isClosed());

// ---------------------------------------------------- test Security DB:

        prefix = DataSourceFactoryFactory.DataSourceClassType.SECURITY;
        System.out.println(prefix);
        assertEquals("security.jdbc.url", prefix + "jdbc.url");
        assertEquals("jdbc:mysql://localhost:3306/sshib-security-bcrypt?useSSL=false",
                Utils.getPropsNotNull(env, log, prefix + "jdbc.url"));

        dataSource.setJdbcUrl(Utils.getPropsNotNull(env, log, prefix + "jdbc.url"));
        dataSource.setUser(Utils.getPropsNotNull(env, log, prefix + "jdbc.user"));
        dataSource.setPassword(Utils.getPropsNotNull(env, log, prefix + "jdbc.password"));

        dataSource.setInitialPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.initialPoolSize"));
        dataSource.setMinPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.minPoolSize"));
        dataSource.setMaxPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxPoolSize"));
        dataSource.setMaxIdleTime(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxIdleTime"));
        //---from golovach:
        dataSource.setMaxStatements(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxStatements"));
        dataSource.setMaxStatementsPerConnection(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxStatementsPerConnection"));
        dataSource.setAcquireIncrement(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.acquireIncrement"));

        conn = dataSource.getConnection();
        assertTrue(conn.isValid(0));
        conn.close();
        assertTrue(conn.isClosed());
        log.info("SUCCESS");
    }

// ---------------------------------------------------------------------------DATA SOURCE FACTORY C3P0 main method test
//                                                          dataSourceFactoryC3P0_newDataSource_commonDataSource_return
    @Test
    public void dataSourceFactoryC3P0_newDataSource_commonDataSource_return() throws Exception {
        log.warn("start");
        // ----------------------------------------- common dataSource
        DataSourceFactoryC3P0 dataSourceFactoryC3P0 = new DataSourceFactoryC3P0();
        DataSource dataSource = dataSourceFactoryC3P0.newDataSource();
        connection = dataSource.getConnection();
        assertTrue(connection.isValid(0));

        log.info("success");
    }

    @Test
    public void name1() {
        log.warn("start");
        log.info("success");
    }

    @Test
    public void name2() {
        log.warn("start");
        log.info("success");
    }

    @Test
    public void name3() {
        log.warn("start");
        log.info("success");
    }
}
