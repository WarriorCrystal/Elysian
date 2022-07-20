//Decompiled by Procyon!

package com.elysian.client.util.annotations.child;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Child {
    String value();
}
