package com.revature.quizzard.web.util;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestMapping {
    String value() default "/";
    HttpMethod method();
    String produces() default "";
    String consumes() default "";
}
