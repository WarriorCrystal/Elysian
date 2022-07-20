//Decompiled by Procyon!

package com.elysian.client.util.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Mode {
    String[] value();
}
