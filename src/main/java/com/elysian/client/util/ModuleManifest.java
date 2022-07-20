package com.elysian.client.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ModuleManifest {
    String label() default "";

    String[] aliases() default {};


    int key() default 0;

    boolean hidden() default false;

    boolean persistent() default false;

    String description() default "";
}

