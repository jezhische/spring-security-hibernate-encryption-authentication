package com.jezh.security_hibernate.jdbc.dataSourceFactoryFactory;

import com.jezh.security_hibernate.jdbc.dataSourceFactory.DataSourceFactory;
import com.jezh.security_hibernate.jdbc.dataSourceFactoryImpl.DataSourceFactoryC3P0;
import com.jezh.security_hibernate.jdbc.dataSourceFactoryImpl.DataSourceFactoryDbcp;
import com.jezh.security_hibernate.jdbc.dataSourceFactoryImpl.DataSourceFactoryProxool;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class DataSourceFactoryFactory {
//    private static String CURRENT_JDBS_DRIVER = "RAW";
    private static DataSourceFactory dataSourceFactory;

    public static class DataSourceClassType {
        public static String SECURITY = "security.";
    }

    public static synchronized DataSourceFactory getDataSourceFactory(String dataSourceClass, String prefix)
            throws Exception{
//        DataSourceFactory result;
        switch (dataSourceClass) {
            case "com.mchange.v2.c3p0.ComboPooledDataSource":
                return new DataSourceFactoryC3P0(prefix);
//                break;
//            case "org.logicalcobwebs.proxool.ProxoolFacade":
//                return new DataSourceFactoryProxool(prefix);
////                break;
//            case "org.apache.commons.dbcp.BasicDataSource":
//                return new DataSourceFactoryDbcp(prefix);
//                break;
            default: throw new RuntimeException("unknown DataSource class");
        }
    }

// перегруженный метод, чтобы не указывать префикс, когда не нужно:
    public static synchronized DataSourceFactory getDataSourceFactory(String dataSourceClass) throws Exception {
        return getDataSourceFactory(dataSourceClass, "");
    }

//    public static synchronized void setJdbcDriver(String jdbcDriver) {
//        CURRENT_JDBS_DRIVER = jdbcDriver;
//    }
}
