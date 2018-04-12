package com.jezh.security_hibernate.integrationTest.baseTest;

import com.jezh.security_hibernate.config.AppConfig;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
// "to load the context configuration and bootstrap the context that the test will use":
@ContextConfiguration(/*loader = MyContextLoader.class, */classes = {AppConfig.class/*, TestContext.class*/})
// "This annotation ensures that the application context which is loaded for our test is a WebApplicationContext..."
// Иначе получаем  "No qualifying bean of type 'javax.servlet.ServletContext' available"
@WebAppConfiguration(value = "com.jezh.security_hibernate.config.SpringMvcDispatcherServletInitializer")
public abstract class BaseIntegrationTestConfig {

    @Autowired
    public Logger log;

    public MockMvc mockMvc;

    public ServletContext servletContext;

    @Autowired
    public WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        log.warn("----@Before BaseIntegrationTestConfig: creating mockMvc from webApplicationContext, retrieving servletContext");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        servletContext = webApplicationContext.getServletContext();
        log.info("----@Before passed successfully: creating mockMvc from webApplicationContext, retrieving servletContext");
    }

    @BeforeClass
    public static void init() throws Exception {
// Just to check that this configuration has successfully attached to test classes and the @BeforeClass is runned normally:
//        log.trace("@BeforeClass checks the jdbc drivers enable: ");
//        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
//        while (driverEnumeration.hasMoreElements()) log.debug(driverEnumeration.nextElement());
// ------------------------------------------------------------------------------------------------
    }
}
