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

    /**
     * 对无参数静态函数的调用
     * @param method    函数名称
     * @param args      函数参数
     * @return
     */
    public static Object invokeStatic(Method method , Object[] args) {
        return invoke(accessible(method) , null , args);
    }

    /**
     * 调用一个静态函数
     * @param cls                   函数所在的 Class
     * @param method                函数名称
     * @param methodArgTypes        函数参数列表
     * @param args                  函数参数
     * @return
     */
    public static Object invokeStatic(Class<?> cls , String method , Class<?>[] methodArgTypes , Object[] args) {
        return invokeStatic(method(cls , method , methodArgTypes) , args);
    }

    /**
     * 调用传入的 {@link Method}
     * @param object            调用的目标对象
     * @param method            被调用的 {@link Method} 名称
     * @param methodArgTypes    method 的参数列表 Class 数组
     * @param args              函数需要的参数
     * @return
     */
    public static Object invoke(Object object , String method , Class<?>[] methodArgTypes, Object[] args) {
        Method m = method(toClass(object), method, methodArgTypes);
        return isStatic(m) ? invokeStatic(m , args) : invoke(m , object , args);
    }

    /**
     * 调用传入的 {@link Method}
     * @param method    被调用的 {@link Method}
     * @param object    调用的目标对象
     * @param args      函数需要的参数
     * @return
     */
    public static Object invoke(Method method , Object object , Object[] args) {
        try {
            return accessible(method).invoke(object , args);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    /**
     * 获取无参数的的 {@link Method}
     * @param cls       方法所在的类
     * @param method    函数名称
     * @return          返回可访问的 {@link Method} 实例.
     */
    public static Method method(Class<?> cls , String method) {
        return method(cls , method , null);
    }

    /**
     * 获取指定的 {@link Method}
     * @param cls               方法所在的类
     * @param method            函数名称
     * @param methodArgTypes    函数参数列表
     * @return                  返回可访问的 {@link Method} 实例.
     */
    public static Method method(Class<?> cls , String method , Class<?>[] methodArgTypes) {
        Assert.notNull(cls , "cls must not be null");
        Assert.notBlank(method , "method must no be blank");

        return method(cls , method , methodArgTypes, true);
    }

    /**
     * 获取指定的 {@link Method}
     * @param cls               方法所在的类
     * @param method            函数名称
     * @param methodArgTypes   函数参数列表
     * @param declared          是否获取 DeclaredMethod {@link Class#getDeclaredMethod(String, Class[])}
     * @return  返回可访问的 {@link Method} 实例.
     */
    public static Method method(Class<?> cls , String method , Class<?>[] methodArgTypes , boolean declared) {
        Assert.notNull(cls , "cls must not be null");
        Assert.notBlank(method , "method must no be blank");
        try {
            Method m = declared ? cls.getDeclaredMethod(method , methodArgTypes)
                    : cls.getMethod(method , methodArgTypes);
            return accessible(m);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    /**
     * 用无参构造器构造一个实例对象
     * @param cls   构造器所在 Class
     * @param <T>
     * @return
     */
    public static <T> T construct(Class<T> cls) {
        Assert.notNull(cls , "cls must not be null");
        try {
            return cls.newInstance();
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    /**
     * 构造一个指定类型的实列对象
     * @param cls           构造器所在 Class
     * @param args          构造器需要的参数
     * @param <T>
     * @return
     */
    public static <T> T construct(Class<T> cls , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        return construct(cls , true , args);
    }

    /**
     * 构造一个指定类型的实列对象
     * @param cls           构造器所在 Class
     * @param declared      是否调用 declared 的构造器
     * @param args          构造器需要的参数
     * @param <T>
     * @return
     */
    public static <T> T construct(Class<T> cls , boolean declared , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        return newInstance(constructor(cls, declared, BeanUtils.defaultClassArrayIfNull(null , args)) , args);
    }

    /**
     * 构造一个指定类型的实列对象
     * @param cls                    构造器所在 Class
     * @param constructorArgTypes    构造器参数类型
     * @param args                   构造器需要的参数
     * @param <T>
     * @return
     */
    public static <T> T construct(Class<T> cls ,  Class<?>[] constructorArgTypes , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        return construct(cls , true , constructorArgTypes , args);
    }

    /**
     * 构造一个指定类型的实列对象
     * @param cls                   构造器所在 Class
     * @param declared              是否调用 declared 的构造器
     * @param constructorArgTypes    构造器参数类型
     * @param args                   构造器需要的参数
     * @param <T>
     * @return
     */
    public static <T> T construct(Class<T> cls , boolean declared , Class<?>[] constructorArgTypes , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        return newInstance(constructor(cls, declared, constructorArgTypes) , args);
    }

    /**
     * 获取无参数构造器 {@link Constructor}
     * @param cls    构造器所在的类
     * @param <T>
     * @return
     */
    public static <T> Constructor<T> constructor(Class<T> cls) {
        Assert.notNull(cls , "cls must not be null");
        return constructor(cls , null);
    }

    /**
     * 获取指定参数的构造器 {@link Constructor}
     * @param cls       构造器所在的类
     * @param args      构造器需要的参数
     * @param <T>
     * @return
     */
    public static <T> Constructor<T> constructor(Class<T> cls , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        return constructor(cls , true , args);
    }

    /**
     * 获取指定参数列表的构造器 {@link Constructor}
     * @param cls               构造器所在的类
     * @param declared          {@link Class#getDeclaredConstructor(Class[])}
     * @param args              构造器需要的参数
     * @param <T>
     * @return              返回一个可访问的构造器实例
     */
    public static <T> Constructor<T> constructor(Class<T> cls , boolean declared , Object ... args) {
        Assert.notNull(cls , "cls must not be null");
        return constructor(cls , declared , BeanUtils.defaultClassArrayIfNull(null , args));
    }

    /**
     * 获取指定参数列表的构造器 {@link Constructor}
     * @param cls                         构造器所在 Class
     * @param constructorArgTypes        构造器参数列表
     * @param <T>
     * @return  返回一个可访问的构造器实例
     */
    public static <T> Constructor<T> constructor(Class<T> cls , Class<?>[] constructorArgTypes) {
        Assert.notNull(cls , "cls must not be null");
        return constructor(cls , true , constructorArgTypes);
    }

    /**
     * 获取指定参数列表的构造器 {@link Constructor}
     * @param cls                       构造器所在 Class
     * @param declared                  {@link Class#getDeclaredConstructor(Class[])}
     * @param constructorArgTypes        构造器参数列表
     * @param <T>
     * @return  返回一个可访问的构造器实例
     */
    public static <T> Constructor<T> constructor(Class<T> cls , boolean declared , Class<?>[] constructorArgTypes) {
        Assert.notNull(cls , "cls must not be null");
        try {
            Constructor<T> constructor = declared ? cls.getDeclaredConstructor(constructorArgTypes) : cls.getConstructor(constructorArgTypes);
            return accessible(constructor);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    /**
     * 获取指定名称的 {@link Class} 实例
     * @param className     Class 名称
     * @return
     */
    public static Class<?> toClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    /**
     * 获取指定名称的 {@link Class} 实例
     * @param className     Class 名称
     * @param initialize    如果是 true Class 将会被初始化
     * @param loader        指定的类加载器
     * @return
     */
    public static Class<?> toClass(String className , boolean initialize, ClassLoader loader) {
        try {
            return Class.forName(className , initialize , loader);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

    /**
     * 获取传入对象的 {@link Class} 实例
     * @param object
     * @param <T>
     * @return
     */
    public static <T> Class<T> toClass(T object) {
        Assert.notNull(object , "object must not be null");
        return (Class<T>) object.getClass();
    }

    /**
     * 设置 accessible 为 true. {@link AccessibleObject#setAccessible(boolean)}
     * @param accessibleObject   需要被 {@link AccessibleObject#setAccessible(boolean)} 的对象
     * @param <T>
     * @return
     */
    public static <T extends AccessibleObject> T accessible(T accessibleObject) {
        Assert.notNull(accessibleObject , "accessibleObject must not be null");
        if (Assert.isFalse(accessibleObject::isAccessible)) {
            accessibleObject.setAccessible(true);
        }
        return accessibleObject;
    }

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


    private static <T> T newInstance(Constructor<T> constructor , Object ... args) {
        Assert.notNull(constructor , "constructor must not be null");
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw Throwables.wrap(e , THROWABLE_CLASS);
        }
    }

}
