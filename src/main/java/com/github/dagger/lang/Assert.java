package com.github.dagger.lang;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class Assert {

    public static void notBlank(CharSequence charSequence , String msg) {
        notNull(charSequence , msg);
        if (charSequence.length() == 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notNull(Object obj , String msg) {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notEmpty(Object[] array , String msg) {
        notNull(array , msg);
        if (array.length == 0) {
            throw new IllegalArgumentException(msg);
        }
    }


    public static boolean isFalse(BooleanSupplier supplier) {
        return ! isTrue(supplier);
    }

    public static boolean isTrue(BooleanSupplier supplier) {
        return supplier.getAsBoolean();
    }

}
