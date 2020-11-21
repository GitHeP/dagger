package com.github.dagger.reflect;

import com.github.dagger.lang.DaggerRuntimeException;

/**
 * @author he peng
 * @date 2020/11/21
 */
public class ReflectException extends DaggerRuntimeException {

    private static final long serialVersionUID = -8713535341256452345L;

    public ReflectException() {
    }

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }

    public ReflectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
