package com.jezh.security_hibernate.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.jezh.security_hibernate.jdbc.dataSourceFactoryFactory.DataSourceFactoryFactory;
import com.jezh.security_hibernate.util.utils.Utils;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.jezh.security_hibernate")
@PropertySource({ "classpath:persistence-mysql.properties", "classpath:security-persistence-mysql.properties",
"classpath:persistence-hibernate.properties", "classpath:persistence-connection-pool.properties"})
public class AppConfig implements WebMvcConfigurer {

	@Resource
	private Environment env;

	@Resource
	private Logger log;
	
// --------------------------------------------------------------------------------------define a bean for ViewResolver

	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}

// ----------------------------------------------------------------------------------------define a bean for DATASOURCE
	@Bean
	public DataSource dataSource() {
//		// --------------------------create connection pool
//		ComboPooledDataSource dataSource = new ComboPooledDataSource();
//
////                                                                              -----------------------logging---------
//        log.info("Start DATA_SOURCE initialization--------------------------------------------");
//
//        // ------------------ set the jdbc driver
//        try {
//            String jdbcDriver = env.getProperty("jdbc.driver");
//            dataSource.setDriverClass(jdbcDriver);
////                                                                              -----------------------logging---------
//            log.trace("jdbc: jdbc.driver is set to " + jdbcDriver);
//        } catch (PropertyVetoException e) {
//            log.error(e.getMessage());
//            throw new RuntimeException(e); // Кастинг Exception в RuntimeException потому, что выполнение программы далее
//// бессмысленно и д.б. прервано, но Exception требует обработки или пробрасывания в сигнатуру метода для обработки в
//// другом месте. Чтобы этого избежать, пробрасываем RuntimeException, которое не требует обработки, но выполнение
//// программы завершит.
//        }
//        // ------------------ set database connection props
//            dataSource.setJdbcUrl(Utils.getPropsNotNull(env, log, "jdbc.url"));
//            dataSource.setJdbcUrl(Utils.getPropsNotNull(env, log, "jdbc.user"));
//            dataSource.setJdbcUrl(Utils.getPropsNotNull(env, log, "jdbc.password"));
//
//        // ------------------ set connection pool properties
//        dataSource.setInitialPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.initialPoolSize"));
//        dataSource.setMinPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.minPoolSize"));
//        dataSource.setMaxPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxPoolSize"));
//        dataSource.setMaxIdleTime(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxIdleTime"));
//        //---from golovach:
//        dataSource.setMaxStatements(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxStatements"));
//        dataSource.setMaxStatementsPerConnection(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxStatementsPerConnection"));
//        dataSource.setAcquireIncrement(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.acquireIncrement"));
////                                                                              -----------------------logging---------
//        log.info("DATA_SOURCE initialization finished--------------------------------------------");

//                                                                              -----------------------logging---------
        log.warn("Start COMMON_DATA_SOURCE initialization--------------------------------------------");
        try {
            return DataSourceFactoryFactory.getDataSourceFactory(env.getProperty("connection.pool.class"))
                    .newDataSource();
        } catch (Exception e) {
            log.error(e.getCause() + ": " + e.getMessage());
            throw new RuntimeException();
        }
    }

// ------------------------------------------------------------------------------define a bean for SECURITY DATA SOURCE
	
	@Bean
	public DataSource securityDataSource() {
//                                                                              -----------------------logging---------
        log.info("Start SECURITY_DATA_SOURCE initialization --------------------------------------------");
// отличие от dataSource() в том, что передаю в параметры DataSourceFactoryFactory.getDataSourceFactory() еще и String prefix,
// благодаря которому идет обращение к security-jdbc-mysql.properties, а не к jdbc-mysql.properties
        try {
            return DataSourceFactoryFactory.getDataSourceFactory(
                    env.getProperty("connection.pool.class"), DataSourceFactoryFactory.DataSourceClassType.SECURITY)
                    .newDataSource();
        } catch (Exception e) {
            log.error(e.getCause() + ": " + e.getMessage());
            throw new RuntimeException();
        }
	}

// ----------------------------------------------------------------------------------------- set HIBERNATE PROPERTIES
    private Properties getHibernateProperties() {

        // set hibernate properties
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        return props;
    }

// ----------------------------------------------------------------------------------------------- get SESSION_FACTORY:
//    session factory will be using to get access to db via Hibernate. Here is set: data source; entity location
// (packages to scan); hibernate properties (e.g. sql dialect and "show sql")

    @Bean
	public LocalSessionFactoryBean sessionFactory(){
		
		// create session factorys
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		// set the properties
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}

// ------------------------------------------------------------------------------------------- get TRANSACTION_MANAGER
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

// ---------------------------------------------------------------------------------------------- get RESOURCE_HANDLER
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/"); 
    }	
}









