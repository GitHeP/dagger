package com.github.dagger.reflect;

import com.github.dagger.bean.BeanUtils;
import com.github.dagger.lang.Throwables;
import com.sun.istack.internal.NotNull;
import sun.reflect.CallerSensitive;

import java.lang.reflect.Constructor;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class Reflectable {

    private static final Class<ReflectException> THROWABLE_CLASS = ReflectException.class;

    @CallerSensitive
    public static Class<?> reflect(@NotNull String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            // TODO throw this exception
        }
        return null;
    }

    public static <T> T construct(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    public static <T> T construct(Class<T> cls , Object ... objs) {
        return construct(cls , true , objs);
    }


    public static <T> T construct(Class<T> cls , boolean declared , Object ... objs) {
        Class<?>[] classes = BeanUtils.getClassArray(objs);
        try {
            Constructor<T> constructor = declared ? cls.getDeclaredConstructor(classes) : cls.getConstructor(classes);
            return constructor.newInstance(objs);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }
}
