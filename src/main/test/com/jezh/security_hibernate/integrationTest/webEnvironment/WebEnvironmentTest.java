package com.jezh.security_hibernate.integrationTest.webEnvironment;

import com.jezh.security_hibernate.integrationTest.baseTest.BaseIntegrationTestConfig ;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WebEnvironmentTest extends BaseIntegrationTestConfig {

    // verify that I'm loading the WebApplicationContext object properly, and also that the right servletContext is being attached:
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
        assertNotNull(webApplicationContext.getBean("demoController"));
        assertNotNull(webApplicationContext.getBean("demoAppConfig"));
        assertNotNull(webApplicationContext.getBean("demoSecurityConfig"));
        log.info(">>>true ---------------------------------------------------------------");
    }
}
