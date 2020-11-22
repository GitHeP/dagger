package com.github.dagger.lang;

import com.github.dagger.reflect.Reflectable;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class Throwables {

    public static void throwIfRuntimeException(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }

        if (throwable instanceof Error) {
            throw (Error) throwable;
        }
    }

    public static void throwWrappedException(Throwable throwable , Class<? extends RuntimeException> wrap) {
    }

    public static <X extends Throwable> void throwIfInstanceOf(
            Throwable throwable, Class<X> declaredType) throws X {
        if (declaredType.isInstance(throwable)) {
            throw declaredType.cast(throwable);
        }
    }

    public static <X extends RuntimeException> X wrap(Throwable throwable , Class<X> wrap) {
        return Reflectable.construct(wrap , new Class<?>[]{Throwable.class} , throwable);
    }
}
