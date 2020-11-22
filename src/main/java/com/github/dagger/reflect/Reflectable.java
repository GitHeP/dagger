package com.github.dagger.reflect;

import com.github.dagger.bean.BeanUtils;
import com.github.dagger.lang.Assert;
import com.github.dagger.lang.Throwables;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author he peng
 * @date 2020/11/21
 */
public abstract class Reflectable {

    private static final Class<ReflectException> THROWABLE_CLASS = ReflectException.class;


    public static boolean isPublic(Member member) {
        return Modifier.isPublic(member.getModifiers());
    }

    public static boolean isPrivate(Member member) {
        return Modifier.isPrivate(member.getModifiers());
    }

    public static boolean isProtected(Member member) {
        return Modifier.isProtected(member.getModifiers());
    }

    public static boolean isStatic(Member member) {
        return Modifier.isStatic(member.getModifiers());
    }

    public static boolean isFinal(Member member) {
        return Modifier.isFinal(member.getModifiers());
    }

    public static boolean isSynchronized(Member member) {
        return Modifier.isSynchronized(member.getModifiers());
    }

    public static boolean isVolatile(Member member) {
        return Modifier.isVolatile(member.getModifiers());
    }

    public static boolean isTransient(Member member) {
        return Modifier.isTransient(member.getModifiers());
    }

    public static boolean isNative(Member member) {
        return Modifier.isNative(member.getModifiers());
    }

    public static boolean isAbstract(Member member) {
        return Modifier.isAbstract(member.getModifiers());
    }

    public static boolean isInterface(Member member) {
        return Modifier.isInterface(member.getModifiers());
    }

    public static boolean isStrict(Member member) {
        return Modifier.isStrict(member.getModifiers());
    }


    public static boolean isPublic(Class<?> cls) {
        return Modifier.isPublic(cls.getModifiers());
    }

    public static boolean isPrivate(Class<?> cls) {
        return Modifier.isPrivate(cls.getModifiers());
    }

    public static boolean isProtected(Class<?> cls) {
        return Modifier.isProtected(cls.getModifiers());
    }

    public static boolean isStatic(Class<?> cls) {
        return Modifier.isStatic(cls.getModifiers());
    }

    public static boolean isFinal(Class<?> cls) {
        return Modifier.isFinal(cls.getModifiers());
    }

    public static boolean isAbstract(Class<?> cls) {
        return Modifier.isAbstract(cls.getModifiers());
    }

    public static boolean isStrict(Class<?> cls) {
        return Modifier.isStrict(cls.getModifiers());
    }


    public static boolean isInterface(Class<?> cls) {
        return Modifier.isInterface(cls.getModifiers());
    }


    public static Object invokeStatic(Class<?> cls , String method , Class<?>[] argClasses , Object[] args) {
        return invokeStatic(method(cls , method , argClasses) , args);
    }

    public static Object invokeStatic(Method method , Object[] args) {
        return invoke(accessible(method) , null , args);
    }

    public static Object invoke(Object object , String method , Class<?>[] argClasses , Object[] args) {
        Method m = method(toClass(object), method, argClasses);
        return isStatic(m) ? invokeStatic(m , args) : invoke(m , object , args);
    }

    public static Object invoke(Method method , Object object , Object[] args) {
        try {
            return accessible(method).invoke(object , args);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    public static Class<?> toClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    public static Class<?> toClass(String className , boolean initialize,
                                       ClassLoader loader) {
        try {
            return Class.forName(className , initialize , loader);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    public static <T> Class<T> toClass(T object) {
        Assert.notNull(object , "object must not be null");
        return (Class<T>) object.getClass();
    }

    public static <T> T construct(Class<T> cls) {
        Assert.notNull(cls , "cls must not be null");
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    public static <T> T construct(Class<T> cls ,  Class<?>[] argClasses , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        Assert.notEmpty(argClasses , "argClasses must not be empty");
        return construct(cls , true , argClasses , args);
    }

    public static <T> T construct(Class<T> cls , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        Assert.notEmpty(args , "args must not be empty");
        return construct(cls , true , args);
    }

    public static <T> T construct(Class<T> cls , boolean declared , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        return newInstance(constructor(cls, declared, BeanUtils.classArray(args)) , args);
    }

    public static <T> T construct(Class<T> cls , boolean declared , Class<?>[] argClasses , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        Assert.notEmpty(argClasses , "argClasses must not be empty");
        return newInstance(constructor(cls, declared, argClasses) , args);
    }

    public static <T> Constructor<T> constructor(Class<T> cls , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        return constructor(cls , true , BeanUtils.classArray(args));
    }


    public static <T> Constructor<T> constructor(Class<T> cls , boolean declared , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        return constructor(cls , declared , BeanUtils.classArray(args));
    }

    public static <T> Constructor<T> constructor(Class<T> cls , Class<?>[] argClasses) {
        Assert.notNull(cls , "cls must not be null");
        Assert.notEmpty(argClasses , "argClasses must not be empty");
        return constructor(cls , true , argClasses);
    }

    public static <T> Constructor<T> constructor(Class<T> cls , boolean declared , Class<?>[] argClasses) {
        Assert.notNull(cls , "cls must not be null");
        Assert.notEmpty(argClasses , "argClasses must not be empty");
        try {
            Constructor<T> constructor = declared ? cls.getDeclaredConstructor(argClasses) : cls.getConstructor(argClasses);
            return accessible(constructor);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    public static Method method(Class<?> cls , String method) {
        return method(cls , method , null , true);
    }

    public static Method method(Class<?> cls , String method , Class<?>[] classes) {
        Assert.notNull(cls , "cls must not be null");
        Assert.notEmpty(classes , "classes must not be empty");
        Assert.notBlank(method , "method must no be blank");

        return method(cls , method , classes , true);
    }

    public static Method method(Class<?> cls , String method , Class<?>[] classes , boolean declared) {
        Assert.notNull(cls , "cls must not be null");
        Assert.notBlank(method , "method must no be blank");
        try {
            Method m = declared ? cls.getDeclaredMethod(method , classes)
                    : cls.getMethod(method , classes);
            return accessible(m);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    public static <T extends AccessibleObject> T accessible(T accessibleObject) {
        Assert.notNull(accessibleObject , "accessibleObject must not be null");
        if (Assert.isFalse(accessibleObject::isAccessible)) {
            accessibleObject.setAccessible(true);
        }
        return accessibleObject;
    }


    private static <T> T newInstance(Constructor<T> constructor , Object ... args) {
        Assert.notNull(constructor , "constructor must not be null");
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

}
