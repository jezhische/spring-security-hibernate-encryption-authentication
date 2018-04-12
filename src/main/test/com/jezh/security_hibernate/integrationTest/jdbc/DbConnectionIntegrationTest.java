package com.jezh.security_hibernate.integrationTest.jdbc;

import com.jezh.security_hibernate.integrationTest.baseTest.BaseIntegrationTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockServletContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DbConnectionIntegrationTest extends BaseIntegrationTestConfig {
//    @Autowired
//    private DataSource securityDataSource;
    @Autowired
    private Environment env;

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

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        log.warn("----@Before DbConnectionIntegrationTest: start retrieving jdbc properties with environment bean");
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

        jdbc_driver_mysql = env.getProperty("jdbc.driver");
        jdbc_url_mysql = env.getProperty("jdbc.url");
        jdbc_user_mysql = env.getProperty("jdbc.user");
        jdbc_password_mysql = env.getProperty("jdbc.password");

        security_jdbc_driver_mysql = env.getProperty("security.jdbc.driver");
        security_jdbc_url_mysql = env.getProperty("security.jdbc.url");
        security_jdbc_user_mysql = env.getProperty("security.jdbc.user");
        security_jdbc_password_mysql = env.getProperty("security.jdbc.password");
//..................//
        log.info("----@Before DbConnectionIntegrationTest: success retrieving jdbc properties with environment bean");
    }

    @Test
    public void dataSourcePropertiesExist() {
        log.warn("--------------------start test jdbc properties is not null");
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
        log.info("-----------test passed successfully: jdbc properties is not null ");
    }

@Test
public void verifyWebContextAndServletContext() {
    log.info(">>>test... ---------------------------------------------------------------");
    log.warn(String.format("%n%-147s Verify that the WebApplicationContext object was loading properly. " +
            "%n%-147s Verify that the ServletContext is not null." +
            "%n%-147s Verify that the ServletContext instance is the instance of MockServletContext class." +
            "%n%-147s Verify that the demoController bean is not null." +
            "%n%-147s Verify that the demoAppConfig bean is not null." +
            "%n%-147s Verify that the demoSecurityConfig bean is not null.", "", "", "", "", "", ""));
//        servletContext = webApplicationContext.getServletContext();
    assertNotNull(servletContext);
    assertTrue(servletContext instanceof MockServletContext);
    assertNotNull(webApplicationContext.getBean("registrationController"));
    assertNotNull(webApplicationContext.getBean("appConfig"));
    assertNotNull(webApplicationContext.getBean("dataSourceFactoryC3P0"));
    log.info(">>>true ---------------------------------------------------------------");
}
}
