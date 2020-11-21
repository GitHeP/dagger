package com.github.dagger.lang;

import java.util.Objects;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class Assert {

    public static void notNull(Object obj , String msg) {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException(msg);
        }
    }
}
