package com.jezh.security_hibernate.jdbc.dataSourceFactoryImpl;

import com.jezh.security_hibernate.jdbc.dataSourceFactory.DataSourceFactory;
import com.jezh.security_hibernate.util.utils.Utils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Component
public class DataSourceFactoryC3P0 implements DataSourceFactory {
    @Resource
    private Environment env;
    @Resource
    private Logger log;

// чтобы
    private String prefix = "";

    public DataSourceFactoryC3P0() {
    }

    public DataSourceFactoryC3P0(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public DataSource newDataSource() {
        // --------------------------create connection pool
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        prefix = prefix.equals("") ? prefix : prefix + ".";

//                                                                              -----------------------logging---------
        log.info("Start DATA_SOURCE initialization with C3P0 connection pool ------------------------------------");

        // ------------------ set the DEFAULT jdbc driver
        try {
// Если this создан с помощью конструктора без аргументов, то prefix = ""; со вторым конструктором prefix = "что-то",
// например, "security.", если в аргументы конструктора подается, к примеру,
// DataSourceFactoryFactory.DataSourceClassType.SECURITY = "security."
            String jdbcDriver = env.getProperty(prefix + "jdbc.driver");
            dataSource.setDriverClass(jdbcDriver);
//                                                                              -----------------------logging---------
            log.trace("jdbc: jdbc.driver is set to " + jdbcDriver);
        } catch (PropertyVetoException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e); // Кастинг Exception в RuntimeException потому, что выполнение программы далее
// бессмысленно и д.б. прервано, но Exception требует обработки или пробрасывания в сигнатуру метода для обработки в
// другом месте. Чтобы этого избежать, пробрасываем RuntimeException, которое не требует обработки, но выполнение
// программы завершит.
        }
        // ------------------ set database connection props
        try {
            dataSource.setJdbcUrl(Utils.getPropsNotNull(env, log, prefix + "jdbc.url"));
            dataSource.setUser(Utils.getPropsNotNull(env, log, prefix + "jdbc.user"));
            dataSource.setPassword(Utils.getPropsNotNull(env, log, prefix + "jdbc.password"));

            // ------------------ set connection pool properties
            dataSource.setInitialPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.initialPoolSize"));
            dataSource.setMinPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.minPoolSize"));
            dataSource.setMaxPoolSize(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxPoolSize"));
            dataSource.setMaxIdleTime(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxIdleTime"));
            //---from golovach:
            dataSource.setMaxStatements(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxStatements"));
            dataSource.setMaxStatementsPerConnection(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.maxStatementsPerConnection"));
            dataSource.setAcquireIncrement(Utils.parsePropsToInt(env, log, "c3p0.connection.pool.acquireIncrement"));
        } catch (Exception e) {
//                                                                              -----------------------logging---------
            log.error(e.getCause() + ": " + e.getMessage());
        }
//                                                                              -----------------------logging---------
        log.info("DATA_SOURCE initialization finished with C3P0 connection pool ------------------------------------");
        return dataSource;
    }
}
