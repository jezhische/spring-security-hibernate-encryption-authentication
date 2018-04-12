package com.jezh.security_hibernate.util.utils;

import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

//@Component
public class Utils {

//    public static int parsePropsToInt(Environment env, Logger log, String propName) {
//        String property = env.getProperty(propName);
//        int result = 0;
//        if (property != null) {
//            try {
//                result = Integer.parseInt(property);
////                                                                              -----------------------logging---------
//                log.trace(String.format("%s is set to %d", propName, result));
//            } catch (NumberFormatException e) {
//                log.error(String.format("%s thrown when parse %s property: %s\n",
//                        e.getClass().getSimpleName(), propName, e.getMessage()));
//                throw new RuntimeException();
//            }
//        } else {
//            log.error(String.format("missing %s property\n", propName));
//            throw new RuntimeException();
//        }
//        return result;
//    }

// Переносим обработку исключений в выцзывающий класс, чтобы было понятнее, откуда брошено исключение
    public static int parsePropsToInt(Environment env, Logger log, String propName) throws Exception {
        String property = env.getProperty(propName);
        int result = 0;
        if (property != null) {
            try {
                result = Integer.parseInt(property);
//                                                                              -----------------------logging---------
                log.trace(String.format("%s is set to %d", propName, result));
            } catch (NumberFormatException e) {
                throw new NumberFormatException(
                        String.format("wrong number format: '%s' cannot be '%s'", propName, property));
            }
        } else {
            throw new RuntimeException(String.format("missing %s property", propName),
                    new MissingRequiredPropertiesException());
        }
        return result;
    }

    public static String getPropsNotNull(Environment env, Logger log, String propName) throws Exception {
        String property = env.getProperty(propName);
        if (property != null) {
//                                                                              -----------------------logging---------
            log.trace("jdbc: " + propName + " is set to " + property);
            return property;
        } else {
            throw new RuntimeException(String.format("missing %s property", propName),
                    new MissingRequiredPropertiesException());
        }
    }
}
