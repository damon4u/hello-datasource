package com.damon4u.demo.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-08-22 12:18
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ReadDataSource {
}
