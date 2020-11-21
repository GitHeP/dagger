package com.github.dagger.lang;

/**
 * @author he peng
 * @date 2020/11/21
 */
public class DaggerRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -3949910943287989165L;

    public DaggerRuntimeException() {
    }

    public DaggerRuntimeException(String message) {
        super(message);
    }

    public DaggerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaggerRuntimeException(Throwable cause) {
        super(cause);
    }

    public DaggerRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
