package com.damon4u.demo.datasource.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-09-21 10:40
 */
public class DataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);

    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public static void setRead() {
        context.set(DataSourceType.READ.getType());
        logger.info(">>>>>>>>>>>> switch to read datasource");
    }

    public static void setWrite() {
        context.set(DataSourceType.WRITE.getType());
        logger.info(">>>>>>>>>>>> switch to write datasource");
    }

    public static String getReadOrWrite() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}
