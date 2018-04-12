package com.jezh.security_hibernate.jdbc.dataSourceFactory;

import javax.sql.DataSource;

public interface DataSourceFactory {
    DataSource newDataSource();
}
